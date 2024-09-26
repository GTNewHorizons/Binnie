package binnie.core.machines.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;

class ComponentBinnieCoreGUI extends MachineComponent implements IInteraction.RightClick {

    private final BinnieCoreGUI id;

    public ComponentBinnieCoreGUI(Machine machine, BinnieCoreGUI id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        BinnieCore.proxy.openGui(id, player, x, y, z);
    }
}
