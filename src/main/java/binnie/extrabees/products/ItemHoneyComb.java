package binnie.extrabees.products;

import static binnie.extrabees.item.EBMisc.Items.BlackDye;
import static binnie.extrabees.item.EBMisc.Items.BlueDye;
import static binnie.extrabees.item.EBMisc.Items.BlutoniumDust;
import static binnie.extrabees.item.EBMisc.Items.BrownDye;
import static binnie.extrabees.item.EBMisc.Items.CoalDust;
import static binnie.extrabees.item.EBMisc.Items.CopperDust;
import static binnie.extrabees.item.EBMisc.Items.CyaniteDust;
import static binnie.extrabees.item.EBMisc.Items.DiamondShard;
import static binnie.extrabees.item.EBMisc.Items.EmeraldShard;
import static binnie.extrabees.item.EBMisc.Items.GoldDust;
import static binnie.extrabees.item.EBMisc.Items.GreenDye;
import static binnie.extrabees.item.EBMisc.Items.IronDust;
import static binnie.extrabees.item.EBMisc.Items.LeadDust;
import static binnie.extrabees.item.EBMisc.Items.NickelDust;
import static binnie.extrabees.item.EBMisc.Items.PlatinumDust;
import static binnie.extrabees.item.EBMisc.Items.RedDye;
import static binnie.extrabees.item.EBMisc.Items.RubyShard;
import static binnie.extrabees.item.EBMisc.Items.SapphireShard;
import static binnie.extrabees.item.EBMisc.Items.SilverDust;
import static binnie.extrabees.item.EBMisc.Items.TinDust;
import static binnie.extrabees.item.EBMisc.Items.TitaniumDust;
import static binnie.extrabees.item.EBMisc.Items.TungstenDust;
import static binnie.extrabees.item.EBMisc.Items.WhiteDye;
import static binnie.extrabees.item.EBMisc.Items.YelloriumDust;
import static binnie.extrabees.item.EBMisc.Items.YellowDye;
import static binnie.extrabees.item.EBMisc.Items.ZincDust;

import binnie.extrabees.item.EBItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;

public class ItemHoneyComb extends ItemProduct {

    protected IIcon icon1;
    protected IIcon icon2;

    public ItemHoneyComb() {
        super(EnumHoneyComb.values());
        final String name = "honeyComb";

        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName(name);

        GameRegistry.registerItem(this, name);
    }

    public static void addSubtypes() {
        ItemStack beeswax = Mods.forestry.stack("beeswax");
        ItemStack honeyDrop = Mods.forestry.stack("honeyDrop");
        OreDictionary.registerOre("ingotIron", Items.iron_ingot);
        OreDictionary.registerOre("ingotGold", Items.gold_ingot);
        OreDictionary.registerOre("gemDiamond", Items.diamond);
        OreDictionary.registerOre("gemEmerald", Items.emerald);
        OreDictionary.registerOre("gemLapis", new ItemStack(Items.dye, 1, 4));

        EnumHoneyComb.BARREN.addProduct(beeswax, 1.00f);
        EnumHoneyComb.BARREN.addProduct(honeyDrop, 0.50f);

        EnumHoneyComb.ROTTEN.addProduct(beeswax, 0.20f);
        EnumHoneyComb.ROTTEN.addProduct(honeyDrop, 0.20f);
        EnumHoneyComb.ROTTEN.addProduct(new ItemStack(Items.rotten_flesh, 1, 0), 0.80f);

        EnumHoneyComb.BONE.addProduct(beeswax, 0.20f);
        EnumHoneyComb.BONE.addProduct(honeyDrop, 0.20f);
        EnumHoneyComb.BONE.addProduct(new ItemStack(Items.dye, 1, 15), 0.80f);

        EnumHoneyComb.OIL.tryAddProduct(Propolis.Items.OIL.get(1), 0.60f);
        EnumHoneyComb.OIL.addProduct(honeyDrop, 0.75f);

        EnumHoneyComb.COAL.addProduct(beeswax, 0.80f);
        EnumHoneyComb.COAL.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.COAL.tryAddProduct(CoalDust.get(1), 1.00f);

        EnumHoneyComb.WATER.tryAddProduct(Propolis.Items.WATER.get(1), 1.00f);
        EnumHoneyComb.WATER.addProduct(honeyDrop, 0.90f);

        EnumHoneyComb.STONE.addProduct(beeswax, 0.50f);
        EnumHoneyComb.STONE.addProduct(honeyDrop, 0.25f);

        EnumHoneyComb.MILK.tryAddProduct(EnumHoneyDrop.MILK.get(1), 1.00f);
        EnumHoneyComb.MILK.addProduct(honeyDrop, 0.90f);

        EnumHoneyComb.FRUIT.tryAddProduct(EnumHoneyDrop.APPLE.get(1), 1.00f);
        EnumHoneyComb.FRUIT.addProduct(honeyDrop, 0.90f);

        EnumHoneyComb.SEED.tryAddProduct(EnumHoneyDrop.SEED.get(1), 1.00f);
        EnumHoneyComb.SEED.addProduct(honeyDrop, 0.90f);

        EnumHoneyComb.ALCOHOL.tryAddProduct(EnumHoneyDrop.ALCOHOL.get(1), 1.00f);
        EnumHoneyComb.ALCOHOL.addProduct(honeyDrop, 0.90f);

        EnumHoneyComb.FUEL.tryAddProduct(Propolis.Items.FUEL.get(1), 0.60f);
        EnumHoneyComb.FUEL.addProduct(honeyDrop, 0.50f);

        EnumHoneyComb.CREOSOTE.tryAddProduct(Propolis.Items.CREOSOTE.get(1), 0.70f);
        EnumHoneyComb.CREOSOTE.addProduct(honeyDrop, 0.50f);

        EnumHoneyComb.LATEX.addProduct(honeyDrop, 0.50f);
        EnumHoneyComb.LATEX.addProduct(beeswax, 0.85f);
        if (!OreDictionary.getOres("itemRubber").isEmpty()) {
            EnumHoneyComb.LATEX.tryAddProduct(OreDictionary.getOres("itemRubber").get(0), 1.00f);
        } else {
            EnumHoneyComb.LATEX.active = false;
        }

        EnumHoneyComb.REDSTONE.addProduct(beeswax, 0.80f);
        EnumHoneyComb.REDSTONE.addProduct(new ItemStack(Items.redstone, 1, 0), 1.00f);
        EnumHoneyComb.REDSTONE.addProduct(honeyDrop, 0.50f);

        EnumHoneyComb.RESIN.addProduct(beeswax, 1.00f);
        EnumHoneyComb.RESIN.tryAddProduct(Mods.ic2.stack("itemHarz"), 1.00f);
        EnumHoneyComb.RESIN.tryAddProduct(Mods.ic2.stack("itemHarz"), 0.50f);

        EnumHoneyComb.IC2ENERGY.addProduct(beeswax, 0.80f);
        EnumHoneyComb.IC2ENERGY.addProduct(new ItemStack(Items.redstone, 1, 0), 0.75f);
        EnumHoneyComb.IC2ENERGY.tryAddProduct(EnumHoneyDrop.ENERGY.get(1), 1.00f);

        EnumHoneyComb.IRON.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.IRON.tryAddProduct(IronDust.get(1), 1.00f);

        EnumHoneyComb.GOLD.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.GOLD.tryAddProduct(GoldDust.get(1), 1.00f);

        EnumHoneyComb.COPPER.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.COPPER.tryAddProduct(CopperDust.get(1), 1.00f);

        EnumHoneyComb.TIN.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.TIN.tryAddProduct(TinDust.get(1), 1.00f);

        EnumHoneyComb.NICKEL.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.NICKEL.tryAddProduct(NickelDust.get(1), 1.00f);

        EnumHoneyComb.SILVER.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.SILVER.tryAddProduct(SilverDust.get(1), 1.00f);

        EnumHoneyComb.URANIUM.copyProducts(EnumHoneyComb.STONE);
        if (!OreDictionary.getOres("crushedUranium").isEmpty()) {
            EnumHoneyComb.URANIUM.tryAddProduct(OreDictionary.getOres("crushedUranium").get(0), 0.50f);
        }

        EnumHoneyComb.CLAY.addProduct(beeswax, 0.25f);
        EnumHoneyComb.CLAY.addProduct(honeyDrop, 0.80f);
        EnumHoneyComb.CLAY.addProduct(new ItemStack(Items.clay_ball), 0.80f);

        EnumHoneyComb.OLD.addProduct(beeswax, 1.00f);
        EnumHoneyComb.OLD.addProduct(honeyDrop, 0.90f);

        EnumHoneyComb.FUNGAL.addProduct(beeswax, 0.90f);
        EnumHoneyComb.FUNGAL.addProduct(new ItemStack(Blocks.brown_mushroom_block, 1, 0), 1.00f);
        EnumHoneyComb.FUNGAL.addProduct(new ItemStack(Blocks.red_mushroom_block, 1, 0), 0.75f);

        EnumHoneyComb.ACIDIC.addProduct(beeswax, 0.80f);
        EnumHoneyComb.ACIDIC.tryAddProduct(EnumHoneyDrop.ACID.get(1), 0.50f);
        if (!OreDictionary.getOres("dustSulfur").isEmpty()) {
            EnumHoneyComb.ACIDIC.addProduct(OreDictionary.getOres("dustSulfur").get(0), 0.75f);
        }

        EnumHoneyComb.VENOMOUS.addProduct(beeswax, 0.80f);
        EnumHoneyComb.VENOMOUS.tryAddProduct(EnumHoneyDrop.POISON.get(1), 0.80f);

        EnumHoneyComb.SLIME.addProduct(beeswax, 1.00f);
        EnumHoneyComb.SLIME.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.SLIME.addProduct(new ItemStack(Items.slime_ball, 1, 0), 0.75f);

        EnumHoneyComb.BLAZE.addProduct(beeswax, 0.75f);
        EnumHoneyComb.BLAZE.addProduct(new ItemStack(Items.blaze_powder, 1, 0), 1.00f);

        EnumHoneyComb.COFFEE.addProduct(beeswax, 0.90f);
        EnumHoneyComb.COFFEE.addProduct(honeyDrop, 0.75f);
        EnumHoneyComb.COFFEE.tryAddProduct(Mods.ic2.stack("itemCofeePowder"), 0.75f);

        EnumHoneyComb.GLACIAL.tryAddProduct(EnumHoneyDrop.ICE.get(1), 0.80f);
        EnumHoneyComb.GLACIAL.addProduct(honeyDrop, 0.75f);

        EnumHoneyComb.SHADOW.addProduct(honeyDrop, 0.50f);
        if (!OreDictionary.getOres("dustObsidian").isEmpty()) {
            EnumHoneyComb.SHADOW.tryAddProduct(OreDictionary.getOres("dustObsidian").get(0), 0.75f);
        } else {
            EnumHoneyComb.SHADOW.active = false;
        }

        EnumHoneyComb.LEAD.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.LEAD.tryAddProduct(LeadDust.get(1), 1.00f);

        EnumHoneyComb.ZINC.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.ZINC.tryAddProduct(ZincDust.get(1), 1.00f);

        EnumHoneyComb.TITANIUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.TITANIUM.tryAddProduct(TitaniumDust.get(1), 1.00f);

        EnumHoneyComb.TUNGSTEN.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.TUNGSTEN.tryAddProduct(TungstenDust.get(1), 1.00f);

        EnumHoneyComb.PLATINUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.PLATINUM.tryAddProduct(PlatinumDust.get(1), 1.00f);

        EnumHoneyComb.LAPIS.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.LAPIS.addProduct(new ItemStack(Items.dye, 6, 4), 1.00f);

        EnumHoneyComb.EMERALD.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.EMERALD.tryAddProduct(EmeraldShard.get(1), 1.00f);

        EnumHoneyComb.RUBY.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.RUBY.tryAddProduct(RubyShard.get(1), 1.00f);

        EnumHoneyComb.SAPPHIRE.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.SAPPHIRE.tryAddProduct(SapphireShard.get(1), 1.00f);

        EnumHoneyComb.DIAMOND.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.DIAMOND.tryAddProduct(DiamondShard.get(1), 1.00f);

        EnumHoneyComb.RED.addProduct(honeyDrop, 0.80f);
        EnumHoneyComb.RED.addProduct(beeswax, 0.80f);

        EnumHoneyComb.GLOWSTONE.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.GLOWSTONE.addProduct(new ItemStack(Items.glowstone_dust), 1.00f);

        EnumHoneyComb.SALTPETER.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.SALTPETER.tryAddProduct(getOreDictionary("dustSaltpeter"), 1.00f);

        EnumHoneyComb.COMPOST.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.COMPOST.tryAddProduct(Mods.forestry.stack("fertilizerBio"), 1.00f);

        EnumHoneyComb.SAWDUST.addProduct(honeyDrop, 0.25f);
        if (!OreDictionary.getOres("dustSawdust").isEmpty()) {
            EnumHoneyComb.SAWDUST.tryAddProduct(OreDictionary.getOres("dustSawdust").get(0), 1.00f);
        } else if (!OreDictionary.getOres("sawdust").isEmpty()) {
            EnumHoneyComb.SAWDUST.tryAddProduct(OreDictionary.getOres("sawdust").get(0), 1.00f);
        }

        EnumHoneyComb.CERTUS.addProduct(honeyDrop, 0.25f);
        EnumHoneyComb.CERTUS.addProduct(new ItemStack(Items.quartz), 0.25f);
        if (!OreDictionary.getOres("dustCertusQuartz").isEmpty()) {
            EnumHoneyComb.CERTUS.tryAddProduct(OreDictionary.getOres("dustCertusQuartz").get(0), 0.20f);
        }

        EnumHoneyComb.ENDERPEARL.addProduct(honeyDrop, 0.25f);
        if (!OreDictionary.getOres("dustEnderPearl").isEmpty()) {
            EnumHoneyComb.ENDERPEARL.tryAddProduct(OreDictionary.getOres("dustEnderPearl").get(0), 0.25f);
        }

        EnumHoneyComb.YELLORIUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.YELLORIUM.tryAddProduct(YelloriumDust.get(1), 0.25f);

        EnumHoneyComb.CYANITE.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.CYANITE.tryAddProduct(CyaniteDust.get(1), 0.25f);

        EnumHoneyComb.BLUTONIUM.copyProducts(EnumHoneyComb.STONE);
        EnumHoneyComb.BLUTONIUM.tryAddProduct(BlutoniumDust.get(1), 0.25f);
        OreDictionary.registerOre("beeComb", new ItemStack(EBItems.comb, 1, 32767));

        for (int i = 0; i < 16; ++i) {
            EnumHoneyComb type = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
            if (type != EnumHoneyComb.RED) {
                type.copyProducts(EnumHoneyComb.RED);
            }
        }

        for (int i = 0; i < 16; ++i) {
            EnumHoneyComb type = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
            EnumHoneyDrop drop = EnumHoneyDrop.values()[EnumHoneyDrop.RED.ordinal() + i];
            int[] dyeC = { 1, 11, 4, 2, 0, 15, 3, 14, 6, 5, 8, 12, 9, 10, 13, 7 };
            int k = dyeC[i];
            ItemStack dye = switch (k) {
                case 0 -> BlackDye.get(1);
                case 1 -> RedDye.get(1);
                case 2 -> GreenDye.get(1);
                case 3 -> BrownDye.get(1);
                case 4 -> BlueDye.get(1);
                case 11 -> YellowDye.get(1);
                case 15 -> WhiteDye.get(1);
                default -> new ItemStack(Items.dye, 1, k);
            };
            type.addProduct(drop.get(1), 1.00f);
            drop.addRemenant(dye);
        }
    }

    private static ItemStack getOreDictionary(String string) {
        if (!OreDictionary.getOres(string).isEmpty()) {
            return OreDictionary.getOres(string).get(0);
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int indexColor) {
        EnumHoneyComb comb = EnumHoneyComb.get(stack);
        if (comb == null) {
            return 0xffffff;
        }
        if (indexColor == 0) {
            return comb.primaryColor;
        }
        return comb.secondaryColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int i, int j) {
        if (j > 0) {
            return icon1;
        }
        return icon2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon1 = BinnieCore.proxy.getIcon(register, "forestry", "beeCombs.0");
        icon2 = BinnieCore.proxy.getIcon(register, "forestry", "beeCombs.1");
    }

    public enum VanillaComb {

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
}
