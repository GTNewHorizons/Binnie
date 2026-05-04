package binnie.genetics.machine.common;

import net.minecraft.item.ItemStack;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

public class IndividualInoculateValidator extends SlotValidator {

    private final String tooltipKey;

    public IndividualInoculateValidator(String tooltipKey) {
        super(null);
        this.tooltipKey = tooltipKey;
    }

    @Override
    public boolean isValid(ItemStack object) {
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
        if (root == null) {
            return false;
        }
        BreedingSystem system = Binnie.Genetics.getSystem(root);
        return system != null && system.isDNAManipulable(object);
    }

    @Override
    public String getTooltip() {
        return I18N.localise(tooltipKey);
    }
}
