package binnie.core.machines.power;

import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.inventory.TankSlot;

public interface ITankMachine extends IFluidHandler, IValidatedTankContainer {

    TankInfo[] getTankInfos();

    IFluidTank[] getTanks();

    TankSlot addTank(int index, String name, int capacity);

    IFluidTank getTank(int index);

    TankSlot getTankSlot(int slot);
}
