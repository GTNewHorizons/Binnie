package binnie.extrabees.products;

import static binnie.extrabees.ExtraBees.EB_MODID;
import static binnie.extrabees.item.EBItems.comb;
import static binnie.extrabees.item.EBMisc.Items.BlutoniumDust;
import static binnie.extrabees.item.EBMisc.Items.CoalDust;
import static binnie.extrabees.item.EBMisc.Items.CopperDust;
import static binnie.extrabees.item.EBMisc.Items.CyaniteDust;
import static binnie.extrabees.item.EBMisc.Items.DiamondShard;
import static binnie.extrabees.item.EBMisc.Items.EmeraldShard;
import static binnie.extrabees.item.EBMisc.Items.GoldDust;
import static binnie.extrabees.item.EBMisc.Items.IronDust;
import static binnie.extrabees.item.EBMisc.Items.LeadDust;
import static binnie.extrabees.item.EBMisc.Items.NickelDust;
import static binnie.extrabees.item.EBMisc.Items.PlatinumDust;
import static binnie.extrabees.item.EBMisc.Items.RubyShard;
import static binnie.extrabees.item.EBMisc.Items.SapphireShard;
import static binnie.extrabees.item.EBMisc.Items.SilverDust;
import static binnie.extrabees.item.EBMisc.Items.TinDust;
import static binnie.extrabees.item.EBMisc.Items.TitaniumDust;
import static binnie.extrabees.item.EBMisc.Items.TungstenDust;
import static binnie.extrabees.item.EBMisc.Items.YelloriumDust;
import static binnie.extrabees.item.EBMisc.Items.ZincDust;
import static net.minecraft.init.Items.dye;

import binnie.core.Mods;
import binnie.core.item.DamageItems;
import binnie.extrabees.item.EBItems;
import binnie.extrabees.item.EBMisc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class CombItems extends DamageItems {
    public enum Items {
        BARREN("barren", new Color(0x736c44), new Color(0xc2bea7)),
        ROTTEN("rotten", new Color(0x3e5221), new Color(0xb1cc89)),
        BONE("bone", new Color(0xc4c4af), new Color(0xdedec1)),
        OIL("oil", new Color(0x060608), new Color(0x2c2b36)),
        COAL("coal", new Color(0x9e9478), new Color(0x38311e)),
        FUEL("fuel", new Color(0x9c6f40), new Color(0xffc400)),
        WATER("water", new Color(0x2732cf), new Color(0x79a8c9)),
        MILK("milk", new Color(0xd7d9c7), new Color(0xffffff)),
        FRUIT("fruit", new Color(0x7d2934), new Color(0xdb4f62)),
        SEED("seed", new Color(0x344f33), new Color(0x71cc6e)),
        ALCOHOL("alcohol", new Color(0x418521), new Color(0xded94e)),
        STONE("stone", new Color(0x8c8c91), new Color(0xc6c6cc)),
        REDSTONE("redstone", new Color(0xfa9696), new Color(0xe61010)),
        RESIN("resin", new Color(0xffc74f), new Color(0xc98a00)),
        IC2ENERGY("energy", new Color(0xe9f50f), new Color(0x20b3c9)),
        IRON("iron", new Color(0x363534), new Color(0xa87058)),
        GOLD("gold", new Color(0x363534), new Color(0xe6cc0b)),
        COPPER("copper", new Color(0x363534), new Color(0xd16308)),
        TIN("tin", new Color(0x363534), new Color(0xbdb1bd)),
        SILVER("silver", new Color(0x363534), new Color(0xdbdbdb)),
        URANIUM("uranium", new Color(0x1eff00), new Color(0x41ab33)),
        CLAY("clay", new Color(0x6b563a), new Color(0xb0c0d6)),
        OLD("old", new Color(0x453314), new Color(0xb39664)),
        FUNGAL("fungal", new Color(0x6e654b), new Color(0x2b9443)),
        CREOSOTE("creosote", new Color(0x9c810c), new Color(0xbdaa57)),
        LATEX("latex", new Color(0x595541), new Color(0xa8a285)),
        ACIDIC("acidic", new Color(0x348543), new Color(0x14f73e)),
        VENOMOUS("venomous", new Color(0x7d187d), new Color(0xff33ff)),
        SLIME("slime", new Color(0x3b473c), new Color(0x80d185)),
        BLAZE("blaze", new Color(0xff6a00), new Color(0xffcc00)),
        COFFEE("coffee", new Color(0x54381d), new Color(0xb37f4b)),
        GLACIAL("glacial", new Color(0x4e8787), new Color(0xcbf2f2)),
        SHADOW("shadow", new Color(0x000000), new Color(0x361835)),
        LEAD("lead", new Color(0x363534), new Color(0x9a809c)),
        ZINC("zinc", new Color(0x363534), new Color(0xedebff)),
        TITANIUM("titanium", new Color(0x363534), new Color(0xb0aae3)),
        TUNGSTEN("tungsten", new Color(0x363534), new Color(0x131214)),
        PLATINUM("platinum", new Color(0x363534), new Color(0x9a809c)),
        LAPIS("lapis", new Color(0x363534), new Color(0x3d2cdb)),
        EMERALD("emerald", new Color(0x363534), new Color(0x1cff03)),
        RUBY("ruby", new Color(0x363534), new Color(0xd60000)),
        SAPPHIRE("sapphire", new Color(0x363534), new Color(0xa47ff)),
        DIAMOND("diamond", new Color(0x363534), new Color(0x7fbdfa)),
        RED("red", new Color(0xcc4c4c), new Color(0xff0000)),
        YELLOW("yellow", new Color(0xe5e533), new Color(0xffdd00)),
        BLUE("blue", new Color(0x99b2f2), new Color(0x0022ff)),
        GREEN("green", new Color(0x667f33), new Color(0x009900)),
        BLACK("black", new Color(0x191919), new Color(0x575757)),
        WHITE("white", new Color(0xd6d6d6), new Color(0xffffff)),
        BROWN("brown", new Color(0x7f664c), new Color(0x5c350f)),
        ORANGE("orange", new Color(0xf2b233), new Color(0xff9d00)),
        CYAN("cyan", new Color(0x4c99b2), new Color(0x00ffe5)),
        PURPLE("purple", new Color(0xb266e5), new Color(0xae00ff)),
        GRAY("gray", new Color(0x4c4c4c), new Color(0xbababa)),
        LIGHTBLUE("lightblue", new Color(0x99b2f2), new Color(0x009dff)),
        PINK("pink", new Color(0xf2b2cc), new Color(0xff80df)),
        LIMEGREEN("limegreen", new Color(0x7fcc19), new Color(0x00ff08)),
        MAGENTA("magenta", new Color(0xe57fd8), new Color(0xff00cc)),
        LIGHTGRAY("lightgray", new Color(0x999999), new Color(0xc9c9c9)),
        NICKEL("nickel", new Color(0x363534), new Color(0xffdefc)),
        GLOWSTONE("glowstone", new Color(0xa69c5e), new Color(0xe0c409)),
        SALTPETER("saltpeter", new Color(0xa69c5e), new Color(0xe0c409)),
        COMPOST("compost", new Color(0x423308), new Color(0x6b5e3b)),
        SAWDUST("sawdust", new Color(0xbfaa71), new Color(0xf2d37e)),
        CERTUS("certus", new Color(0xc6d0ff), new Color(0x394d63)),
        ENDERPEARL("enderpearl", new Color(0x349786), new Color(0x32620)),
        YELLORIUM("yellorium", new Color(0x27204d), new Color(14019840)),
        CYANITE("cyanite", new Color(0x27204d), new Color(0x86ed)),
        BLUTONIUM("blutonium", new Color(0x27204d), new Color(0x1b00e6));

        final String name;
        final int primary;
        final int secondary;
        final Object2FloatOpenHashMap<ItemStack> products = new Object2FloatOpenHashMap<>();
        public static final Items[] VALUES = values();

        Items(String name, Color primary, Color secondary) {
            this.name = name;
            this.primary = primary.getRGB();
            this.secondary = secondary.getRGB();
        }

        public ItemStack get(int count) {
            return comb.getStack(count, ordinal());
        }

        public void addRecipe() {
            RecipeManagers.centrifugeManager.addRecipe(20, get(1), products);
        }

        public void addProduct(ItemStack item, float chance) {
            if (item == null) return;

            products.put(item.copy(), chance);
        }

        public void copyProducts(Items comb) {
            products.putAll(comb.products);
        }
    }

    public enum ForestryCombs {

        HONEY,
        COCOA,
        SIMMERING,
        STRINGY,
        FROZEN,
        DRIPPING,
        SILKY,
        PARCHED,
        MYSTERIOUS,
        IRRADIATED,
        POWDERY,
        REDDENED,
        DARKENED,
        OMEGA,
        WHEATEN,
        MOSSY,
        QUARTZ;

        public ItemStack get() {
            return new ItemStack(Mods.forestry.item("beeCombs"), 1, ordinal());
        }
    }

    private IIcon cells;
    private IIcon outline;

    public CombItems() {
        super(
                Tabs.tabApiculture,
                EB_MODID,
                "honeyComb",
                true,
                Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));

        setMaxStackSize(64);
        setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(String name) {
        return "extrabees.item.comb." + name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int indexColor) {
        final int damage = stack.getItemDamage();
        final Items comb = damage < Items.VALUES.length ? Items.VALUES[stack.getItemDamage()] : null;

        if (comb == null) return 0xffffff;
        return indexColor == 0 ? comb.primary : comb.secondary;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass > 0 ? cells : outline;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        cells = register.registerIcon("forestry:beeCombs.0");
        outline = register.registerIcon("forestry:beeCombs.1");
    }

    public static void addSubtypes() {
        final ItemStack beeswax = Mods.forestry.stack("beeswax");
        final ItemStack honeyDrop = Mods.forestry.stack("honeyDrop");
        
        OreDictionary.registerOre("gemLapis", new ItemStack(dye, 1, 4));

        Items.BARREN.addProduct(beeswax, 1.00f);
        Items.BARREN.addProduct(honeyDrop, 0.50f);

        Items.ROTTEN.addProduct(beeswax, 0.20f);
        Items.ROTTEN.addProduct(honeyDrop, 0.20f);
        Items.ROTTEN.addProduct(new ItemStack(net.minecraft.init.Items.rotten_flesh, 1, 0), 0.80f);

        Items.BONE.addProduct(beeswax, 0.20f);
        Items.BONE.addProduct(honeyDrop, 0.20f);
        Items.BONE.addProduct(new ItemStack(dye, 1, 15), 0.80f);

        Items.OIL.addProduct(Propolis.Items.OIL.get(1), 0.60f);
        Items.OIL.addProduct(honeyDrop, 0.75f);

        Items.COAL.addProduct(beeswax, 0.80f);
        Items.COAL.addProduct(honeyDrop, 0.75f);
        Items.COAL.addProduct(CoalDust.get(1), 1.00f);

        Items.WATER.addProduct(Propolis.Items.WATER.get(1), 1.00f);
        Items.WATER.addProduct(honeyDrop, 0.90f);

        Items.STONE.addProduct(beeswax, 0.50f);
        Items.STONE.addProduct(honeyDrop, 0.25f);

        Items.MILK.addProduct(DropItems.Items.MILK.get(1), 1.00f);
        Items.MILK.addProduct(honeyDrop, 0.90f);

        Items.FRUIT.addProduct(DropItems.Items.APPLE.get(1), 1.00f);
        Items.FRUIT.addProduct(honeyDrop, 0.90f);

        Items.SEED.addProduct(DropItems.Items.SEED.get(1), 1.00f);
        Items.SEED.addProduct(honeyDrop, 0.90f);

        Items.ALCOHOL.addProduct(DropItems.Items.ALCOHOL.get(1), 1.00f);
        Items.ALCOHOL.addProduct(honeyDrop, 0.90f);

        Items.FUEL.addProduct(Propolis.Items.FUEL.get(1), 0.60f);
        Items.FUEL.addProduct(honeyDrop, 0.50f);

        Items.CREOSOTE.addProduct(Propolis.Items.CREOSOTE.get(1), 0.70f);
        Items.CREOSOTE.addProduct(honeyDrop, 0.50f);

        Items.LATEX.addProduct(honeyDrop, 0.50f);
        Items.LATEX.addProduct(beeswax, 0.85f);
        if (!OreDictionary.getOres("itemRubber").isEmpty()) {
            Items.LATEX.addProduct(OreDictionary.getOres("itemRubber").get(0), 1.00f);
        }

        Items.REDSTONE.addProduct(beeswax, 0.80f);
        Items.REDSTONE.addProduct(new ItemStack(net.minecraft.init.Items.redstone, 1, 0), 1.00f);
        Items.REDSTONE.addProduct(honeyDrop, 0.50f);

        Items.RESIN.addProduct(beeswax, 1.00f);
        Items.RESIN.addProduct(Mods.ic2.stack("itemHarz"), 1.00f);
        Items.RESIN.addProduct(Mods.ic2.stack("itemHarz"), 0.50f);

        Items.IC2ENERGY.addProduct(beeswax, 0.80f);
        Items.IC2ENERGY.addProduct(new ItemStack(net.minecraft.init.Items.redstone, 1, 0), 0.75f);
        Items.IC2ENERGY.addProduct(DropItems.Items.ENERGY.get(1), 1.00f);

        Items.IRON.copyProducts(Items.STONE);
        Items.IRON.addProduct(IronDust.get(1), 1.00f);

        Items.GOLD.copyProducts(Items.STONE);
        Items.GOLD.addProduct(GoldDust.get(1), 1.00f);

        Items.COPPER.copyProducts(Items.STONE);
        Items.COPPER.addProduct(CopperDust.get(1), 1.00f);

        Items.TIN.copyProducts(Items.STONE);
        Items.TIN.addProduct(TinDust.get(1), 1.00f);

        Items.NICKEL.copyProducts(Items.STONE);
        Items.NICKEL.addProduct(NickelDust.get(1), 1.00f);

        Items.SILVER.copyProducts(Items.STONE);
        Items.SILVER.addProduct(SilverDust.get(1), 1.00f);

        Items.URANIUM.copyProducts(Items.STONE);
        if (!OreDictionary.getOres("crushedUranium").isEmpty()) {
            Items.URANIUM.addProduct(OreDictionary.getOres("crushedUranium").get(0), 0.50f);
        }

        Items.CLAY.addProduct(beeswax, 0.25f);
        Items.CLAY.addProduct(honeyDrop, 0.80f);
        Items.CLAY.addProduct(new ItemStack(net.minecraft.init.Items.clay_ball), 0.80f);

        Items.OLD.addProduct(beeswax, 1.00f);
        Items.OLD.addProduct(honeyDrop, 0.90f);

        Items.FUNGAL.addProduct(beeswax, 0.90f);
        Items.FUNGAL.addProduct(new ItemStack(Blocks.brown_mushroom_block, 1, 0), 1.00f);
        Items.FUNGAL.addProduct(new ItemStack(Blocks.red_mushroom_block, 1, 0), 0.75f);

        Items.ACIDIC.addProduct(beeswax, 0.80f);
        Items.ACIDIC.addProduct(DropItems.Items.ACID.get(1), 0.50f);
        if (!OreDictionary.getOres("dustSulfur").isEmpty()) {
            Items.ACIDIC.addProduct(OreDictionary.getOres("dustSulfur").get(0), 0.75f);
        }

        Items.VENOMOUS.addProduct(beeswax, 0.80f);
        Items.VENOMOUS.addProduct(DropItems.Items.POISON.get(1), 0.80f);

        Items.SLIME.addProduct(beeswax, 1.00f);
        Items.SLIME.addProduct(honeyDrop, 0.75f);
        Items.SLIME.addProduct(new ItemStack(net.minecraft.init.Items.slime_ball, 1, 0), 0.75f);

        Items.BLAZE.addProduct(beeswax, 0.75f);
        Items.BLAZE.addProduct(new ItemStack(net.minecraft.init.Items.blaze_powder, 1, 0), 1.00f);

        Items.COFFEE.addProduct(beeswax, 0.90f);
        Items.COFFEE.addProduct(honeyDrop, 0.75f);
        Items.COFFEE.addProduct(Mods.ic2.stack("itemCofeePowder"), 0.75f);

        Items.GLACIAL.addProduct(DropItems.Items.ICE.get(1), 0.80f);
        Items.GLACIAL.addProduct(honeyDrop, 0.75f);

        Items.SHADOW.addProduct(honeyDrop, 0.50f);
        if (!OreDictionary.getOres("dustObsidian").isEmpty()) {
            Items.SHADOW.addProduct(OreDictionary.getOres("dustObsidian").get(0), 0.75f);
        }

        Items.LEAD.copyProducts(Items.STONE);
        Items.LEAD.addProduct(LeadDust.get(1), 1.00f);

        Items.ZINC.copyProducts(Items.STONE);
        Items.ZINC.addProduct(ZincDust.get(1), 1.00f);

        Items.TITANIUM.copyProducts(Items.STONE);
        Items.TITANIUM.addProduct(TitaniumDust.get(1), 1.00f);

        Items.TUNGSTEN.copyProducts(Items.STONE);
        Items.TUNGSTEN.addProduct(TungstenDust.get(1), 1.00f);

        Items.PLATINUM.copyProducts(Items.STONE);
        Items.PLATINUM.addProduct(PlatinumDust.get(1), 1.00f);

        Items.LAPIS.copyProducts(Items.STONE);
        Items.LAPIS.addProduct(new ItemStack(dye, 6, 4), 1.00f);

        Items.EMERALD.copyProducts(Items.STONE);
        Items.EMERALD.addProduct(EmeraldShard.get(1), 1.00f);

        Items.RUBY.copyProducts(Items.STONE);
        Items.RUBY.addProduct(RubyShard.get(1), 1.00f);

        Items.SAPPHIRE.copyProducts(Items.STONE);
        Items.SAPPHIRE.addProduct(SapphireShard.get(1), 1.00f);

        Items.DIAMOND.copyProducts(Items.STONE);
        Items.DIAMOND.addProduct(DiamondShard.get(1), 1.00f);

        Items.RED.addProduct(honeyDrop, 0.80f);
        Items.RED.addProduct(beeswax, 0.80f);

        Items.GLOWSTONE.addProduct(honeyDrop, 0.25f);
        Items.GLOWSTONE.addProduct(new ItemStack(net.minecraft.init.Items.glowstone_dust), 1.00f);

        Items.SALTPETER.addProduct(honeyDrop, 0.25f);
        final List<ItemStack> ores = OreDictionary.getOres("dustSaltpeter");
        final ItemStack saltpeter = ores.isEmpty() ? null : ores.get(0);
        Items.SALTPETER.addProduct(saltpeter, 1.00f);

        Items.COMPOST.addProduct(honeyDrop, 0.25f);
        Items.COMPOST.addProduct(Mods.forestry.stack("fertilizerBio"), 1.00f);

        Items.SAWDUST.addProduct(honeyDrop, 0.25f);
        if (!OreDictionary.getOres("dustSawdust").isEmpty()) {
            Items.SAWDUST.addProduct(OreDictionary.getOres("dustSawdust").get(0), 1.00f);
        } else if (!OreDictionary.getOres("sawdust").isEmpty()) {
            Items.SAWDUST.addProduct(OreDictionary.getOres("sawdust").get(0), 1.00f);
        }

        Items.CERTUS.addProduct(honeyDrop, 0.25f);
        Items.CERTUS.addProduct(new ItemStack(net.minecraft.init.Items.quartz), 0.25f);
        if (!OreDictionary.getOres("dustCertusQuartz").isEmpty()) {
            Items.CERTUS.addProduct(OreDictionary.getOres("dustCertusQuartz").get(0), 0.20f);
        }

        Items.ENDERPEARL.addProduct(honeyDrop, 0.25f);
        if (!OreDictionary.getOres("dustEnderPearl").isEmpty()) {
            Items.ENDERPEARL.addProduct(OreDictionary.getOres("dustEnderPearl").get(0), 0.25f);
        }

        Items.YELLORIUM.copyProducts(Items.STONE);
        Items.YELLORIUM.addProduct(YelloriumDust.get(1), 0.25f);

        Items.CYANITE.copyProducts(Items.STONE);
        Items.CYANITE.addProduct(CyaniteDust.get(1), 0.25f);

        Items.BLUTONIUM.copyProducts(Items.STONE);
        Items.BLUTONIUM.addProduct(BlutoniumDust.get(1), 0.25f);
        OreDictionary.registerOre("beeComb", new ItemStack(EBItems.comb, 1, 32767));

        // Copy the default products on RED, then add a drop
        for (int i = 0; i < 16; ++i) {
            final Items dyeComb = Items.VALUES[Items.RED.ordinal() + i];
            final DropItems.Items drop = DropItems.Items.VALUES[DropItems.Items.RED.ordinal() + i];

            if (dyeComb != Items.RED) {
                dyeComb.copyProducts(Items.RED);
                dyeComb.addProduct(drop.get(1), 1);
            }
        }

        // RED gets its drop later, to avoid copying it to all the others
        Items.RED.addProduct(DropItems.Items.RED.get(1), 1);

        // Add dyes to binnie drops
        // TODO: move this to itemDrop
        for (int i = 0; i < 16; ++i) {
            final DropItems.Items drop = DropItems.Items.VALUES[DropItems.Items.RED.ordinal() + i];
            drop.addRemnant(EBMisc.Items.getDye(i));
        }
    }
}
