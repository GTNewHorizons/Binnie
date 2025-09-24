package binnie.core.network.packet;

import static binnie.core.craftgui.minecraft.ContainerCraftGUI.ACTIONS;
import static binnie.core.craftgui.minecraft.ContainerCraftGUI.ACTION_BATCH;
import static binnie.core.craftgui.minecraft.ContainerCraftGUI.ACTION_TYPE;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import binnie.core.BinnieCore;
import binnie.core.network.BinnieCorePacketID;

public class MessageCraftGUI extends MessageNBT {

    public MessageCraftGUI(MessageBinnie message) {
        super(message);
    }

    public MessageCraftGUI(NBTTagCompound action) {
        super(BinnieCorePacketID.CraftGUIAction.ordinal(), action);
    }

    public static void sendToServer(NBTTagList actions) {
        NBTTagCompound actionsBatch = new NBTTagCompound();
        actionsBatch.setString(ACTION_TYPE, ACTION_BATCH);
        actionsBatch.setTag(ACTIONS, actions);
        final MessageCraftGUI packet = new MessageCraftGUI(actionsBatch);
        BinnieCore.proxy.sendToServer(packet);
    }
}
