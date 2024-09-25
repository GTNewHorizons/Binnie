package binnie.genetics.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.UniqueFluidStackSet;
import binnie.core.util.UniqueItemStackSet;
import forestry.api.recipes.RecipeManagers;

public abstract class AnalystPageProduce extends ControlAnalystPage {

    public AnalystPageProduce(IWidget parent, IArea area) {
        super(parent, area);
    }

    protected Collection<? extends ItemStack> getAllProducts(ItemStack key) {
        Collection<ItemStack> products = new UniqueItemStackSet();
        products.addAll(getCentrifuge(key));
        products.addAll(getSqueezer(key));
        products.add(FurnaceRecipes.smelting().getSmeltingResult(key));
        products.addAll(getCrafting(key));
        return products;
    }

    public Collection<ItemStack> getCentrifuge(ItemStack stack) {
        List<ItemStack> products = new ArrayList<>();
        for (Map.Entry<Object[], Object[]> recipe : RecipeManagers.centrifugeManager.getRecipes().entrySet()) {
            boolean isRecipe = false;
            for (Object obj : recipe.getKey()) {
                if (obj instanceof ItemStack i && stack.isItemEqual(i)) {
                    isRecipe = true;
                    break;
                }
            }

            if (isRecipe) {
                for (Object obj : recipe.getValue()) {
                    if (obj instanceof ItemStack) {
                        products.add((ItemStack) obj);
                    }
                }
            }
        }
        return products;
    }

    public Collection<ItemStack> getSqueezer(ItemStack stack) {
        List<ItemStack> products = new ArrayList<>();
        for (Map.Entry<Object[], Object[]> recipe : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
            boolean isRecipe = false;
            for (Object obj : recipe.getKey()) {
                if (obj instanceof ItemStack i && stack.isItemEqual(i)) {
                    isRecipe = true;
                    break;
                }
            }

            if (isRecipe) {
                for (Object obj : recipe.getValue()) {
                    if (obj instanceof ItemStack) {
                        products.add((ItemStack) obj);
                    }
                }
            }
        }
        return products;
    }

    public Collection<ItemStack> getCrafting(ItemStack stack) {
        List<ItemStack> products = new ArrayList<>();
        for (IRecipe iRecipe : CraftingManager.getInstance().getRecipeList()) {
            if (iRecipe instanceof ShapelessRecipes shapeless) {
                boolean match = true;
                for (final ItemStack rec : shapeless.recipeItems) {
                    if (rec != null && !stack.isItemEqual(rec)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    products.add(shapeless.getRecipeOutput());
                }
            }

            if (iRecipe instanceof ShapedRecipes shaped) {
                boolean match = true;
                for (final ItemStack rec : shaped.recipeItems) {
                    if (rec != null && !stack.isItemEqual(rec)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    products.add(shaped.getRecipeOutput());
                }
            }

            if (iRecipe instanceof ShapelessOreRecipe shapelessOre) {
                boolean match = true;
                for (final Object rec : shapelessOre.getInput()) {
                    if (rec != null && (!(rec instanceof ItemStack i) || !stack.isItemEqual(i))) {
                        match = false;
                        break;
                    }
                }

                if (!match) {
                    continue;
                }
                products.add(shapelessOre.getRecipeOutput());
            }
        }
        return products;
    }

    public Collection<FluidStack> getAllFluids(ItemStack stack) {
        return new ArrayList<>(getSqueezerFluid(stack));
    }

    public Collection<FluidStack> getSqueezerFluid(ItemStack stack) {
        List<FluidStack> products = new ArrayList<>();
        for (final Map.Entry<Object[], Object[]> recipe : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
            boolean isRecipe = false;
            for (final Object obj : recipe.getKey()) {
                if (obj instanceof ItemStack i && stack.isItemEqual(i)) {
                    isRecipe = true;
                    break;
                }
            }

            if (isRecipe) {
                for (final Object obj : recipe.getValue()) {
                    if (obj instanceof FluidStack f) {
                        products.add(f);
                    }
                }
            }
        }
        return products;
    }

    protected Collection<? extends FluidStack> getAllProducts() {
        return new UniqueFluidStackSet();
    }

    protected Collection<ItemStack> getAllProductsAndFluids(Collection<ItemStack> collection) {
        Collection<ItemStack> products = new UniqueItemStackSet();
        for (final ItemStack stack : collection) {
            products.addAll(getAllProducts(stack));
        }

        Collection<ItemStack> products2 = new UniqueItemStackSet();
        for (final ItemStack stack2 : products) {
            products2.addAll(getAllProducts(stack2));
        }

        Collection<ItemStack> products3 = new UniqueItemStackSet();
        for (final ItemStack stack3 : products2) {
            products3.addAll(getAllProducts(stack3));
        }

        products.addAll(products2);
        products.addAll(products3);
        Collection<FluidStack> allFluids = new UniqueFluidStackSet();
        for (final ItemStack stack4 : collection) {
            allFluids.addAll(getAllFluids(stack4));
        }

        Collection<FluidStack> fluids2 = new UniqueFluidStackSet();
        for (FluidStack ignored : allFluids) {
            fluids2.addAll(getAllProducts());
        }

        Collection<FluidStack> fluids3 = new UniqueFluidStackSet();
        for (FluidStack ignored : fluids2) {
            fluids3.addAll(getAllProducts());
        }

        allFluids.addAll(fluids2);
        allFluids.addAll(fluids3);
        for (FluidStack fluid : allFluids) {
            ItemStack container = null;
            for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry
                    .getRegisteredFluidContainerData()) {
                if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle))
                        && data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.emptyContainer.isItemEqual(new ItemStack(Items.bucket)) && data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }
            }
            if (container != null) {
                products.add(container);
            }
        }
        return products;
    }

    protected int getRefined(String string, int y, Collection<ItemStack> products) {
        new ControlTextCentered(this, y, string).setColor(getColor());
        y += 10;
        int maxBiomePerLine = (int) ((w() + 2.0f - 16.0f) / 18.0f);
        float biomeListX = (w() - (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2.0f;
        int dx = 0;
        int dy = 0;
        for (ItemStack soilStack : products) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(soilStack);
            soilStack.stackSize = 1;
            ControlItemDisplay display = new ControlItemDisplay(
                    this,
                    biomeListX + dx,
                    y + dy,
                    soilStack,
                    fluid == null);
            if (fluid != null) {
                display.addTooltip(fluid.getLocalizedName());
            }
            dx += 18;
        }

        if (dx != 0) {
            dy += 18;
        }
        return y + dy;
    }
}
