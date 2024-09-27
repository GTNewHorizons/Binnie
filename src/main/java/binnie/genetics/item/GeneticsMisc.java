package binnie.genetics.item;

import static binnie.genetics.Genetics.GENETICS_MODID;
import static binnie.genetics.item.GeneticsItems.itemGenetics;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import binnie.core.item.DamageItems;
import binnie.genetics.GeneticsCreativeTab;

public class GeneticsMisc extends DamageItems {

    public enum Items {

        LaboratoryCasing("casingIron"),
        DNADye("dnaDye"),
        FluorescentDye("dyeFluor"),
        Enzyme("enzyme"),
        GrowthMedium("growthMedium"),
        EmptySequencer("sequencerEmpty"),
        EmptySerum("serumEmpty"),
        EmptyGenome("genomeEmpty"),
        Cylinder("cylinderEmpty"),
        IntegratedCircuit("integratedCircuit"),
        IntegratedCPU("integratedCPU"),
        IntegratedCasing("casingCircuit");

        final String name;
        private static final Items[] VALUES = values();

        Items(String name) {
            this.name = name;
        }

        public ItemStack get(int count) {
            return itemGenetics.getStack(count, ordinal());
        }
    }

    protected GeneticsMisc() {
        super(
                GeneticsCreativeTab.instance,
                GENETICS_MODID,
                Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));
    }
}
