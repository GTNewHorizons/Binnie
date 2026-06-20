package binnie.core.liquid;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.ManagerBase;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ManagerLiquid extends ManagerBase {

    private static final Set<String> EXTERNAL_FLUIDS = new HashSet<>();

    static {
        EXTERNAL_FLUIDS.add("liquidnitrogen");
    }

    private static boolean isExternalFluid(String identifier) {
        return EXTERNAL_FLUIDS.contains(identifier) && Loader.isModLoaded("gregtech");
    }

    public Map<String, IFluidType> fluids;
    private final Set<String> ownedFluids;

    public ManagerLiquid() {
        fluids = new LinkedHashMap<>();
        ownedFluids = new HashSet<>();
    }

    public void createLiquids(IFluidType[] liquids, int startID) {
        for (IFluidType liquid : liquids) {
            String identifier = liquid.getIdentifier().toLowerCase();
            Fluid fluid = createOrGetLiquid(liquid, startID++);
            if (fluid == null && !isExternalFluid(identifier)) {
                throw new RuntimeException("Liquid registered incorrectly - " + liquid.getIdentifier());
            }
        }
    }

    public BinnieFluid createLiquid(IFluidType fluid, int id) {
        Fluid registeredFluid = createOrGetLiquid(fluid, id);
        return (registeredFluid instanceof BinnieFluid) ? (BinnieFluid) registeredFluid : null;
    }

    private Fluid createOrGetLiquid(IFluidType fluid, int id) {
        String identifier = fluid.getIdentifier().toLowerCase();
        fluids.put(identifier, fluid);
        Fluid registeredFluid = FluidRegistry.getFluid(identifier);
        if (registeredFluid == null && !isExternalFluid(identifier)) {
            BinnieFluid binnieFluid = new BinnieFluid(fluid);
            FluidRegistry.registerFluid(binnieFluid);
            registeredFluid = FluidRegistry.getFluid(identifier);
            ownedFluids.add(identifier);
        }
        ItemFluidContainer.registerFluid(fluid, id);
        return registeredFluid;
    }

    public FluidStack getLiquidStack(String name, int amount) {
        return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
    }

    @SideOnly(Side.CLIENT)
    public void reloadIcons(IIconRegister register) {
        for (IFluidType type : fluids.values()) {
            String identifier = type.getIdentifier().toLowerCase();
            FluidStack stack = getLiquidStack(identifier, 1);
            Fluid fluid = stack == null ? null : stack.getFluid();
            type.registerIcon(register);
            if (fluid == null) {
                throw new RuntimeException("[Binnie] Liquid not registered properly - " + type.getIdentifier());
            }
            if (ownedFluids.contains(identifier)) {
                fluid.setIcons(type.getIcon());
            }
        }
    }

    @Override
    public void postInit() {
        for (IFluidType fluid : fluids.values()) {
            String identifier = fluid.getIdentifier().toLowerCase();
            if (isExternalFluid(identifier) && FluidRegistry.getFluid(identifier) == null) {
                throw new RuntimeException("[Binnie] External liquid not registered - " + identifier);
            }
            for (FluidContainer container : FluidContainer.values()) {
                if (container.isActive() && fluid.canPlaceIn(container)) {
                    container.registerContainerData(fluid);
                }
            }
        }
    }
}
