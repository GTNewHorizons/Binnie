package binnie.genetics.machine.acclimatiser;

import net.minecraft.item.ItemStack;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;

public class AcclimatiserItemSlotValidator extends SlotValidator {

    public AcclimatiserItemSlotValidator() {
        super(null);
    }

    @Override
    public boolean isValid(ItemStack stack) {
        for (ToleranceType type : ToleranceType.values()) {
            if (type.hasEffect(stack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTooltip() {
        return I18N.localise("genetics.machine.acclimatiser.acclimatizingItems");
    }
}
