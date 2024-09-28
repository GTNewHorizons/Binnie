package binnie.extrabees.item;

import net.minecraft.item.Item;

import binnie.core.item.DamageItems;
import binnie.extrabees.genetics.items.ItemDictionary;
import binnie.extrabees.products.CombItems;
import binnie.extrabees.products.DropItems;
import binnie.extrabees.products.Propolis;

public class EBItems {

    public final static DamageItems itemMisc = new EBMisc();
    public final static DamageItems comb = new CombItems();
    public final static DamageItems propolis = new Propolis();
    public final static DamageItems honeyDrop = new DropItems();
    public static Item dictionary = new ItemDictionary();
}
