package binnie.genetics.core;

import java.lang.reflect.Constructor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.genetics.craftgui.WindowAcclimatiser;
import binnie.genetics.craftgui.WindowAnalyser;
import binnie.genetics.craftgui.WindowGenepool;
import binnie.genetics.craftgui.WindowIncubator;
import binnie.genetics.craftgui.WindowInoculator;
import binnie.genetics.craftgui.WindowIsolator;
import binnie.genetics.craftgui.WindowPolymeriser;
import binnie.genetics.craftgui.WindowSequencer;
import binnie.genetics.craftgui.WindowSplicer;
import binnie.genetics.gui.WindowAnalyst;
import cpw.mods.fml.relauncher.Side;

public enum GeneticsGUI implements IBinnieGUID {

    Genepool(WindowGenepool.class),
    Isolator(WindowIsolator.class),
    Sequencer(WindowSequencer.class),
    Replicator(WindowPolymeriser.class),
    Inoculator(WindowInoculator.class),
    GeneBank(null),
    Analyser(WindowAnalyser.class),
    Incubator(WindowIncubator.class),
    Database(null),
    DatabaseNEI(null),
    Acclimatiser(WindowAcclimatiser.class),
    Splicer(WindowSplicer.class),
    Analyst,
    Registry,
    MasterRegistry;

    protected Class<? extends Window> windowClass;

    GeneticsGUI() {
        // ignored
    }

    GeneticsGUI(Class window) {
        windowClass = window;
    }

    public Window getWindow(EntityPlayer player, IInventory object, Side side) throws Exception {
        switch (this) {
            case Analyst:
                return new WindowAnalyst(player, null, side, false, false);

            case MasterRegistry:
                return new WindowAnalyst(player, null, side, true, true);

            case Registry:
                return new WindowAnalyst(player, null, side, true, false);

            case GeneBank:
            case Database:
            case DatabaseNEI:
                // These GUIs now use MUI2 via ItemDatabase.buildUI()
                return null;
        }
        if (windowClass == null) {
            return null;
        }
        Constructor constr = windowClass.getConstructor(EntityPlayer.class, IInventory.class, Side.class);
        return (Window) constr.newInstance(player, object, side);
    }

    @Override
    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        Window window = null;
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }

        try {
            if (this == GeneticsGUI.Database || this == GeneticsGUI.DatabaseNEI || this == GeneticsGUI.GeneBank) {
                // Now handled by MUI2 via ItemDatabase
                return null;
            } else {
                window = getWindow(player, object, side);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return window;
    }
}
