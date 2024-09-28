package binnie.extratrees.item;

import static binnie.extratrees.ExtraTrees.ET_MODID;
import static binnie.extratrees.item.ETItems.itemFood;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.item.DamageItems;
import binnie.core.util.I18N;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;

/**
 * This class doesn't extend {@link DamageItems} because it needs to extend {@link net.minecraft.item.ItemFood}, and
 * Java doesn't allow multiple inheritance.
 */
public class FoodItems extends ItemFood {

    public enum Items {

        Crabapple("Crabapple", 2),
        Orange("Orange", 4),
        Kumquat("Kumquat", 2),
        Lime("Lime", 2),
        WildCherry("WildCherry", 2),
        SourCherry("SourCherry", 2),
        BlackCherry("BlackCherry", 2),
        Blackthorn("Blackthorn", 3),
        CherryPlum("CherryPlum", 3),
        Almond("Almond", 1),
        Apricot("Apricot", 4),
        Grapefruit("Grapefruit", 4),
        Peach("Peach", 4),
        Satsuma("Satsuma", 3),
        BuddhaHand("BuddhaHand", 3),
        Citron("Citron", 3),
        FingerLime("FingerLime", 3),
        KeyLime("KeyLime", 2),
        Manderin("Manderin", 3),
        Nectarine("Nectarine", 3),
        Pomelo("Pomelo", 3),
        Tangerine("Tangerine", 3),
        Pear("Pear", 4),
        SandPear("SandPear", 2),
        Hazelnut("Hazelnut", 2),
        Butternut("Butternut", 1),
        Beechnut("Beechnut", 0),
        Pecan("Pecan", 0),
        Banana("Banana", 4),
        RedBanana("RedBanana", 4),
        Plantain("Plantain", 2),
        BrazilNut("BrazilNut", 0),
        Fig("Fig", 2),
        Acorn("Acorn", 0),
        Elderberry("Elderberry", 1),
        Olive("Olive", 1),
        GingkoNut("GingkoNut", 1),
        Coffee("Coffee", 0),
        OsangeOrange("OsangeOrange", 1),
        Clove("Clove", 0),
        Papayimar("Papayimar", 8),
        Blackcurrant("Blackcurrant", 2),
        Redcurrant("Redcurrant", 2),
        Blackberry("Blackberry", 2),
        Raspberry("Raspberry", 2),
        Blueberry("Blueberry", 2),
        Cranberry("Cranberry", 2),
        Juniper("Juniper", 0),
        Gooseberry("Gooseberry", 2),
        GoldenRaspberry("GoldenRaspberry", 2),
        Coconut("Coconut", 2),
        Cashew("Cashew", 0),
        Avacado("Avacado", 2),
        Nutmeg("Nutmeg", 0),
        Allspice("Allspice", 0),
        Chilli("Chilli", 2),
        StarAnise("StarAnise", 0),
        Mango("Mango", 4),
        Starfruit("Starfruit", 2),
        Candlenut("Candlenut", 0);

        final String name;
        final int hunger;
        private static final Items[] VALUES = values();

        Items(String name, int hunger) {
            this.name = name;
            this.hunger = hunger;
        }

        public ItemStack get(int count) {
            return itemFood.getStack(count, ordinal());
        }

        public void addJuice(int time, int amount, int mulch) {
            RecipeManagers.squeezerManager.addRecipe(
                    time,
                    new ItemStack[] { get(1) },
                    Binnie.Liquid.getLiquidStack("juice", amount),
                    Mods.forestry.stack("mulch"),
                    mulch);
        }

        public void addOil(int time, int amount, int mulch) {
            RecipeManagers.squeezerManager.addRecipe(
                    time,
                    new ItemStack[] { get(1) },
                    Binnie.Liquid.getLiquidStack("seedoil", amount),
                    Mods.forestry.stack("mulch"),
                    mulch);
        }

        Items addOredict(String string) {
            OreDictionary.registerOre("crop" + string, get(1));
            return this;
        }

        public static void registerOreDictionary() {
            Crabapple.addOredict("Apple").addOredict("Crabapple");
            Orange.addOredict("Orange");
            Kumquat.addOredict("Kumquat");
            Lime.addOredict("Lime");
            WildCherry.addOredict("Cherry").addOredict("WildCherry");
            SourCherry.addOredict("Cherry").addOredict("SourCherry");
            BlackCherry.addOredict("Cherry").addOredict("BlackCherry");
            Blackthorn.addOredict("Blackthorn");
            CherryPlum.addOredict("Plum").addOredict("CherryPlum");
            Almond.addOredict("Almond");
            Apricot.addOredict("Apricot");
            Grapefruit.addOredict("Grapefruit");
            Peach.addOredict("Peach");
            Satsuma.addOredict("Satsuma").addOredict("Orange");
            BuddhaHand.addOredict("BuddhaHand").addOredict("Citron");
            Citron.addOredict("Citron");
            FingerLime.addOredict("Lime").addOredict("FingerLime");
            KeyLime.addOredict("KeyLime").addOredict("Lime");
            Manderin.addOredict("Orange").addOredict("Manderin");
            Nectarine.addOredict("Peach").addOredict("Nectarine");
            Pomelo.addOredict("Pomelo");
            Tangerine.addOredict("Tangerine").addOredict("Orange");
            Pear.addOredict("Pear");
            SandPear.addOredict("SandPear");
            Hazelnut.addOredict("Hazelnut");
            Butternut.addOredict("Butternut").addOredict("Walnut");
            Beechnut.addOredict("Beechnut");
            Pecan.addOredict("Pecan");
            Banana.addOredict("Banana");
            RedBanana.addOredict("RedBanana").addOredict("Banana");
            Plantain.addOredict("Plantain");
            BrazilNut.addOredict("BrazilNut");
            Fig.addOredict("Fig");
            Acorn.addOredict("Acorn");
            Elderberry.addOredict("Elderberry");
            Olive.addOredict("Olive");
            GingkoNut.addOredict("GingkoNut");
            Coffee.addOredict("Coffee");
            OsangeOrange.addOredict("OsangeOrange");
            Clove.addOredict("Clove");
            Papayimar.addOredict("Papayimar");
            Blackcurrant.addOredict("Blackcurrant");
            Redcurrant.addOredict("Redcurrant");
            Blackberry.addOredict("Blackberry");
            Raspberry.addOredict("Raspberry");
            Blueberry.addOredict("Blueberry");
            Cranberry.addOredict("Cranberry");
            Juniper.addOredict("Juniper");
            Gooseberry.addOredict("Gooseberry");
            GoldenRaspberry.addOredict("GoldenRaspberry");
            Coconut.addOredict("Coconut");
            Cashew.addOredict("Cashew");
            Avacado.addOredict("Avacado");
            Nutmeg.addOredict("Nutmeg");
            Chilli.addOredict("Chilli");
            StarAnise.addOredict("StarAnise");
            Mango.addOredict("Mango");
            Starfruit.addOredict("Starfruit");
            Candlenut.addOredict("Candlenut");
        }
    }

    private final String[] NAMES;
    private final IIcon[] ICONS;
    private final String modid;

    protected FoodItems() {
        super(0, 0, false);
        final String name = "food";

        setCreativeTab(Tabs.tabArboriculture);
        setHasSubtypes(true);
        setUnlocalizedName(name);

        NAMES = Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new);
        ICONS = new IIcon[NAMES.length];
        this.modid = ET_MODID;

        GameRegistry.registerItem(this, name);
    }

    public ItemStack getStack(int count, int damage) {
        return new ItemStack(this, count, damage);
    }

    public String getName(int damage) {
        return damage < NAMES.length ? NAMES[damage] : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item thiz, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < NAMES.length; ++i) {
            list.add(new ItemStack(thiz, 1, i));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        final int damage = stack.getItemDamage();
        return (damage < NAMES.length) ? I18N.localise("for.extratrees.item.food." + NAMES[damage].toLowerCase())
                : "null";
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconFromDamage(stack.getItemDamage());
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return (damage < ICONS.length && damage >= 0) ? ICONS[damage] : null;
    }

    public String getIconName(int damage) {
        return NAMES[damage];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < NAMES.length; ++i) {
            ICONS[i] = register.registerIcon(modid + ":food/" + getIconName(i));
        }
    }

    /**
     * I don't quite understand why these next two functions were overridden instead of using something with deobfed
     * names. In the vain hope of not breaking things, I leave them unchanged.
     */

    @Override
    public int func_150905_g(ItemStack stack) {
        return Items.VALUES[stack.getItemDamage()].hunger;
    }

    @Override
    public float func_150906_h(ItemStack stack) {
        return 3.0f;
    }
}
