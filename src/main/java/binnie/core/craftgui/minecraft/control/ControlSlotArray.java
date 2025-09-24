package binnie.core.craftgui.minecraft.control;

import static binnie.core.craftgui.minecraft.ContainerCraftGUI.SLOT_INDEX;
import static binnie.core.craftgui.minecraft.ContainerCraftGUI.SLOT_NUMBER;
import static binnie.core.craftgui.minecraft.ContainerCraftGUI.SLOT_REG;
import static binnie.core.craftgui.minecraft.ContainerCraftGUI.SLOT_TYPE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;

public class ControlSlotArray extends Control implements Iterable<ControlSlot> {

    private final List<ControlSlot> slots;

    public ControlSlotArray(IWidget parent, int x, int y, int columns, int rows) {
        super(parent, x, y, columns * 18, rows * 18);
        slots = new ArrayList<>();
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                slots.add(createSlot(column * 18, row * 18));
            }
        }
    }

    public ControlSlot createSlot(int x, int y) {
        return new ControlSlot(this, x, y);
    }

    public ControlSlotArray create(int[] index) {
        return create(InventoryType.Machine, index);
    }

    public ControlSlotArray create(InventoryType inventoryType, int[] index) {
        int slotIndex = 0;
        for (ControlSlot controlSlot : slots) {
            controlSlot.assign(inventoryType, index[slotIndex]);

            NBTTagCompound slotReg = new NBTTagCompound();
            slotReg.setByte(SLOT_TYPE, (byte) inventoryType.ordinal());
            slotReg.setShort(SLOT_INDEX, (short) slotIndex);
            slotReg.setShort(SLOT_NUMBER, (short) controlSlot.slot.slotNumber);

            ((Window) getSuperParent()).sendClientAction(SLOT_REG, slotReg);

            slotIndex++;
        }
        return this;
    }

    @Override
    public Iterator<ControlSlot> iterator() {
        return slots.iterator();
    }
}
