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
import binnie.core.craftgui.minecraft.control.ControlMachineProgress;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.network.packet.MessageCraftGUI;
import binnie.core.util.I18N;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.genepool.Genepool;
import cpw.mods.fml.relauncher.Side;

public class WindowGenepool extends WindowMachine {

    static Texture progressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
    static Texture progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());

    public WindowGenepool(EntityPlayer player, IInventory inventory, Side side) {
        super(280, 198, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowGenepool(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();

        final NBTTagList actions = new NBTTagList();

        int x = 16;
        int y = 32;
        new ControlLiquidTank(this, x, y).setTankID(Genepool.TANK_ETHANOL);

        x += 26;
        new ControlSlotArray(this, x, y + 3, 2, 3).create(actions, InventoryType.Machine, Genepool.SLOT_RESERVE);

        x += 38;
        new ControlIconDisplay(this, x, y + 22, GUIIcon.ArrowRight.getIcon());

        x += 18;
        new ControlSlot(this, x, y + 21).assign(actions, InventoryType.Machine, Genepool.SLOT_BEE);

        x += 18;
        new ControlMachineProgress(this, x, y + 19, progressBase, progress, Position.LEFT);

        x += 130;
        new ControlLiquidTank(this, x, y).setTankID(Genepool.TANK_DNA);
        new ControlEnergyBar(this, 21, 115, 16, 60, Position.BOTTOM);
        new ControlSlot(this, 121.0f, 82.0f).assign(actions, InventoryType.Machine, Genepool.SLOT_ENZYME);
        new ControlSlotCharge(this, 143, 82, 7).setColor(0xefe8af);
        new ControlErrorState(this, 181.0f, 83.0f);
        new ControlPlayerInventory(this).create(actions);

        MessageCraftGUI.sendToServer(actions);
    }

    @Override
    public String getTitle() {
        return I18N.localise("gui.genetics.machine.labMachine.genepool.title");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Genepool";
    }
}
