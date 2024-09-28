package binnie.botany.items;

import static binnie.botany.Botany.BOTANY_MODID;
import static binnie.botany.items.BotanyItems.misc;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import binnie.botany.CreativeTabBotany;
import binnie.core.item.DamageItems;

public class BotanyMisc extends DamageItems {

    public enum Items {

        AshPowder("powderAsh"),
        PulpPowder("powderPulp"),
        MulchPowder("powderMulch"),
        SulphurPowder("powderSulphur"),
        FertiliserPowder("powderFertiliser"),
        CompostPowder("powderCompost"),
        Mortar("mortar"),
        Weedkiller("weedkiller");

        final String name;
        private static final Items[] VALUES = values();

        Items(String name) {
            this.name = name;
        }

        public ItemStack get(int count) {
            return misc.getStack(count, ordinal());
        }
    }

    public BotanyMisc() {
        super(
                CreativeTabBotany.instance,
                BOTANY_MODID,
                Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));
    }
}
