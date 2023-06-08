package binnie.extrabees.apiary.machine.unlighting;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class UnlightingComponentModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {

    public UnlightingComponentModifier(Machine machine) {
        super(machine);
    }

    @Override
    public boolean isSelfUnlighted() {
        return true;
    }
}
