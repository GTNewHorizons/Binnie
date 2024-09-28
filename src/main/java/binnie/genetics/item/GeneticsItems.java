package binnie.genetics.item;

import net.minecraft.item.Item;

import binnie.core.item.DamageItems;

public class GeneticsItems {

    public static final Item serum = new ItemSerum();
    public static final Item serumArray = new ItemSerumArray();
    public static final Item sequencer = new ItemSequence();
    public static final Item database = new ItemDatabase();
    public static final Item analyst = new ItemAnalyst();
    public static final Item registry = new ItemRegistry();
    public static final Item masterRegistry = new ItemMasterRegistry();
    public static DamageItems itemGenetics = new GeneticsMisc();

    private final Item item;

    GeneticsItems(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
