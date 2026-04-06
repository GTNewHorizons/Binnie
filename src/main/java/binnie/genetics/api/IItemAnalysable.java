package binnie.genetics.api;

import net.minecraft.item.ItemStack;

public interface IItemAnalysable {

    boolean isAnalysed(ItemStack stack);

    ItemStack analyse(ItemStack stack);
}
