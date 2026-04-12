package binnie.genetics.machine.common;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import binnie.core.machines.power.ErrorState;
import binnie.core.util.I18N;
import binnie.genetics.api.IGene;
import binnie.genetics.genetics.Engineering;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;

public class GeneManipulationUtil {

    /**
     * Applies a gene to an individual's NBT data. Shared between Inoculator and Splicer.
     *
     * @param gene    the gene to apply
     * @param target  the target individual's item stack
     * @param chromoN 0 for active allele, 1 for inactive allele
     */
    public static void setGene(IGene gene, ItemStack target, int chromoN) {
        int chromosomeID = gene.getChromosome().ordinal();
        if (chromosomeID >= EnumBeeChromosome.HUMIDITY.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
            chromosomeID--;
        }

        Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
        if (!cls.isInstance(gene.getAllele())) {
            return;
        }

        NBTTagCompound beeNBT = target.getTagCompound();
        NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
        NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
        NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
        chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
        target.setTagCompound(beeNBT);
    }

    /**
     * Validates whether the serum in the given slot is applicable to the target individual. Returns null if valid, or
     * an ErrorState describing the problem.
     *
     * @param serum          the serum item stack
     * @param target         the target individual item stack
     * @param errorKeyPrefix localization key prefix (e.g. "genetics.machine.inoculator.error")
     */
    public static ErrorState validateSerum(ItemStack serum, ItemStack target, String errorKeyPrefix) {
        IGene[] genes = Engineering.getGenes(serum);
        if (genes.length == 0) {
            return new ErrorState(
                    I18N.localise(errorKeyPrefix + ".invalidSerum.title"),
                    I18N.localise(errorKeyPrefix + ".invalidSerum.0"));
        }
        if (!genes[0].getSpeciesRoot().isMember(target)) {
            return new ErrorState(
                    I18N.localise(errorKeyPrefix + ".invalidSerum.title"),
                    I18N.localise(errorKeyPrefix + ".invalidSerum.1"));
        }

        IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
        boolean hasAll = true;
        for (IGene gene : genes) {
            if (!hasAll) {
                continue;
            }

            IGenome genome = individual.getGenome();
            IChromosomeType chromosome = gene.getChromosome();
            String geneAlleleUID = gene.getAllele().getUID();
            IAllele a = genome.getActiveAllele(chromosome);
            IAllele b = genome.getInactiveAllele(chromosome);
            hasAll = a.getUID().equals(geneAlleleUID) && b.getUID().equals(geneAlleleUID);
        }

        if (!hasAll) {
            return null;
        }
        return new ErrorState(
                I18N.localise(errorKeyPrefix + ".defunctSerum.title"),
                I18N.localise(errorKeyPrefix + ".defunctSerum"));
    }
}
