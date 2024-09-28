package binnie.extrabees.products;

import static binnie.extrabees.ExtraBees.EB_MODID;
import static binnie.extrabees.item.EBItems.honeyDrop;

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

public class DropItems extends DamageItems {

    public enum Items {

        ENERGY("energy", new Color(0x9c4972), new Color(0xe37171), ""),
        ACID("acid", new Color(0x4bb541), new Color(0x49de3c), "acid"),
        POISON("poison", new Color(0xd106b9), new Color(0xff03e2), "poison"),
        APPLE("apple", new Color(0xc75252), new Color(0xc92a2a), "juice"),
        // CITRUS,
        ICE("ice", new Color(0xaee8e2), new Color(0x96fff5), "liquidnitrogen"),
        MILK("milk", new Color(0xe0e0e0), new Color(0xffffff), "milk"),
        SEED("seed", new Color(0x7cc272), new Color(0xc2bea7), "seedoil"),
        ALCOHOL("alcohol", new Color(0xdbe84d), new Color(0xa5e84d), "short.mead"),
        // FRUIT,
        // VEGETABLE,
        // PUMPKIN,
        // MELON,
        RED("red", new Color(0xcc4c4c), new Color(0xff0000), "for.honey"),
        YELLOW("yellow", new Color(0xe5e533), new Color(0xffdd00), "for.honey"),
        BLUE("blue", new Color(0x99b2f2), new Color(0x0022ff), "for.honey"),
        GREEN("green", new Color(0x667f33), new Color(0x009900), "for.honey"),
        BLACK("black", new Color(0x191919), new Color(0x575757), "for.honey"),
        WHITE("white", new Color(0xd6d6d6), new Color(0xffffff), "for.honey"),
        BROWN("brown", new Color(0x7f664c), new Color(0x5c350f), "for.honey"),
        ORANGE("orange", new Color(0xf2b233), new Color(0xff9d00), "for.honey"),
        CYAN("cyan", new Color(0x4c99b2), new Color(0x00ffe5), "for.honey"),
        PURPLE("purple", new Color(0xb266e5), new Color(0xae00ff), "for.honey"),
        GRAY("gray", new Color(0x4c4c4c), new Color(0xbababa), "for.honey"),
        LIGHTBLUE("lightblue", new Color(0x99b2f2), new Color(0x009dff), "for.honey"),
        PINK("pink", new Color(0xf2b2cc), new Color(0xff80df), "for.honey"),
        LIMEGREEN("limegreen", new Color(0x7fcc19), new Color(0x00ff08), "for.honey"),
        MAGENTA("magenta", new Color(0xe57fd8), new Color(0xff00cc), "for.honey"),
        LIGHTGRAY("lightgray", new Color(0x999999), new Color(0xc9c9c9), "for.honey");

        final String name;
        final String liquid;
        final int primary;
        final int secondary;
        ItemStack remnant;
        public static final Items[] VALUES = values();

        Items(String name, Color primary, Color secondary, String liquid) {
            this.name = name;
            this.liquid = liquid;
            this.primary = primary.getRGB();
            this.secondary = secondary.getRGB();
        }

        public ItemStack get(int count) {
            return honeyDrop.getStack(count, ordinal());
        }

        public void addRemnant(ItemStack stack) {
            remnant = stack;
        }

        public void addRecipe() {
            FluidStack liquid = Binnie.Liquid.getLiquidStack(name, 200);
            if (liquid == null) {
                return;
            }
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] { get(1) }, liquid, remnant, 100);
        }
    }

    private IIcon drop;
    private IIcon shine;

    public DropItems() {
        super(
                Tabs.tabApiculture,
                EB_MODID,
                "honeyDrop",
                true,
                Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));

        setMaxStackSize(64);
        setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(String name) {
        return "extrabees.item.honeydrop." + name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int indexColor) {
        final int damage = stack.getItemDamage();
        final CombItems.Items comb = damage < CombItems.Items.VALUES.length
                ? CombItems.Items.VALUES[stack.getItemDamage()]
                : null;

        if (comb == null) return 0xffffff;
        return indexColor == 0 ? comb.primary : comb.secondary;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass > 0 ? drop : shine;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        drop = register.registerIcon("forestry:honeyDrop.0");
        shine = register.registerIcon("forestry:honeyDrop.1");
    }
}
