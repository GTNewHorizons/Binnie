package binnie.genetics.api;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.ISpeciesRoot;

public interface IItemSerum extends IItemChargable {

    IGene[] getGenes(ItemStack stack);

    ISpeciesRoot getSpeciesRoot(ItemStack stack);

    IGene getGene(ItemStack stack, int chromosome);

    ItemStack addGene(ItemStack stack, IGene gene);
}
