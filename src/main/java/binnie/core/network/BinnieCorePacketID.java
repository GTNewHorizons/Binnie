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
        final MessageUpdate packet = new MessageUpdate(message);
        final TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());

        if (tile instanceof INetworkedEntity iNetworkedEntity) {
            iNetworkedEntity.readFromPacket(packet.payload);
        }
    }

    private static void processTileMetadata(MessageBinnie message) {
        final MessageMetadata packet = new MessageMetadata(message);
        final TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());

        if (tile instanceof TileEntityMetadata tileEntityMetadata) {
            tileEntityMetadata.setTileMetadata(packet.meta, true);
        }
    }

    private static void processCraftGUIAction(MessageBinnie message, MessageContext context) {
        final MessageCraftGUI packet = new MessageCraftGUI(message);
        final NBTTagCompound tagCompound = packet.getTagCompound();

        if (tagCompound == null) return;

        if (context.side == Side.CLIENT) {
            final EntityPlayer player = BinnieCore.proxy.getPlayer();

            if (player.openContainer instanceof ContainerCraftGUI gui) {
                gui.receiveNBT(Side.CLIENT, player, tagCompound);
            }
        } else {
            if (context.netHandler instanceof NetHandlerPlayServer server) {
                final EntityPlayer player = server.playerEntity;

                if (player.openContainer instanceof ContainerCraftGUI gui) {
                    gui.receiveNBT(Side.SERVER, player, tagCompound);
                }
            }
        }
    }

    private static void processTileDescriptionSync(MessageBinnie message, MessageContext context) {
        if (!context.side.isClient()) return;

        final MessageTileNBT packet = new MessageTileNBT(message);
        final NBTTagCompound tagCompound = packet.getTagCompound();

        if (tagCompound == null) return;

        final TileEntity tile = packet.getTarget(BinnieCore.proxy.getWorld());

        if (tile == null) return;

        final IMachine machine = Machine.getMachine(tile);

        if (machine instanceof INetwork.TilePacketSync tps) {
            tps.syncFromNBT(tagCompound);
        }
    }
}
