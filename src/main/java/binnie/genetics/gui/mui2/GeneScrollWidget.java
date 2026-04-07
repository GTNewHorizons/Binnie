package binnie.genetics.gui.mui2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.ScrollWidget;
import com.cleanroommc.modularui.widget.scroll.VerticalScrollData;
import com.cleanroommc.modularui.widgets.TextWidget;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.genetics.GeneTracker;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;

public class GeneScrollWidget extends ScrollWidget<GeneScrollWidget> {

    private final BreedingSystem system;
    private final boolean isNEI;
    private final EntityPlayer player;
    private final PanelSyncManager syncManager;
    private String filter = "";
    private IChromosomeType chromosomeFilter;

    public GeneScrollWidget(BreedingSystem system, boolean isNEI, EntityPlayer player, PanelSyncManager syncManager) {
        super(new VerticalScrollData());
        this.system = system;
        this.isNEI = isNEI;
        this.player = player;
        this.syncManager = syncManager;
    }

    public void setFilter(String filter) {
        String normalized = filter.toLowerCase();
        if (normalized.equals(this.filter)) return;
        this.filter = normalized;
        rebuildGenes();
    }

    public void setChromosomeFilter(IChromosomeType type) {
        if (this.chromosomeFilter == type) {
            this.chromosomeFilter = null;
        } else {
            this.chromosomeFilter = type;
        }
        rebuildGenes();
    }

    public IChromosomeType getChromosomeFilter() {
        return chromosomeFilter;
    }

    public void forceChromosomeFilter(IChromosomeType type) {
        if (this.chromosomeFilter == type) return;
        this.chromosomeFilter = type;
        rebuildGenes();
    }

    @Override
    public void afterInit() {
        super.afterInit();
        rebuildGenes();
    }

    public void rebuildGenes() {
        removeAll();

        GeneTracker tracker = GeneTracker.getDisplayTracker(player.worldObj, player.getGameProfile());
        Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());
        if (genes == null) {
            getScrollArea().getScrollY().setScrollSize(0);
            scheduleResize();
            return;
        }

        int contentWidth = getArea().width > 0 ? getArea().width - 14 : 154;
        int iconsPerRow = Math.max(1, contentWidth / 18);
        int rowWidth = iconsPerRow * 18;
        int xOffset = (contentWidth - rowWidth) / 2;

        int col = 0;
        int y = 0;

        for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
            if (chromosomeFilter != null && entry.getKey() != chromosomeFilter) {
                continue;
            }
            String chromoName = system.getChromosomeName(entry.getKey());
            boolean chromoMatches = filter.isEmpty() || chromoName.toLowerCase().contains(filter);

            List<IAllele> discovered = new ArrayList<>();
            for (IAllele allele : entry.getValue()) {
                Gene gene = new Gene(allele, entry.getKey(), system.getSpeciesRoot());
                if ((isNEI || tracker.isSequenced(gene))
                        && (chromoMatches || gene.getName().toLowerCase().contains(filter))) {
                    discovered.add(allele);
                }
            }

            if (discovered.isEmpty()) {
                continue;
            }

            col = 0;
            TextWidget<?> label = new TextWidget<>(IKey.str(chromoName));
            label.pos(0, y);
            label.size(contentWidth, 12);
            label.color(0x999999);
            addChild(label, -1);
            y += 12;

            for (IAllele allele : discovered) {
                if (col >= iconsPerRow) {
                    y += 20;
                    col = 0;
                }
                Gene gene = new Gene(allele, entry.getKey(), system.getSpeciesRoot());
                GeneIconWidget icon = new GeneIconWidget(gene, syncManager);
                icon.pos(xOffset + col * 18, y);
                addChild(icon, -1);
                col++;
            }
            y += 24;
        }

        getScrollArea().getScrollY().setScrollSize(y);
        scheduleResize();
    }
}
