package binnie.genetics.machine.polymeriser;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.Validator;
import binnie.core.util.I18N;
import binnie.genetics.item.GeneticLiquid;

class PolymerisingBacteriaValidator extends Validator<FluidStack> {

    @Override
    public boolean isValid(FluidStack itemStack) {
        return GeneticLiquid.BacteriaPoly.get(1).isFluidEqual(itemStack);
    }

    @Override
    public String getTooltip() {
        return I18N.localise("fluid.binnie.bacteriaPoly");
    }
}
