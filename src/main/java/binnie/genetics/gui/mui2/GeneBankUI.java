package binnie.genetics.gui.mui2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.input.Keyboard;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.ItemDrawable;
import com.cleanroommc.modularui.factory.PlayerInventoryGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widgets.PageButton;
import com.cleanroommc.modularui.widgets.PagedWidget;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.layout.Row;
import com.cleanroommc.modularui.widgets.textfield.TextFieldWidget;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.genetics.GeneTracker;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;

public class GeneBankUI {

    private static final int PADDING = 7;
    private static final int PANEL_WIDTH = 186;
    private static final int CONTENT_WIDTH = PANEL_WIDTH - PADDING * 2; // 172
    private static final int PLAYER_INV_PADDING = (PANEL_WIDTH - 162) / 2; // 12

    private static final int TITLE_HEIGHT = 10;
    private static final int SEARCH_HEIGHT = 14;
    private static final int GENE_SCROLL_HEIGHT = 116;
    private static final int FILTER_HEIGHT = 26;
    private static final int PLAYER_INV_HEIGHT = 76;
    private static final int GAP = 2;
    private static final int SECTION_GAP = 6;

    private static final int PANEL_HEIGHT = PADDING + TITLE_HEIGHT
            + GAP
            + SEARCH_HEIGHT
            + GAP
            + GENE_SCROLL_HEIGHT
            + GAP
            + FILTER_HEIGHT
            + SECTION_GAP
            + PLAYER_INV_HEIGHT
            + PADDING;

    private static final int CHAR_WIDTH = 6;

    public static ModularPanel buildPanel(PlayerInventoryGuiData data, PanelSyncManager syncManager, boolean isNEI) {
        EntityPlayer player = data.getPlayer();

        syncManager.addOpenListener(p -> {
            if (p instanceof EntityPlayerMP) {
                GeneTracker tracker = GeneTracker.getDisplayTracker(p.worldObj, p.getGameProfile());
                tracker.synchToPlayer(p);
            }
        });

        registerGeneSelectAction(syncManager);

        final TextFieldWidget[] searchFieldHolder = new TextFieldWidget[1];
        ModularPanel panel = new ModularPanel("gene_bank") {

            @Override
            public boolean onKeyPressed(char typedChar, int keyCode) {
                boolean handled = super.onKeyPressed(typedChar, keyCode);
                if (!handled && keyCode == Keyboard.KEY_TAB && searchFieldHolder[0] != null) {
                    getContext().focus(searchFieldHolder[0]);
                    return true;
                }
                return handled;
            }
        };
        panel.size(PANEL_WIDTH, PANEL_HEIGHT);

        List<BreedingSystem> systems = new ArrayList<>(Binnie.Genetics.getActiveSystems());
        if (systems.isEmpty()) {
            panel.bindPlayerInventory();
            return panel;
        }

        PagedWidget.Controller controller = new PagedWidget.Controller();
        List<GeneScrollWidget> scrollWidgets = new ArrayList<>();

        buildTabRow(panel, systems, controller, player);

        int curY = PADDING;

        addTitleRow(panel, systems, player, curY);
        curY += TITLE_HEIGHT + GAP;

        List<List<ChromoFilterButton>> allPageButtons = new ArrayList<>();
        @SuppressWarnings("unchecked")
        final List<ChromoFilterButton>[] currentPageButtons = new List[] { new ArrayList<>() };

        TextFieldWidget searchField = createSearchField(currentPageButtons, scrollWidgets);
        searchFieldHolder[0] = searchField;
        searchField.pos(PADDING, curY);
        panel.child(searchField);
        curY += SEARCH_HEIGHT + GAP;

        int scrollInset = 2;
        int scrollWidth = CONTENT_WIDTH - scrollInset * 2;
        int geneScrollHeight = GENE_SCROLL_HEIGHT - scrollInset * 2;
        for (BreedingSystem system : systems) {
            GeneScrollWidget scrollWidget = new GeneScrollWidget(system, isNEI, player, syncManager);
            scrollWidget.size(scrollWidth, geneScrollHeight);
            scrollWidgets.add(scrollWidget);
        }

        int geneY = curY;
        ParentWidget<?> geneAreaBg = new ParentWidget<>();
        geneAreaBg.pos(PADDING, geneY);
        geneAreaBg.size(CONTENT_WIDTH, GENE_SCROLL_HEIGHT);
        geneAreaBg.background(GuiTextures.DISPLAY);
        panel.child(geneAreaBg);

        int combinedHeight = GENE_SCROLL_HEIGHT + GAP + FILTER_HEIGHT;
        PagedWidget<?> pagedWidget = new PagedWidget<>().pos(PADDING, geneY).size(CONTENT_WIDTH, combinedHeight)
                .controller(controller);
        for (int s = 0; s < systems.size(); s++) {
            BreedingSystem system = systems.get(s);
            GeneScrollWidget scrollWidget = scrollWidgets.get(s);

            ParentWidget<?> page = new ParentWidget<>();
            page.size(CONTENT_WIDTH, combinedHeight);

            scrollWidget.pos(scrollInset, scrollInset);
            page.child(scrollWidget);

            List<ChromoFilterButton> pageButtons = new ArrayList<>();
            ParentWidget<?> filterPage = buildFilterPage(system, scrollWidget, CONTENT_WIDTH, FILTER_HEIGHT,
                    pageButtons);
            filterPage.pos(0, GENE_SCROLL_HEIGHT + GAP);
            page.child(filterPage);
            allPageButtons.add(pageButtons);

            pagedWidget.addPage(page);
        }

        wireTabHandlers(allPageButtons, currentPageButtons, searchField);

        if (!allPageButtons.isEmpty()) {
            currentPageButtons[0] = allPageButtons.get(0);
        }
        pagedWidget.onPageChange(pageIdx -> {
            if (pageIdx >= 0 && pageIdx < allPageButtons.size()) {
                currentPageButtons[0] = allPageButtons.get(pageIdx);
            }
        });

        panel.child(pagedWidget);
        curY += combinedHeight + SECTION_GAP;

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_PADDING, curY));

        return panel;
    }

    private static void registerGeneSelectAction(PanelSyncManager syncManager) {
        syncManager.registerSyncedAction("gene-select", false, true, packet -> {
            try {
                NBTTagCompound action = packet.readNBTTagCompoundFromBuffer();
                Gene gene = new Gene(action.getCompoundTag("gene"));
                if (gene.isCorrupted()) return;

                ItemStack held = syncManager.getCursorItem();
                if (held == null || !Engineering.isGeneAcceptor(held) || !Engineering.canAcceptGene(held, gene)) {
                    return;
                }

                ItemStack converted = Engineering.addGene(held, gene);
                syncManager.setCursorItem(converted);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void buildTabRow(ModularPanel panel, List<BreedingSystem> systems,
            PagedWidget.Controller controller, EntityPlayer player) {
        Row tabRow = new Row();
        tabRow.coverChildren();
        tabRow.top(-(32 - 4));
        tabRow.left(4);
        for (int i = 0; i < systems.size(); i++) {
            BreedingSystem system = systems.get(i);
            int tabLocation;
            if (systems.size() == 1) {
                tabLocation = -1;
            } else if (i == 0) {
                tabLocation = -1;
            } else if (i == systems.size() - 1) {
                tabLocation = 1;
            } else {
                tabLocation = 0;
            }
            PageButton tabButton = new PageButton(i, controller).tab(GuiTextures.TAB_TOP, tabLocation)
                    .overlay(new ItemDrawable(system.getItemStackRepresentative()).asIcon());
            tabButton.tooltipBuilder(tooltip -> {
                tooltip.addLine(IKey.str(system.toString()));
                int tg = 0;
                int sg = 0;
                GeneTracker tracker = GeneTracker.getDisplayTracker(player.worldObj, player.getGameProfile());
                Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());
                if (genes != null) {
                    for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
                        tg += entry.getValue().size();
                        for (IAllele allele : entry.getValue()) {
                            Gene gene = new Gene(allele, entry.getKey(), system.getSpeciesRoot());
                            if (tracker.isSequenced(gene)) {
                                sg++;
                            }
                        }
                    }
                }
                tooltip.addLine(IKey.lang("genetics.gui.geneBank.sequencedGenes.short", sg, tg));
            });
            tabRow.child(tabButton);
        }
        panel.child(tabRow);
    }

    private static void addTitleRow(ModularPanel panel, List<BreedingSystem> systems, EntityPlayer player, int curY) {
        panel.child(
                new TextWidget<>(IKey.lang("genetics.gui.geneBank")).pos(PADDING, curY)
                        .size(CONTENT_WIDTH / 2, TITLE_HEIGHT));

        int totalGenes = 0;
        int seqGenes = 0;
        for (BreedingSystem sys : systems) {
            GeneTracker tracker = GeneTracker.getDisplayTracker(player.worldObj, player.getGameProfile());
            Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(sys.getSpeciesRoot());
            if (genes == null) continue;
            for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
                totalGenes += entry.getValue().size();
                for (IAllele allele : entry.getValue()) {
                    Gene gene = new Gene(allele, entry.getKey(), sys.getSpeciesRoot());
                    if (tracker.isSequenced(gene)) {
                        ++seqGenes;
                    }
                }
            }
        }
        panel.child(
                new TextWidget<>(IKey.lang("genetics.gui.geneBank.sequencedGenes.short", seqGenes, totalGenes))
                        .pos(PADDING + CONTENT_WIDTH / 2, curY).size(CONTENT_WIDTH / 2, TITLE_HEIGHT).color(0xFFFF55));
    }

    private static TextFieldWidget createSearchField(List<ChromoFilterButton>[] currentPageButtons,
            List<GeneScrollWidget> scrollWidgets) {
        TextFieldWidget searchField = new TextFieldWidget() {

            @Override
            public void afterInit() {
                super.afterInit();
                getContext().focus(this);
            }

            @Override
            public Result onKeyPressed(char character, int keyCode) {
                if (keyCode == Keyboard.KEY_TAB && isFocused()) {
                    List<ChromoFilterButton> buttons = currentPageButtons[0];
                    if (Interactable.hasShiftDown()) {
                        if (buttons != null && !buttons.isEmpty()) {
                            getContext().focus(buttons.get(0));
                        }
                    } else {
                        if (buttons != null && buttons.size() > 1) {
                            getContext().focus(buttons.get(1));
                        } else if (buttons != null && !buttons.isEmpty()) {
                            getContext().focus(buttons.get(0));
                        }
                    }
                    return Result.SUCCESS;
                }
                return super.onKeyPressed(character, keyCode);
            }
        };
        searchField.size(CONTENT_WIDTH, SEARCH_HEIGHT);
        searchField.hintText(I18N.localise("genetics.gui.geneBank.search"));
        searchField.background(GuiTextures.DISPLAY_SMALL);
        searchField.onUpdateListener(w -> {
            String text = w.getText().toLowerCase();
            for (GeneScrollWidget sw : scrollWidgets) {
                if (sw.isValid()) {
                    sw.setFilter(text);
                }
            }
        });
        return searchField;
    }

    // Tab order: Search -> specific filters (1..N-1) -> All (0) -> Search
    private static void wireTabHandlers(List<List<ChromoFilterButton>> allPageButtons,
            List<ChromoFilterButton>[] currentPageButtons, TextFieldWidget searchField) {
        for (List<ChromoFilterButton> pageButtons : allPageButtons) {
            int size = pageButtons.size();
            if (size == 0) continue;

            ChromoFilterButton allBtn = pageButtons.get(0);
            allBtn.onTab(() -> allBtn.getContext().focus(searchField));
            allBtn.onShiftTab(() -> {
                List<ChromoFilterButton> buttons = currentPageButtons[0];
                if (buttons != null && buttons.size() > 1) {
                    allBtn.getContext().focus(buttons.get(buttons.size() - 1));
                } else {
                    allBtn.getContext().focus(searchField);
                }
            });

            for (int i = 1; i < size; i++) {
                ChromoFilterButton btn = pageButtons.get(i);
                int nextIdx = i + 1;
                int prevIdx = i - 1;

                if (nextIdx < size) {
                    btn.onTab(() -> {
                        List<ChromoFilterButton> buttons = currentPageButtons[0];
                        if (buttons != null && nextIdx < buttons.size()) {
                            btn.getContext().focus(buttons.get(nextIdx));
                        }
                    });
                } else {
                    btn.onTab(() -> {
                        List<ChromoFilterButton> buttons = currentPageButtons[0];
                        if (buttons != null && !buttons.isEmpty()) {
                            btn.getContext().focus(buttons.get(0));
                        }
                    });
                }

                if (prevIdx >= 1) {
                    btn.onShiftTab(() -> {
                        List<ChromoFilterButton> buttons = currentPageButtons[0];
                        if (buttons != null && prevIdx < buttons.size()) {
                            btn.getContext().focus(buttons.get(prevIdx));
                        }
                    });
                } else {
                    btn.onShiftTab(() -> btn.getContext().focus(searchField));
                }
            }
        }
    }

    private static ParentWidget<?> buildFilterPage(BreedingSystem system, GeneScrollWidget scrollWidget, int width,
            int height, List<ChromoFilterButton> outButtons) {
        ParentWidget<?> page = new ParentWidget<>();
        page.size(width, height);

        Map<IChromosomeType, List<IAllele>> chromosomes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());
        if (chromosomes == null) return page;

        int btnGap = 2;
        int btnH = 11;
        int fx = 0;
        int fy = 0;

        String allLabel = I18N.localise("genetics.gui.geneBank.filter.all");
        int allWidth = allLabel.length() * CHAR_WIDTH + 6;
        ChromoFilterButton allBtn = new ChromoFilterButton(allLabel, scrollWidget, null, allWidth);
        allBtn.pos(fx, fy);
        page.child(allBtn);
        outButtons.add(allBtn);
        fx += allWidth + btnGap;

        for (IChromosomeType chromo : chromosomes.keySet()) {
            String name = system.getChromosomeName(chromo);
            int btnWidth = name.length() * CHAR_WIDTH + 6;

            if (fx + btnWidth > width && fx > 0) {
                fy += btnH + btnGap;
                fx = 0;
            }
            if (fy + btnH > height) break;

            ChromoFilterButton btn = new ChromoFilterButton(name, scrollWidget, chromo, btnWidth);
            btn.pos(fx, fy);
            page.child(btn);
            outButtons.add(btn);
            fx += btnWidth + btnGap;
        }

        return page;
    }
}
