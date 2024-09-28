package binnie.extrabees.item;

import binnie.core.item.DamageItems;
import binnie.extrabees.genetics.items.ItemDictionary;
import binnie.extrabees.products.ItemHoneyComb;
import binnie.extrabees.products.ItemHoneyDrop;
import binnie.extrabees.products.ItemPropolis;
import net.minecraft.item.Item;

public class EBItems {
    public final static DamageItems itemMisc = new EBMisc();
    public final static Item comb = new ItemHoneyComb();
    public final static Item propolis = new ItemPropolis();
    public final static Item honeyDrop = new ItemHoneyDrop();
    public static Item dictionary = new ItemDictionary();
}
