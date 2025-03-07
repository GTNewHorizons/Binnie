package binnie.genetics.machine;

import net.minecraft.item.ItemStack;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.genetics.Genetics;
import binnie.genetics.machine.acclimatiser.AcclimatiserPackage;
import binnie.genetics.machine.analyser.AnalyserPackage;
import binnie.genetics.machine.genepool.GenepoolPackage;
import binnie.genetics.machine.incubator.IncubatorPackage;
import binnie.genetics.machine.lab.LabMachinePackage;

public enum LaboratoryMachine implements IMachineType {

    LabMachine(LabMachinePackage.class),
    Analyser(AnalyserPackage.class),
    Incubator(IncubatorPackage.class),
    Genepool(GenepoolPackage.class),
    Acclimatiser(AcclimatiserPackage.class);

    private final Class<? extends MachinePackage> clss;

    LaboratoryMachine(Class<? extends MachinePackage> clss) {
        this.clss = clss;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return clss;
    }

    public ItemStack get(int i) {
        return new ItemStack(Genetics.packageLabMachine.getBlock(), i, ordinal());
    }
}
