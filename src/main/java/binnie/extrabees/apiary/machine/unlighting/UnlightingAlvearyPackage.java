package binnie.extrabees.apiary.machine.unlighting;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeTexture;

public class UnlightingAlvearyPackage extends AlvearyMachine.AlvearyPackage implements IMachineInformation {

    public UnlightingAlvearyPackage() {
        super("unlighting", ExtraBeeTexture.AlvearyUnlighting.getTexture(), false);
    }

    @Override
    public void createMachine(Machine machine) {
        new UnlightingComponentModifier(machine);
    }
}
