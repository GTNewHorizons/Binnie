package binnie.core.machines.power;

import binnie.core.machines.IMachine;

public class ComponentProcessSetCost extends ComponentProcess {

    private final int processLength;
    private final int processEnergy;

    public ComponentProcessSetCost(IMachine machine, int rfCost, int timePeriod) {
        super(machine);
        processLength = timePeriod;
        processEnergy = rfCost;
    }

    @Override
    public int getProcessLength() {
        return processLength;
    }

    @Override
    public int getProcessEnergy() {
        return processEnergy;
    }
}
