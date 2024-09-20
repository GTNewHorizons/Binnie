package binnie.genetics.genetics;

import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.genetics.api.IGene;
import forestry.api.genetics.ISpeciesRoot;

public interface IGeneItem {

    ISpeciesRoot getSpeciesRoot();

    void getInfo(List<String> tooltip);

    int getColour(int color);

    void writeToItem(ItemStack stack);

    void addGene(IGene gene);
}
