package binnie.genetics.machine.inoculator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.common.GeneManipulationUtil;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;

public class InoculatorComponentLogic extends ComponentProcessSetCost implements IProcess {

    private float bacteriaDrain = 0.0f;

    public InoculatorComponentLogic(Machine machine) {
        super(machine, Inoculator.RF_COST, Inoculator.TIME_PERIOD);
    }

    @Override
    public int getProcessLength() {
        return super.getProcessLength() * getNumberOfGenes();
    }

    @Override
    public int getProcessEnergy() {
        return super.getProcessEnergy() * getNumberOfGenes();
    }

    private int getNumberOfGenes() {
        ItemStack serum = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
        if (serum == null) {
            return 1;
        }
        return Engineering.getGenes(serum).length;
    }

    @Override
    public String getTooltip() {
        int n = getNumberOfGenes();
        if (n > 1) {
            return I18N.localise("genetics.machine.inoculator.inoculatingWithGenes", n);
        }
        return I18N.localise("genetics.machine.inoculator.inoculatingWithGene");
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Inoculator.SLOT_TARGET)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.inoculator.error.noIndividual"),
                    Inoculator.SLOT_TARGET);
        }
        if (getUtil().isSlotEmpty(Inoculator.SLOT_SERUM_VIAL)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.inoculator.error.noSerum"),
                    Inoculator.SLOT_SERUM_VIAL);
        }
        if (getUtil().isTankEmpty(Inoculator.TANK_VECTOR)) {
            return new ErrorState.InsufficientLiquid(
                    I18N.localise("genetics.machine.inoculator.error.noLiquid"),
                    Inoculator.TANK_VECTOR);
        }

        ErrorState state = isValidSerum();
        if (state != null) {
            return state;
        }

        ItemStack stack = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
        if (stack != null && Engineering.getCharges(stack) == 0) {
            return new ErrorState(
                    I18N.localise("genetics.machine.inoculator.error.emptySerum.title"),
                    I18N.localise("genetics.machine.inoculator.error.emptySerum"));
        }
        return super.canWork();
    }

    public ErrorState isValidSerum() {
        ItemStack serum = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
        ItemStack target = getUtil().getStack(Inoculator.SLOT_TARGET);
        return GeneManipulationUtil.validateSerum(serum, target, "genetics.machine.inoculator.error");
    }

    @Override
    public ErrorState canProgress() {
        return super.canProgress();
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        ItemStack serum = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
        ItemStack target = getUtil().getStack(Inoculator.SLOT_TARGET);
        IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
        if (!ind.isAnalyzed()) {
            ind.analyze();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            ind.writeToNBT(nbttagcompound);
            target.setTagCompound(nbttagcompound);
        }

        IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
        for (IGene gene : genes) {
            Inoculator.setGene(gene, target, 0);
            Inoculator.setGene(gene, target, 1);
        }
        getUtil().damageItem(Inoculator.SLOT_SERUM_VIAL, 1);
    }

    @Override
    protected void onTickTask() {
        bacteriaDrain += 15.0f * getProgressPerTick() / 100.0f;
        if (bacteriaDrain >= 1.0f) {
            getUtil().drainTank(Inoculator.TANK_VECTOR, 1);
            bacteriaDrain--;
        }
    }
}
