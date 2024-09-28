package binnie.extrabees.products;

import static binnie.extrabees.ExtraBees.EB_MODID;
import static binnie.extrabees.item.EBItems.propolis;

import java.awt.Color;
import java.util.Arrays;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import binnie.Binnie;
import binnie.core.item.DamageItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;

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
                EB_MODID,
                "propolis",
                true,
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("forestry:propolis.0");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return itemIcon;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return itemIcon;
    }
}
