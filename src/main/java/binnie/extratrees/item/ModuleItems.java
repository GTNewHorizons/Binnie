package binnie.extratrees.item;

import static binnie.extratrees.item.ETItems.itemMisc;
import static binnie.extratrees.item.ETMisc.Items.Bark;
import static binnie.extratrees.item.ETMisc.Items.GlassFitting;
import static binnie.extratrees.item.ETMisc.Items.ProvenGear;
import static binnie.extratrees.item.ETMisc.Items.WoodWax;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.liquid.ItemFluidContainer;
import binnie.extratrees.block.ILogType;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.recipes.RecipeManagers;

public class ModuleItems implements IInitializable {

    @Override
    public void preInit() {
        // Loads ETItems.class, guaranteeing that items are init by this point
        // TODO: is this needed?
        itemMisc.getUnlocalizedName();

        if (BinnieCore.isLepidopteryActive()) {
            ETItems.itemDictionaryLepi = new ItemMothDatabase();
        }

        Binnie.Liquid.createLiquids(ExtraTreeLiquid.values(), ItemFluidContainer.LiquidExtraTree);
    }

    @Override
    public void init() {
        OreDictionary.registerOre("pulpWood", ETMisc.Items.Sawdust.get(1));
        FoodItems.Items.registerOreDictionary();
        OreDictionary.registerOre("cropApple", Items.apple);
        OreDictionary.registerOre("seedWheat", Items.wheat_seeds);

        FoodItems.Items.Crabapple.addJuice(10, 150, 10);
        FoodItems.Items.Orange.addJuice(10, 400, 15);
        FoodItems.Items.Kumquat.addJuice(10, 300, 10);
        FoodItems.Items.Lime.addJuice(10, 300, 10);
        FoodItems.Items.WildCherry.addOil(20, 50, 5);
        FoodItems.Items.SourCherry.addOil(20, 50, 3);
        FoodItems.Items.BlackCherry.addOil(20, 50, 5);
        FoodItems.Items.Blackthorn.addJuice(10, 50, 5);
        FoodItems.Items.CherryPlum.addJuice(10, 100, 60);
        FoodItems.Items.Almond.addOil(20, 80, 5);
        FoodItems.Items.Apricot.addJuice(10, 150, 40);
        FoodItems.Items.Grapefruit.addJuice(10, 500, 15);
        FoodItems.Items.Peach.addJuice(10, 150, 40);
        FoodItems.Items.Satsuma.addJuice(10, 300, 10);
        FoodItems.Items.BuddhaHand.addJuice(10, 400, 15);
        FoodItems.Items.Citron.addJuice(10, 400, 15);
        FoodItems.Items.FingerLime.addJuice(10, 300, 10);
        FoodItems.Items.KeyLime.addJuice(10, 300, 10);
        FoodItems.Items.Manderin.addJuice(10, 400, 10);
        FoodItems.Items.Nectarine.addJuice(10, 150, 40);
        FoodItems.Items.Pomelo.addJuice(10, 300, 10);
        FoodItems.Items.Tangerine.addJuice(10, 300, 10);
        FoodItems.Items.Pear.addJuice(10, 300, 20);
        FoodItems.Items.SandPear.addJuice(10, 200, 10);
        FoodItems.Items.Hazelnut.addOil(20, 150, 5);
        FoodItems.Items.Butternut.addOil(20, 180, 5);
        FoodItems.Items.Beechnut.addOil(20, 100, 4);
        FoodItems.Items.Pecan.addOil(29, 50, 2);
        FoodItems.Items.Banana.addJuice(10, 100, 30);
        FoodItems.Items.RedBanana.addJuice(10, 100, 30);
        FoodItems.Items.Plantain.addJuice(10, 100, 40);
        FoodItems.Items.BrazilNut.addOil(20, 20, 2);
        FoodItems.Items.Fig.addOil(20, 50, 3);
        FoodItems.Items.Acorn.addOil(20, 50, 3);
        FoodItems.Items.Elderberry.addJuice(10, 100, 5);
        FoodItems.Items.Olive.addOil(20, 50, 3);
        FoodItems.Items.GingkoNut.addOil(20, 50, 5);
        FoodItems.Items.Coffee.addOil(15, 20, 2);
        FoodItems.Items.OsangeOrange.addJuice(10, 300, 15);
        FoodItems.Items.Clove.addOil(10, 25, 2);
        FoodItems.Items.Coconut.addOil(20, 300, 25);
        FoodItems.Items.Cashew.addJuice(10, 150, 15);
        FoodItems.Items.Avacado.addJuice(10, 300, 15);
        FoodItems.Items.Nutmeg.addOil(20, 50, 10);
        FoodItems.Items.Allspice.addOil(20, 50, 10);
        FoodItems.Items.Chilli.addJuice(10, 100, 10);
        FoodItems.Items.StarAnise.addOil(20, 100, 10);
        FoodItems.Items.Mango.addJuice(10, 400, 20);
        FoodItems.Items.Starfruit.addJuice(10, 300, 10);
        FoodItems.Items.Candlenut.addJuice(20, 50, 10);
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ETItems.itemDurableHammer, 1, 0),
                        "wiw",
                        " s ",
                        " s ",
                        'w',
                        Blocks.obsidian,
                        'i',
                        Items.gold_ingot,
                        's',
                        Items.stick));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ETItems.itemHammer, 1, 0),
                        "wiw",
                        " s ",
                        " s ",
                        'w',
                        "plankWood",
                        'i',
                        Items.iron_ingot,
                        's',
                        Items.stick));

        GameRegistry.addRecipe(ProvenGear.get(1), " s ", "s s", " s ", 's', Mods.forestry.stack("oakStick"));

        GameRegistry.addRecipe(GlassFitting.get(6), "s s", " i ", "s s", 'i', Items.iron_ingot, 's', Items.stick);

        try {
            Item minium = (Item) Class.forName("com.pahimar.ee3.lib.ItemIds").getField("miniumShard").get(null);
            GameRegistry.addRecipe(new ShapelessOreRecipe(FoodItems.Items.Papayimar.get(1), minium, "cropPapaya"));
        } catch (Exception ex) {
            // ignored
        }

        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("water", 2000),
                null,
                new ItemStack(ETItems.itemDictionary),
                "X#X",
                "YEY",
                "RDR",
                '#',
                Blocks.glass_pane,
                'X',
                Items.gold_ingot,
                'Y',
                "ingotCopper",
                'R',
                Items.redstone,
                'D',
                Items.diamond,
                'E',
                Items.emerald);

        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("water", 2000),
                null,
                new ItemStack(ETItems.itemDictionaryLepi),
                "X#X",
                "YEY",
                "RDR",
                '#',
                Blocks.glass_pane,
                'X',
                Items.gold_ingot,
                'Y',
                "ingotBronze",
                'R',
                Items.redstone,
                'D',
                Items.diamond,
                'E',
                Items.emerald);

        RecipeManagers.stillManager.addRecipe(25, ExtraTreeLiquid.Resin.get(5), ExtraTreeLiquid.Turpentine.get(3));

        RecipeManagers.carpenterManager.addRecipe(
                25,
                ExtraTreeLiquid.Turpentine.get(50),
                null,
                WoodWax.get(4),
                "x",
                'x',
                Mods.forestry.stack("beeswax"));

        if (Binnie.Liquid.getLiquidStack("Creosote Oil", 100) != null) {
            RecipeManagers.carpenterManager.addRecipe(
                    25,
                    Binnie.Liquid.getLiquidStack("Creosote Oil", 50),
                    null,
                    WoodWax.get(1),
                    "x",
                    'x',
                    Mods.forestry.stack("beeswax"));
        }

        for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry
                .getRegisteredFluidContainerData()) {
            if (data.fluid.isFluidEqual(Binnie.Liquid.getLiquidStack("water", 0)) && data.fluid.amount == 1000) {
                CraftingManager.getInstance().addRecipe(
                        Mods.forestry.stack("mulch"),
                        " b ",
                        "bwb",
                        " b ",
                        'b',
                        Bark.get(1),
                        'w',
                        data.filledContainer.copy());
            }
        }

        FuelManager.bronzeEngineFuel.put(
                ExtraTreeLiquid.Sap.get(1).getFluid(),
                new EngineBronzeFuel(ExtraTreeLiquid.Sap.get(1).getFluid(), 20, 10000, 1));
        FuelManager.bronzeEngineFuel.put(
                ExtraTreeLiquid.Resin.get(1).getFluid(),
                new EngineBronzeFuel(ExtraTreeLiquid.Resin.get(1).getFluid(), 30, 10000, 1));
        for (ILogType.ExtraTreeLog log : ILogType.ExtraTreeLog.values()) {
            log.addRecipe();
        }
    }
}
