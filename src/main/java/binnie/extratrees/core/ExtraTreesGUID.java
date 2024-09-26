package binnie.extratrees.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.gui.IBinnieGUID;
import binnie.extratrees.craftgui.WindowArboristDatabase;
import binnie.extratrees.craftgui.WindowLepidopteristDatabase;
import binnie.extratrees.craftgui.WindowLumbermill;
import binnie.extratrees.craftgui.WindowSetSquare;
import binnie.extratrees.craftgui.WindowWoodworker;
import cpw.mods.fml.relauncher.Side;

public enum ExtraTreesGUID implements IBinnieGUID {

    Database,
    Woodworker,
    Lumbermill,
    DatabaseNEI,
    Incubator,
    MothDatabase,
    MothDatabaseNEI,
    Infuser,
    SetSquare;

    @Override
    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        Window window = null;
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }
        window = switch (this) {
            case Database, DatabaseNEI -> WindowArboristDatabase.create(player, side, this != ExtraTreesGUID.Database);
            case Woodworker -> WindowWoodworker.create(player, object, side);
            case Lumbermill -> WindowLumbermill.create(player, object, side);
            case MothDatabase, MothDatabaseNEI -> WindowLepidopteristDatabase
                    .create(player, side, this != ExtraTreesGUID.MothDatabase);
            case SetSquare -> WindowSetSquare.create(player, world, x, y, z, side);
            default -> window;
        };
        return window;
    }
}
