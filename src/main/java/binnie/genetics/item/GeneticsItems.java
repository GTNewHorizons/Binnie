package binnie.genetics.item;

import net.minecraft.item.Item;

public enum GeneticsItems {

    SERUM(new ItemSerum()),
    SERUM_ARRAY(new ItemSerumArray()),
    SEQUENCER(new ItemSequence()),
    DATABASE(new ItemDatabase()),
    ANALYST(new ItemAnalyst()),
    REGISTRY(new ItemRegistry()),
    MASTER_REGISTRY(new ItemMasterRegistry());

    private final Item item;

    GeneticsItems(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
