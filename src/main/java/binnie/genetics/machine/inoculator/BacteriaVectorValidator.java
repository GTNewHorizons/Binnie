package binnie.genetics.machine.inoculator;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.Validator;
import binnie.genetics.item.GeneticLiquid;

public class BacteriaVectorValidator extends Validator<FluidStack> {

    @Override
    public boolean isValid(FluidStack object) {
        return GeneticLiquid.BacteriaVector.get(1).isFluidEqual(object);
    }

    @Override
    public String getTooltip() {
        return GeneticLiquid.BacteriaVector.getName();
    }
}
