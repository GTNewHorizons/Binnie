package binnie.genetics.api;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INBTTagable;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

public interface IGene extends INBTTagable {

    IChromosomeType getChromosome();

    String getName();

    ISpeciesRoot getSpeciesRoot();

    IAllele getAllele();

    NBTTagCompound getNBTTagCompound();
}
