package binnie.extratrees.item;

import binnie.core.item.DamageItems;
import net.minecraft.item.Item;

public class ETItems {
    public final static DamageItems itemMisc = new ETMisc();
    public final static Item itemDictionary = new ItemDictionary();
    public static Item itemDictionaryLepi;
    public static FoodItems itemFood = new FoodItems();
    public static Item itemHammer = new ItemHammer(false);
    public static Item itemDurableHammer = new ItemHammer(true);
}
