package binnie.extratrees.item;

import static binnie.extratrees.ExtraTrees.ET_MODID;
import static binnie.extratrees.item.ETItems.itemMisc;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import binnie.core.item.DamageItems;
import forestry.api.core.Tabs;

public class ETMisc extends DamageItems {

    public enum Items {

        // Binnie never enabled this one, so it does nothing
        // TODO: re-enable or remove this
        CarpentryHammer("carpentryHammer"),
        Sawdust("sawdust"),
        Bark("bark"),
        ProvenGear("provenGear"),
        WoodWax("woodWax"),
        GlassFitting("glassFitting");

        final String name;
        private static final Items[] VALUES = values();

        Items(String name) {
            this.name = name;
        }

        public ItemStack get(int count) {
            return itemMisc.getStack(count, ordinal());
        }
    }

    protected ETMisc() {
        super(Tabs.tabArboriculture, ET_MODID, Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));
    }
}
