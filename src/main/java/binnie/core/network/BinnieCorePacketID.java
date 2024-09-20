package binnie.core.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;

import binnie.core.BinnieCore;
import binnie.core.block.TileEntityMetadata;
import binnie.core.craftgui.minecraft.ContainerCraftGUI;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageCraftGUI;
import binnie.core.network.packet.MessageMetadata;
import binnie.core.network.packet.MessageTileNBT;
import binnie.core.network.packet.MessageUpdate;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public enum BinnieCorePacketID implements IPacketID {

    NetworkEntityUpdate,
    TileMetadata,
    CraftGUIAction,
    TileDescriptionSync;

    @Override
    public void onMessage(MessageBinnie message, MessageContext context) {
        if (this == BinnieCorePacketID.NetworkEntityUpdate) {
            MessageUpdate packet = new MessageUpdate(message);
            TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());
            if (tile instanceof INetworkedEntity) {
                ((INetworkedEntity) tile).readFromPacket(packet.payload);
            }
        } else if (this == BinnieCorePacketID.TileMetadata) {
            MessageMetadata packet = new MessageMetadata(message);
            TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());
            if (tile instanceof TileEntityMetadata) {
                ((TileEntityMetadata) tile).setTileMetadata(packet.meta, true);
            }
        } else if (this == BinnieCorePacketID.CraftGUIAction) {
            if (context.side == Side.CLIENT) {
                MessageCraftGUI packet = new MessageCraftGUI(message);
                EntityPlayer player = BinnieCore.proxy.getPlayer();
                if (player.openContainer instanceof ContainerCraftGUI && packet.getTagCompound() != null) {
                    ((ContainerCraftGUI) player.openContainer).recieveNBT(Side.CLIENT, player, packet.getTagCompound());
                }
            } else if (context.netHandler instanceof NetHandlerPlayServer) {
                MessageCraftGUI packet = new MessageCraftGUI(message);
                EntityPlayer player = ((NetHandlerPlayServer) context.netHandler).playerEntity;
                if (player.openContainer instanceof ContainerCraftGUI && packet.getTagCompound() != null) {
                    ((ContainerCraftGUI) player.openContainer).recieveNBT(Side.SERVER, player, packet.getTagCompound());
                }
            }
        } else if (this == BinnieCorePacketID.TileDescriptionSync && context.side == Side.CLIENT) {
            MessageTileNBT packet = new MessageTileNBT(message);
            TileEntity tile = packet.getTarget(BinnieCore.proxy.getWorld());
            if (tile != null && packet.getTagCompound() != null) {
                IMachine machine = Machine.getMachine(tile);
                if (machine instanceof INetwork.TilePacketSync tps) {
                    tps.syncFromNBT(packet.getTagCompound());
                }
            }
        }
    }
}
