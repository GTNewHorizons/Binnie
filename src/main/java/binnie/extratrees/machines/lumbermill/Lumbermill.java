package binnie.extratrees.machines.lumbermill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;

public class Lumbermill {

    public static final int SLOT_WOOD = 0;
    public static final int SLOT_PLANKS = 1;
    public static final int SLOT_BARK = 2;
    public static final int SLOT_SAWDUST = 3;
    public static final int TANK_WATER = 0;
    public static final int RF_COST = 900;
    public static final int TIME_PERIOD = 30;
    public static final int WATER_PER_TICK = 10;

    public static Map<ItemStack, ItemStack> recipes = new HashMap<>();

    public static ItemStack getPlankProduct(ItemStack item) {
        if (Lumbermill.recipes.isEmpty()) {
            calculateLumbermillProducts();
        }

        for (Map.Entry<ItemStack, ItemStack> entry : Lumbermill.recipes.entrySet()) {
            if (entry.getKey().isItemEqual(item)) {
                ItemStack stack = entry.getValue().copy();
                stack.stackSize = 6;
                return stack;
            }
        }
        return null;
    }

    public static void calculateLumbermillProducts() {
        for (IPlankType type : WoodManager.getAllPlankTypes()) {
            for (ItemStack wood : getRecipeResult(type.getStack())) {
                Lumbermill.recipes.put(wood, type.getStack());
            }
        }
    }

    private static Collection<ItemStack> getRecipeResult(ItemStack output) {
        List<ItemStack> list = new ArrayList<>();
        for (IRecipe iRecipe : CraftingManager.getInstance().getRecipeList()) {
            if (iRecipe instanceof ShapelessRecipes shapeless) {
                if (shapeless.recipeItems.size() != 1 || !(shapeless.recipeItems.get(0) instanceof ItemStack)) {
                    continue;
                }

                ItemStack input = shapeless.recipeItems.get(0);
                if (shapeless.getRecipeOutput() != null && shapeless.getRecipeOutput().isItemEqual(output)) {
                    list.add(input);
                }
            }

            if (iRecipe instanceof ShapedRecipes shaped) {
                if (shaped.recipeItems.length != 1) {
                    continue;
                }

                ItemStack input = shaped.recipeItems[0];
                if (shaped.getRecipeOutput() != null && shaped.getRecipeOutput().isItemEqual(output)) {
                    list.add(input);
                }
            }

            if (iRecipe instanceof ShapelessOreRecipe shapelessOre) {
                if (shapelessOre.getInput().size() != 1
                        || !(shapelessOre.getInput().get(0) instanceof ItemStack input)) {
                    continue;
                }

                if (shapelessOre.getRecipeOutput() == null || !shapelessOre.getRecipeOutput().isItemEqual(output)) {
                    continue;
                }
                list.add(input);
            }
        }
        return list;
    }
}
