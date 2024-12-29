package binnie.extrabees.apiary;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;

public class ComponentBeeModifier extends MachineComponent implements IBeeModifier, IBeeListener {

    public ComponentBeeModifier(Machine machine) {
        super(machine);
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return 1.0f;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return 0.0f;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    @Override
    public boolean isSealed() {
        return false;
    }

    @Override
    public boolean isSelfLighted() {
        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        return false;
    }

    @Override
    public boolean isHellish() {
        return false;
    }

    @Override
    public void wearOutEquipment(int amount) {
        // ignored
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return 1.0f;
    }

    @Override
    public boolean onPollenRetrieved(IIndividual individual) {
        return false;
    }

    @Override
    public void onQueenDeath() {
        // ignored
    }
}
