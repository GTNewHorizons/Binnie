package binnie.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import binnie.core.craftgui.minecraft.Window;
import binnie.core.network.IOrdinaled;
import cpw.mods.fml.relauncher.Side;

public interface IBinnieGUID extends IOrdinaled {

    Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side);
}
