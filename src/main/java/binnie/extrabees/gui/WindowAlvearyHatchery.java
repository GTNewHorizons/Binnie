package binnie.extrabees.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagList;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.network.packet.MessageCraftGUI;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.hatchery.AlvearyHatchery;
import cpw.mods.fml.relauncher.Side;

public class WindowAlvearyHatchery extends Window {

    protected Machine machine;
    protected ControlPlayerInventory playerInventory;

    public WindowAlvearyHatchery(EntityPlayer player, IInventory inventory, Side side) {
        super(176.0f, 144.0f, player, inventory, side);
        machine = ((TileEntityMachine) inventory).getMachine();
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        if (player == null || inventory == null) {
            return null;
        }
        return new WindowAlvearyHatchery(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        final NBTTagList actions = new NBTTagList();
        setTitle(machine.getPackage().getGuiDisplayName());
        playerInventory = new ControlPlayerInventory(this).create(actions);
        new ControlSlotArray(this, 43, 30, 5, 1).create(actions, InventoryType.Machine, AlvearyHatchery.SLOT_LARVAE);
        MessageCraftGUI.sendToServer(actions);
    }

    @Override
    public AbstractMod getMod() {
        return ExtraBees.instance;
    }

    @Override
    public String getName() {
        return "AlvearyHatchery";
    }
}
