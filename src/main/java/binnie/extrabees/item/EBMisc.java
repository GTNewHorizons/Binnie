package binnie.extrabees.item;

import static binnie.extrabees.ExtraBees.EB_MODID;
import static binnie.extrabees.item.EBItems.itemMisc;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.item.DamageItems;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class EBMisc extends DamageItems {
    public enum Items {
        ScentedGear("scentedGear"),
        DiamondShard("diamondShard", "Diamond", false),
        EmeraldShard("emeraldShard", "Emerald", false),
        RubyShard("rubyShard", "Ruby", false),
        SapphireShard("sapphireShard", "Sapphire", false),
        LapisShard("lapisShard"),
        IronDust("ironDust", "Iron", true),
        GoldDust("goldDust", "Gold", true),
        SilverDust("silverDust", "Silver", true),
        PlatinumDust("platinumDust", "Platinum", true),
        CopperDust("copperDust", "Copper", true),
        TinDust("tinDust", "Tin", true),
        NickelDust("nickelDust", "Nickel", true),
        LeadDust("leadDust", "Lead", true),
        ZincDust("zincDust", "Zinc", true),
        TitaniumDust("titaniumDust", "Titanium", true),
        TungstenDust("tungstenDust", "Tungsten", true),
        UraniumDust("radioactiveDust"),
        // Don't look at me, it was like this when I got here
        CoalDust("coalDust", "Coal", true),
        RedDye("dyeRed"),
        YellowDye("dyeYellow"),
        BlueDye("dyeBlue"),
        GreenDye("dyeGreen"),
        WhiteDye("dyeWhite"),
        BlackDye("dyeBlack"),
        BrownDye("dyeBrown"),
        ClayDust("clayDust"),
        YelloriumDust("yelloriumDust", "Yellorium", true),
        BlutoniumDust("blutoniumDust", "Blutonium", true),
        CyaniteDust("cyaniteDust", "Cyanite", true);

        final String name;
        final String resourceString;
        final boolean isMetal;
        private static final Items[] VALUES = values();
        private static final int[] binnieToVanillaColors = { 1, 11, 4, 2, 0, 15, 3, 14, 6, 5, 8, 12, 9, 10, 13, 7 };

        Items(String name) {
            this(name, null, false);
        }

        Items(String name, String resourceString, boolean isMetal) {
            this.name = name;
            this.resourceString = resourceString;
            this.isMetal = isMetal;
        }

        public ItemStack get(int count) {
            return itemMisc.getStack(count, ordinal());
        }

        /**
         * @param binnieID The index of colors used by Binnie's mods, starting with red = 0
         * @return An itemstack containing one dye item, preferring Binnie's when possible
         */
        public static ItemStack getDye(int binnieID) {
            final int i = binnieToVanillaColors[binnieID];
            return switch (i) {
                case 0 -> BlackDye.get(1);
                case 1 -> RedDye.get(1);
                case 2 -> GreenDye.get(1);
                case 3 -> BrownDye.get(1);
                case 4 -> BlueDye.get(1);
                case 11 -> YellowDye.get(1);
                case 15 -> WhiteDye.get(1);
                default -> new ItemStack(net.minecraft.init.Items.dye, 1, i);
            };
        }
    }

    protected EBMisc() {
        super(
                Tabs.tabApiculture,
                EB_MODID,
                Arrays.stream(Items.VALUES).map(i -> i.name).toArray(String[]::new));
    }

    @Override
    public String getIconName(int damage) {
        return "misc/" + super.getIconName(damage);
    }
    
    public static void init() {
        OreDictionary.registerOre("dyeRed", Items.RedDye.get(1));
        OreDictionary.registerOre("dyeYellow", Items.YellowDye.get(1));
        OreDictionary.registerOre("dyeBlue", Items.BlueDye.get(1));
        OreDictionary.registerOre("dyeGreen", Items.GreenDye.get(1));
        OreDictionary.registerOre("dyeBlack", Items.BlackDye.get(1));
        OreDictionary.registerOre("dyeWhite", Items.WhiteDye.get(1));
        OreDictionary.registerOre("dyeBrown", Items.BrownDye.get(1));
    }

    public static void postInit() {
        ItemStack lapisShard = Items.LapisShard.get(1);
        GameRegistry.addShapelessRecipe(new ItemStack(net.minecraft.init.Items.dye, 1, 4), lapisShard, lapisShard, lapisShard, lapisShard);
        for (Items item : Items.VALUES) {
            if (item.resourceString != null && item.isMetal) {
                ItemStack dust = null;
                ItemStack ingot = null;
                if (!OreDictionary.getOres("ingot" + item.resourceString).isEmpty()) {
                    ingot = OreDictionary.getOres("ingot" + item.resourceString).get(0).copy();
                }
                if (!OreDictionary.getOres("dust" + item.resourceString).isEmpty()) {
                    dust = OreDictionary.getOres("dust" + item.resourceString).get(0).copy();
                }

                ItemStack input = item.get(1);
                if (dust != null) {
                    GameRegistry.addShapelessRecipe(dust, input, input, input, input);
                } else if (ingot != null) {
                    GameRegistry
                            .addShapelessRecipe(ingot, input, input, input, input, input, input, input, input, input);
                } else if (item == Items.CoalDust) {
                    GameRegistry.addShapelessRecipe(new ItemStack(net.minecraft.init.Items.coal), input, input, input, input);
                }
            } else if (item.resourceString != null) {
                ItemStack gem = null;
                if (!OreDictionary.getOres("gem" + item.resourceString).isEmpty()) {
                    gem = OreDictionary.getOres("gem" + item.resourceString).get(0);
                }

                ItemStack input2 = item.get(1);
                if (gem != null) {
                    GameRegistry.addShapelessRecipe(
                            gem.copy(),
                            input2,
                            input2,
                            input2,
                            input2,
                            input2,
                            input2,
                            input2,
                            input2,
                            input2);
                }
            }
        }

        Item woodGear = null;
        try {
            woodGear = (Item) Class.forName("buildcraft.BuildCraftCore").getField("woodenGearItem").get(null);
        } catch (Exception ex) {
            // ignored
        }

        ItemStack gear = new ItemStack(Blocks.planks, 1);
        if (woodGear != null) {
            gear = new ItemStack(woodGear, 1);
        }

        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("for.honey", 500),
                null,
                Items.ScentedGear.get(1),
                " j ",
                "bgb",
                " p ",
                'j',
                Mods.forestry.item("royalJelly"),
                'b',
                Mods.forestry.item("beeswax"),
                'p',
                Mods.forestry.item("pollen"),
                'g',
                gear);
    }
}
