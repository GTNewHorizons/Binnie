package binnie.genetics.machine.polymeriser;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.machine.ModuleMachine;

public class UnfilledSerumSlotValidator extends SlotValidator {

    public UnfilledSerumSlotValidator() {
        super(ModuleMachine.IconSerum);
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return itemStack.getItem() instanceof IItemChargable;
    }

    @Override
    public String getTooltip() {
        return I18N.localise("genetics.machine.polymeriser.unfilledSerum");
    }
}
