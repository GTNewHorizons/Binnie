package binnie.core.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
        switch (this) {
            case NetworkEntityUpdate -> processNetworkEntityUpdate(message);
            case TileMetadata -> processTileMetadata(message);
            case CraftGUIAction -> processCraftGUIAction(message, context);
            case TileDescriptionSync -> processTileDescriptionSync(message, context);
        }
    }

    private static void processNetworkEntityUpdate(MessageBinnie message) {
        MessageUpdate packet = new MessageUpdate(message);
        TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());

        if (tile instanceof INetworkedEntity iNetworkedEntity) {
            iNetworkedEntity.readFromPacket(packet.payload);
        }
    }

    private static void processTileMetadata(MessageBinnie message) {
        MessageMetadata packet = new MessageMetadata(message);
        TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());

        if (tile instanceof TileEntityMetadata tileEntityMetadata) {
            tileEntityMetadata.setTileMetadata(packet.meta, true);
        }
    }

    private static void processCraftGUIAction(MessageBinnie message, MessageContext context) {
        if (context.side == Side.CLIENT) {
            final EntityPlayer player = BinnieCore.proxy.getPlayer();

            if (player.openContainer instanceof ContainerCraftGUI containerCraftGUI) {
                final MessageCraftGUI packet = new MessageCraftGUI(message);
                final NBTTagCompound tagCompound = packet.getTagCompound();

                if (tagCompound == null) return;

                containerCraftGUI.recieveNBT(Side.CLIENT, player, tagCompound);
            }
        } else {
            MessageCraftGUI packet = new MessageCraftGUI(message);
            NBTTagCompound tagCompound = packet.getTagCompound();

            if (tagCompound == null) return;

            if (context.netHandler instanceof NetHandlerPlayServer netHandlerPlayServer) {
                EntityPlayer player = netHandlerPlayServer.playerEntity;

                if (player.openContainer instanceof ContainerCraftGUI containerCraftGUI) {
                    containerCraftGUI.recieveNBT(Side.SERVER, player, tagCompound);
                }
            }
        }
    }

    private static void processTileDescriptionSync(MessageBinnie message, MessageContext context) {
        if (!context.side.isClient()) return;

        MessageTileNBT packet = new MessageTileNBT(message);
        NBTTagCompound tagCompound = packet.getTagCompound();

        if (tagCompound == null) return;

        TileEntity tile = packet.getTarget(BinnieCore.proxy.getWorld());

        if (tile == null) return;

        IMachine machine = Machine.getMachine(tile);

        if (machine instanceof INetwork.TilePacketSync tps) {
            tps.syncFromNBT(tagCompound);
        }
    }
}
