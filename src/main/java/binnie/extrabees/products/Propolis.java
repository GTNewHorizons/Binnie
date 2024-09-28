package binnie.extrabees.products;

import static binnie.extrabees.item.EBItems.propolis;

import binnie.Binnie;
import binnie.core.item.DamageItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;
import java.awt.Color;
import java.util.Arrays;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class Propolis extends DamageItems {
    public enum Items {
        WATER(new Color(0x24b3c9), new Color(0xc2bea7), "water"),
        OIL(new Color(0x172f33), new Color(0xc2bea7), "oil"),
        FUEL(new Color(0xa38d12), new Color(0xc2bea7), "fuel"),
        CREOSOTE(new Color(0x877501), new Color(0xbda613), "creosote");

        final String name;
        final int primary;
        final int secondary;
        public static final Items[] VALUES = values();

        Items(Color primary, Color secondary, String name) {
            this.name = name;
            this.primary = primary.getRGB();
            this.secondary = secondary.getRGB();
        }

        public ItemStack get(int count) {
            return propolis.getStack(count, ordinal());
        }

        public void addRecipe() {
            FluidStack liquid = Binnie.Liquid.getLiquidStack(name, 500);
            if (liquid == null) return;

            RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] { get(1) }, liquid, null, 0);
        }
    }

    public Propolis() {
        // I don't like this, but the modid is actually only used for registering icons and names, so it's fine
        super(
                Tabs.tabApiculture,
                "forestry",
                "propolis",
                Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));

        setMaxStackSize(64);
        setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(String name) {
        return "extrabees.item.propolis." + name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int layer) {
        final Items i = Items.VALUES[itemStack.getItemDamage()];
        return layer == 0 ? i.primary : i.secondary;
    }

    @Override
    public String getIconName(int damage) {
        return "propolis.0";
    }
}
