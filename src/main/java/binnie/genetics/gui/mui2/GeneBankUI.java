package binnie.genetics.gui.mui2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cleanroommc.modularui.api.drawable.IKey;
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
                GeneTracker tracker = GeneTracker.getTracker(p.worldObj, p.getGameProfile());
                tracker.synchToPlayer(p);
            }
        });

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

        ModularPanel panel = ModularPanel.defaultPanel("gene_bank", PANEL_WIDTH, PANEL_HEIGHT);

        List<BreedingSystem> systems = new ArrayList<>(Binnie.Genetics.getActiveSystems());
        if (systems.isEmpty()) {
            panel.bindPlayerInventory();
            return panel;
        }

        PagedWidget.Controller controller = new PagedWidget.Controller();
        List<GeneScrollWidget> scrollWidgets = new ArrayList<>();

        // Top tabs
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
                GeneTracker tracker = GeneTracker.getTracker(player.worldObj, player.getGameProfile());
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

        int curY = PADDING;

        // Title row: "Gene Bank" left, "X/Y" right
        panel.child(
                new TextWidget<>(IKey.lang("genetics.gui.geneBank")).pos(PADDING, curY)
                        .size(CONTENT_WIDTH / 2, TITLE_HEIGHT));

        int totalGenes = 0;
        int seqGenes = 0;
        for (BreedingSystem sys : systems) {
            GeneTracker tracker = GeneTracker.getTracker(player.worldObj, player.getGameProfile());
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
        curY += TITLE_HEIGHT + GAP;

        // Search field
        TextFieldWidget searchField = new TextFieldWidget();
        searchField.pos(PADDING, curY);
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
        panel.child(searchField);
        curY += SEARCH_HEIGHT + GAP;

        // Build gene scroll widgets
        int scrollInset = 2;
        int scrollWidth = CONTENT_WIDTH - scrollInset * 2;
        int geneScrollHeight = GENE_SCROLL_HEIGHT - scrollInset * 2;
        for (BreedingSystem system : systems) {
            GeneScrollWidget scrollWidget = new GeneScrollWidget(system, isNEI, player, syncManager);
            scrollWidget.size(scrollWidth, geneScrollHeight);
            scrollWidgets.add(scrollWidget);
        }

        // Gene scroll area background
        int geneY = curY;
        ParentWidget<?> geneAreaBg = new ParentWidget<>();
        geneAreaBg.pos(PADDING, geneY);
        geneAreaBg.size(CONTENT_WIDTH, GENE_SCROLL_HEIGHT);
        geneAreaBg.background(GuiTextures.DISPLAY);
        panel.child(geneAreaBg);

        // Single PagedWidget combining gene scroll + filter buttons per system
        int combinedHeight = GENE_SCROLL_HEIGHT + GAP + FILTER_HEIGHT;
        PagedWidget<?> pagedWidget = new PagedWidget<>().pos(PADDING, geneY).size(CONTENT_WIDTH, combinedHeight)
                .controller(controller);
        for (int s = 0; s < systems.size(); s++) {
            BreedingSystem system = systems.get(s);
            GeneScrollWidget scrollWidget = scrollWidgets.get(s);

            ParentWidget<?> page = new ParentWidget<>();
            page.size(CONTENT_WIDTH, combinedHeight);

            // Gene scroll inside the page
            scrollWidget.pos(scrollInset, scrollInset);
            page.child(scrollWidget);

            // Filter buttons below gene scroll
            ParentWidget<?> filterPage = buildFilterPage(system, scrollWidget, CONTENT_WIDTH, FILTER_HEIGHT);
            filterPage.pos(0, GENE_SCROLL_HEIGHT + GAP);
            page.child(filterPage);

            pagedWidget.addPage(page);
        }
        panel.child(pagedWidget);
        curY += combinedHeight + SECTION_GAP;

        // Player inventory, centered
        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_PADDING, curY));

        return panel;
    }

    private static ParentWidget<?> buildFilterPage(BreedingSystem system, GeneScrollWidget scrollWidget, int width,
            int height) {
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
            fx += btnWidth + btnGap;
        }

        return page;
    }
}
