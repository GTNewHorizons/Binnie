package binnie.genetics.machine.isolator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import net.minecraft.item.ItemStack;

import binnie.core.genetics.Gene;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.extrabees.config.EBConfigMachines;
import binnie.genetics.item.ItemSequence;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

public class IsolatorComponentLogic extends ComponentProcessSetCost implements IProcess {

    protected float enzymePerProcess = 0.5f;
    protected float ethanolPerProcess = 10.0f;

    public IsolatorComponentLogic(Machine machine) {
        super(machine, Isolator.RF_COST, Isolator.TIME_PERIOD);
    }

    @Override
    public ErrorState canWork() {
        if (getUtil().isSlotEmpty(Isolator.SLOT_TARGET)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.isolator.error.noIndividual"),
                    Isolator.SLOT_TARGET);
        }
        // TODO check slot id in validation
        if (!getUtil().isSlotEmpty(Isolator.SLOT_RESULT)) {
            return new ErrorState.NoSpace(
                    I18N.localise("genetics.machine.isolator.error.noRoom"),
                    Isolator.SLOT_FINISHED);
        }
        if (getUtil().isSlotEmpty(Isolator.SLOT_SEQUENCER_VIAL)) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.isolator.error.noVials"),
                    Isolator.SLOT_SEQUENCER_VIAL);
        }
        return super.canWork();
    }

    @Override
    public ErrorState canProgress() {
        if (!getUtil().liquidInTank(Isolator.TANK_ETHANOL, (int) ethanolPerProcess)) {
            return new ErrorState.InsufficientLiquid(
                    I18N.localise("genetics.machine.isolator.error.noLiquid"),
                    Isolator.TANK_ETHANOL);
        }
        if (getUtil().getSlotCharge(Isolator.SLOT_ENZYME) == 0.0f) {
            return new ErrorState.NoItem(
                    I18N.localise("genetics.machine.isolator.error.noEnzyme"),
                    Isolator.SLOT_ENZYME);
        }
        return super.canProgress();
    }

    @Override
    protected void onFinishTask() {
        super.onFinishTask();
        Random rand = getMachine().getWorld().rand;
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(getUtil().getStack(Isolator.SLOT_TARGET));
        if (root == null) {
            return;
        }

        IIndividual individual = root.getMember(getUtil().getStack(Isolator.SLOT_TARGET));
        if (individual == null) {
            return;
        }

        final IChromosomeType[] karyo = root.getKaryotype();

        final IChromosome[] chromosomes = individual.getGenome().getChromosomes();
        if (Arrays.stream(chromosomes).allMatch(Objects::isNull)) {
            return;
        }

        IChromosomeType chromosome;
        do {
            chromosome = karyo[rand.nextInt(karyo.length)];
        } while (chromosomes[chromosome.ordinal()] == null);

        final IAllele allele = rand.nextBoolean() ? individual.getGenome().getActiveAllele(chromosome)
                : individual.getGenome().getInactiveAllele(chromosome);
        final Gene gene = new Gene(allele, chromosome, root);

        ItemStack serum = ItemSequence.create(gene);
        getUtil().setStack(Isolator.SLOT_RESULT, serum);
        getUtil().decreaseStack(Isolator.SLOT_SEQUENCER_VIAL, 1);
        if (rand.nextFloat() < EBConfigMachines.isolatorConsumptionChance) {
            getUtil().decreaseStack(Isolator.SLOT_TARGET, 1);
        }
        getUtil().drainTank(Isolator.TANK_ETHANOL, (int) ethanolPerProcess);
    }

    @Override
    protected void onTickTask() {
        getMachine().getInterface(IChargedSlots.class)
                .alterCharge(Isolator.SLOT_ENZYME, -enzymePerProcess * getProgressPerTick() / 100.0f);
    }
}
