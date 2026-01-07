package binnie.core.craftgui.minecraft.control;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagList;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.network.packet.MessageCraftGUI;

public class ControlPlayerInventory extends Control {

    private final List<ControlSlot> slots;

    public ControlPlayerInventory(IWidget parent, boolean wide) {
        super(
                parent,
                (int) (parent.getSize().x() / 2.0f) - (wide ? 110 : 81),
                (int) parent.getSize().y() - (wide ? 54 : 76) - 12,
                wide ? 220 : 162,
                wide ? 54 : 76);
        slots = new ArrayList<>();
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                ControlSlot slot = new ControlSlot(this, (wide ? 58 : 0) + column * 18, row * 18)
                        .setSlotTexture(CraftGUITexture.SlotInventory);
                slots.add(slot);
            }
        }

        if (wide) {
            for (int i1 = 0; i1 < 9; ++i1) {
                ControlSlot slot2 = new ControlSlot(this, i1 % 3 * 18, i1 / 3 * 18)
                        .setSlotTexture(CraftGUITexture.SlotInventory);
                slots.add(slot2);
            }
        } else {
            for (int i1 = 0; i1 < 9; ++i1) {
                ControlSlot slot2 = new ControlSlot(this, i1 * 18, 58.0f).setSlotTexture(CraftGUITexture.SlotInventory);
                slots.add(slot2);
            }
        }
    }

    public ControlPlayerInventory(IWidget parent) {
        this(parent, false);
    }

    public ControlPlayerInventory(IWidget parent, int x, int y) {
        super(parent, x, y, 54.0f, 220.0f);
        slots = new ArrayList<>();
        for (int row = 0; row < 6; ++row) {
            for (int column = 0; column < 6; ++column) {
                ControlSlot slot = new ControlSlot(this, column * 18, row * 18)
                        .setSlotTexture(CraftGUITexture.SlotInventory);
                slots.add(slot);
            }
        }
    }

    public ControlPlayerInventory create(NBTTagList actions) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                ControlSlot slot = slots.get(column + row * 9);
                slot.assign(actions, InventoryType.Player, 9 + column + row * 9);
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            ControlSlot slot2 = slots.get(27 + i1);
            slot2.assign(actions, InventoryType.Player, i1);
        }

        return this;
    }

    public ControlPlayerInventory createAndRegister() {
        final NBTTagList actions = new NBTTagList();
        create(actions);
        MessageCraftGUI.sendToServer(actions);
        return this;
    }
}
