package binnie.core.craftgui.minecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;

public class CustomSlot extends Slot {

    private InventorySlot inventorySlot;
    private boolean inventorySlotResolved;

    public CustomSlot(IInventory inventory, int index) {
        super(inventory, index, 0, 0);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return inventory.isItemValidForSlot(getSlotIndex(), par1ItemStack);
    }

    public InventorySlot getInventorySlot() {
        if (!inventorySlotResolved) {
            IInventorySlots slots = Machine.getInterface(IInventorySlots.class, inventory);
            inventorySlot = (slots == null) ? null : slots.getSlot(getSlotIndex());
            inventorySlotResolved = true;
        }
        return inventorySlot;
    }

    public boolean handleClick() {
        InventorySlot slot = getInventorySlot();
        return slot != null && slot.isRecipe();
    }

    public void onSlotClick(ContainerCraftGUI container, int mouseButton, int modifier, EntityPlayer player) {
        ItemStack stack = player.inventory.getItemStack();
        if (stack == null || mouseButton == 2) {
            putStack(null);
        } else {
            stack = stack.copy();
            stack.stackSize = 1;
            putStack(stack);
        }
    }
}
