package binnie.genetics.craftgui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagList;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlProgress;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.network.packet.MessageCraftGUI;
import binnie.core.util.I18N;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.isolator.Isolator;
import cpw.mods.fml.relauncher.Side;

public class WindowIsolator extends WindowMachine {

    static Texture ProgressBase = new StandardTexture(0, 218, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());
    static Texture Progress = new StandardTexture(0, 201, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());

    public WindowIsolator(EntityPlayer player, IInventory inventory, Side side) {
        super(330, 208, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowIsolator(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();

        final NBTTagList actions = new NBTTagList();

        int x = 16;
        int y = 32;
        new ControlLiquidTank(this, x, y).setTankID(Isolator.TANK_ETHANOL);
        x += 26;
        new ControlSlotArray(this, x, y + 3, 1, 3).create(actions, InventoryType.Machine, Isolator.SLOT_RESERVE);
        x += 20;
        new ControlIconDisplay(this, x, y + 3 + 1, GUIIcon.ArrowRight.getIcon());
        x += 18;
        new ControlSlot(this, x, y + 3).assign(actions, InventoryType.Machine, Isolator.SLOT_TARGET);
        new ControlSlot(this, x, y + 36 + 3).assign(actions, InventoryType.Machine, Isolator.SLOT_ENZYME);
        new ControlSlotCharge(this, x + 18 + 2, y + 36 + 3, 0).setColor(0xefe8af);
        x += 18;
        new ControlProgress(this, x, y + 3, WindowIsolator.ProgressBase, WindowIsolator.Progress, Position.LEFT);
        x += 142;
        new ControlSlot(this, x, y + 3).assign(actions, InventoryType.Machine, Isolator.SLOT_RESULT);
        new ControlSlot(this, x, y + 3 + 36).assign(actions, InventoryType.Machine, Isolator.SLOT_SEQUENCER_VIAL);
        new ControlIconDisplay(this, x + 1, y + 3 + 19, GUIIcon.ArrowUp.getIcon());
        x += 20;
        new ControlIconDisplay(this, x, y + 3 + 1, GUIIcon.ArrowRight.getIcon());
        x += 18;
        new ControlSlotArray(this, x, y + 3, 2, 3).create(actions, InventoryType.Machine, Isolator.SLOT_FINISHED);
        new ControlEnergyBar(this, 260, 130, 16, 60, Position.BOTTOM);
        new ControlErrorState(this, 153.0f, 81.0f);
        new ControlPlayerInventory(this).create(actions);

        MessageCraftGUI.sendToServer(actions);
    }

    @Override
    public String getTitle() {
        return I18N.localise("gui.genetics.machine.machine.isolator.title");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Isolator";
    }
}
