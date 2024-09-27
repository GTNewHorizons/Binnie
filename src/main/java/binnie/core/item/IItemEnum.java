package binnie.core.item;

import net.minecraft.item.ItemStack;

public interface IItemEnum {

    default boolean isActive() {
        return true;
    }

    String getName(ItemStack itemStack);

    int ordinal();

    ItemStack get(int count);
}
