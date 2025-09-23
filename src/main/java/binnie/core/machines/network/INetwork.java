package binnie.core.machines.network;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.relauncher.Side;

public interface INetwork {

    interface SendGuiNBT {

        void sendGuiNBT(Map<String, NBTTagCompound> nbts);
    }

    interface TilePacketSync {

        void syncToNBT(NBTTagCompound nbt);

        void syncFromNBT(NBTTagCompound nbt);
    }

    interface ReceiveGuiNBT {

        void receiveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt);
    }

    interface GuiNBT extends ReceiveGuiNBT, SendGuiNBT {
    }
}
