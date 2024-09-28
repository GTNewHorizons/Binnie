package binnie.extrabees.item;

import binnie.core.item.DamageItems;
import binnie.extrabees.genetics.items.ItemDictionary;
import binnie.extrabees.products.ItemHoneyComb;
import binnie.extrabees.products.ItemHoneyDrop;
import binnie.extrabees.products.Propolis;
import net.minecraft.item.Item;

public class EBItems {
    public final static DamageItems itemMisc = new EBMisc();
    public final static Item comb = new ItemHoneyComb();
    public final static Propolis propolis = new Propolis();
    public final static Item honeyDrop = new ItemHoneyDrop();
    public static Item dictionary = new ItemDictionary();
}
