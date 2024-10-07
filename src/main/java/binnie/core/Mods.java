package binnie.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class Mods {

    public static final Mod forestry = new Mod("Forestry");
    public static final Mod ic2 = new Mod("IC2");
    public static final Mod botania = new Mod("Botania");
    public static final Mod extraBiomes = new Mod("ExtrabiomesXL");
    public static final Mod dreamcraft = new Mod("dreamcraft");

    private static Item findItem(String modId, String name) {
        Item stack = GameRegistry.findItem(modId, name);
        if (stack == null && forestry.matchID(modId)) {
            throw new RuntimeException("Item not found: " + modId + ":" + name);
        }
        return stack;
    }

    private static ItemStack findItemStack(String modId, String name, int stackSize) {
        ItemStack stack = GameRegistry.findItemStack(modId, name, stackSize);
        if (stack == null && forestry.matchID(modId)) {
            throw new RuntimeException("Stack not found: " + modId + ":" + name);
        }
        return stack;
    }

    private static Block findBlock(String modId, String name) {
        Block stack = GameRegistry.findBlock(modId, name);
        if (stack == null && forestry.matchID(modId)) {
            throw new RuntimeException("Block not found: " + modId + ":" + name);
        }
        return stack;
    }

    public static class Mod {

        private final String modid;
        private final boolean isLoaded;

        private Mod(String id) {
            modid = id;
            isLoaded = Loader.isModLoaded(id);
        }

        public Item item(String name) {
            return findItem(modid, name);
        }

        public Block block(String name) {
            return findBlock(modid, name);
        }

        public ItemStack stack(String name, int stackSize) {
            return findItemStack(modid, name, stackSize);
        }

        public ItemStack stack(String name) {
            return stack(name, 1);
        }

        public ItemStack stack(String string, int i, int j) {
            return new ItemStack(item(string), i, j);
        }

        public boolean active() {
            return isLoaded;
        }

        public boolean matchID(final String id) {
            return modid.equals(id);
        }
    }
}
