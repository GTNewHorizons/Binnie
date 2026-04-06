package binnie.genetics.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.genetics.gui.WindowAnalyst;
import cpw.mods.fml.relauncher.Side;

public enum GeneticsGUI implements IBinnieGUID {

    Genepool,
    Isolator,
    Sequencer,
    Replicator,
    Inoculator,
    GeneBank,
    Analyser,
    Incubator,
    Database,
    DatabaseNEI,
    Acclimatiser,
    Splicer,
    Analyst,
    Registry,
    MasterRegistry;

    public static final GeneticsGUI[] VALUES = values();

    public Window getWindow(EntityPlayer player, IInventory object, Side side) {
        switch (this) {
            case Analyst:
                return new WindowAnalyst(player, null, side, false, false);
            case MasterRegistry:
                return new WindowAnalyst(player, null, side, true, true);
            case Registry:
                return new WindowAnalyst(player, null, side, true, false);
            default:
                return null;
        }
    }

    @Override
    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }
        return getWindow(player, object, side);
    }
}
