package binnie.genetics.machine.common;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.machine.ModuleMachine;

public class SerumSlotValidator extends SlotValidator {

    private final String tooltipKey;

    public SerumSlotValidator(String tooltipKey) {
        super(ModuleMachine.IconSerum);
        this.tooltipKey = tooltipKey;
    }

    @Override
    public boolean isValid(ItemStack itemStack) {
        return itemStack.getItem() instanceof IItemSerum;
    }

    @Override
    public String getTooltip() {
        return I18N.localise(tooltipKey);
    }
}
