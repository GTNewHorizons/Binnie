package binnie.genetics.item;

import binnie.core.item.DamageItems;
import net.minecraft.item.Item;

public enum GeneticsItems {

    SERUM(new ItemSerum()),
    SERUM_ARRAY(new ItemSerumArray()),
    SEQUENCER(new ItemSequence()),
    DATABASE(new ItemDatabase()),
    ANALYST(new ItemAnalyst()),
    REGISTRY(new ItemRegistry()),
    MASTER_REGISTRY(new ItemMasterRegistry());

    // TODO: put this back in Genetics once the itemparser is killed
    public static DamageItems itemGenetics;

    private final Item item;

    GeneticsItems(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
