package binnie.genetics.machine.common;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.inventory.Validator;
import binnie.genetics.item.GeneticLiquid;

public class GeneticLiquidValidator extends Validator<FluidStack> {

    private final GeneticLiquid liquid;

    public GeneticLiquidValidator(GeneticLiquid liquid) {
        this.liquid = liquid;
    }

    @Override
    public boolean isValid(FluidStack object) {
        return liquid.get(1).isFluidEqual(object);
    }

    @Override
    public String getTooltip() {
        return liquid.getName();
    }
}
