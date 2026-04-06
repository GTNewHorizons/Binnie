package binnie.genetics.gui.mui2;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.item.InvWrapper;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.ToggleButton;
import com.mojang.authlib.GameProfile;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.ProcessInfo;

public class GeneticsGuiHelper {

    public static final int PANEL_WIDTH = 198;
    public static final int PAD = 7;
    public static final int PLAYER_INV_X = 7;
    public static final int ENERGY_BAR_WIDTH = 18;
    public static final int CONTENT_Y = 18;
    public static final int PLAYER_INV_Y = 83;
    public static final int BUTTON_COLUMN_X = 174;

    private static final UITexture POWER_SWITCH_ON = UITexture
            .fullImage("gregtech", "gui/overlay_button/power_switch_on");
    private static final UITexture POWER_SWITCH_OFF = UITexture
            .fullImage("gregtech", "gui/overlay_button/power_switch_off");

    public static final UITexture ICON_SERUM_INPUT = UITexture.fullImage("genetics", "items/validator/serum.0");
    public static final UITexture ICON_SERUM_OUTPUT = UITexture.fullImage("genetics", "items/validator/serum.1");
    public static final UITexture ICON_INDIVIDUAL_INPUT = UITexture
            .fullImage("genetics", "items/validator/individual.0");
    public static final UITexture ICON_INDIVIDUAL_OUTPUT = UITexture
            .fullImage("genetics", "items/validator/individual.1");
    public static final UITexture ICON_ENZYME = UITexture.fullImage("genetics", "items/validator/enzyme.0");
    public static final UITexture ICON_DYE = UITexture.fullImage("genetics", "items/validator/dye.0");
    public static final UITexture ICON_SEQUENCER_VIAL = UITexture.fullImage("genetics", "items/validator/sequencer.0");
    public static final UITexture ICON_SEQUENCER_VIAL_FILLED = UITexture
            .fullImage("genetics", "items/validator/sequencer.1");
    public static final UITexture ICON_BACTERIA = UITexture.fullImage("genetics", "items/validator/bacteria.0");
    public static final UITexture ICON_NUGGET = UITexture.fullImage("genetics", "items/validator/nugget.0");

    public static IDrawable slotBackground(UITexture icon) {
        return IDrawable.of(GuiTextures.SLOT_ITEM, icon.asIcon().margin(1));
    }

    public static void addButtonColumn(ModularPanel panel, PanelSyncManager syncManager, Machine machine) {
        addButtonColumn(panel, syncManager, machine, PLAYER_INV_Y);
    }

    public static void addButtonColumn(ModularPanel panel, PanelSyncManager syncManager, Machine machine,
            int playerInvY) {
        addEnergyBar(panel, syncManager, machine, BUTTON_COLUMN_X, CONTENT_Y, playerInvY - CONTENT_Y);

        StringSyncValue ownerSync = new StringSyncValue(() -> {
            if (machine == null) return "";
            GameProfile owner = machine.getOwner();
            return owner != null && owner.getName() != null ? owner.getName() : "";
        });
        syncManager.syncValue("owner_name", ownerSync);

        panel.child(new OwnerIndicatorWidget().syncHandler("owner_name").tooltipBuilder(tip -> {
            String name = ownerSync.getValue();
            if (name != null && !name.isEmpty()) {
                tip.addLine(IKey.lang("genetics.gui.owner", name));
            }
        }).tooltipAutoUpdate(true).pos(BUTTON_COLUMN_X, playerInvY + 18).size(18, 18));

        StringSyncValue errorSync = new StringSyncValue(() -> {
            if (machine == null) return "";
            IProcess process = machine.getInterface(IProcess.class);
            if (process == null) return "";
            ErrorState workError = process.canWork();
            if (workError != null) return workError.toString();
            ErrorState progError = process.canProgress();
            if (progError != null) return progError.toString();
            return "";
        });
        syncManager.syncValue("error_state", errorSync);

        panel.child(new ErrorIndicatorWidget().syncHandler("error_state").tooltipBuilder(tip -> {
            String err = errorSync.getValue();
            if (err != null && !err.isEmpty()) {
                tip.addLine(IKey.str("\u00a7c" + err));
            }
        }).tooltipAutoUpdate(true).pos(BUTTON_COLUMN_X, playerInvY + 36).size(18, 18));

        IProcess process = machine != null ? machine.getInterface(IProcess.class) : null;
        BooleanSyncValue enabledSync = new BooleanSyncValue(
                () -> process != null && process.isMachineEnabled(),
                val -> { if (process != null) process.setMachineEnabled(val); });

        panel.child(
                new ToggleButton().value(enabledSync).overlay(true, POWER_SWITCH_ON).overlay(false, POWER_SWITCH_OFF)
                        .tooltipBuilder(true, tip -> tip.addLine(IKey.lang("genetics.gui.machine_enabled")))
                        .tooltipBuilder(false, tip -> tip.addLine(IKey.lang("genetics.gui.machine_disabled")))
                        .size(18, 18).pos(BUTTON_COLUMN_X, playerInvY + 58));
    }

    public static void addEnergyBar(ModularPanel panel, PanelSyncManager syncManager, Machine machine, int x, int y,
            int height) {
        DoubleSyncValue energySync = new DoubleSyncValue(() -> {
            if (machine == null) return 0.0;
            IPoweredMachine power = machine.getInterface(IPoweredMachine.class);
            if (power != null) {
                PowerInfo info = power.getPowerInfo();
                int max = info.getMaxEnergy();
                if (max > 0) return (double) info.getStoredEnergy() / max;
            }
            return 0.0;
        });
        syncManager.syncValue("energy", energySync);

        panel.child(
                new ColorBarWidget().syncHandler("energy").fillColor(0xFF00CC00).backgroundColor(0xFF1A1A1A).vertical()
                        .tooltipBuilder(tip -> {
                            int pct = (int) (energySync.getDoubleValue() * 100);
                            tip.addLine(IKey.lang("genetics.gui.energy", pct));
                        }).tooltipAutoUpdate(true).pos(x, y).size(ENERGY_BAR_WIDTH, height));
    }

    public static void addProgressBar(ModularPanel panel, PanelSyncManager syncManager, Machine machine, int x, int y,
            int width, int height) {
        DoubleSyncValue progressSync = new DoubleSyncValue(() -> {
            if (machine == null) return 0.0;
            IProcess process = machine.getInterface(IProcess.class);
            if (process != null) {
                ProcessInfo info = process.getInfo();
                return info.getCurrentProgress() / 100.0;
            }
            return 0.0;
        });
        syncManager.syncValue("progress", progressSync);

        panel.child(
                new ColorBarWidget().syncHandler("progress").fillColor(0xFF55CC55).backgroundColor(0xFF1A1A1A)
                        .horizontal().tooltipBuilder(tip -> {
                            int pct = (int) (progressSync.getDoubleValue() * 100);
                            tip.addLine(IKey.lang("genetics.gui.progress", pct));
                        }).tooltipAutoUpdate(true).pos(x, y).size(width, height));
    }

    public static void addChargeBar(ModularPanel panel, PanelSyncManager syncManager, Machine machine, int slotId,
            String label, String syncKey, int x, int y) {
        DoubleSyncValue chargeSync = new DoubleSyncValue(() -> {
            if (machine == null) return 0.0;
            IChargedSlots charged = machine.getInterface(IChargedSlots.class);
            return charged != null ? (double) charged.getCharge(slotId) : 0.0;
        });
        syncManager.syncValue(syncKey, chargeSync);

        panel.child(
                new ColorBarWidget().syncHandler(syncKey).fillColor(0xFFEFE8AF).backgroundColor(0xFF1A1A1A).horizontal()
                        .tooltipBuilder(tip -> {
                            int pct = (int) (chargeSync.getDoubleValue() * 100);
                            tip.addLine(IKey.str(label + ": " + pct + "%"));
                        }).tooltipAutoUpdate(true).pos(x, y).size(18, 5));
    }

    public static void addStatusControls(ModularPanel panel, PanelSyncManager syncManager, Machine machine) {
        StringSyncValue errorSync = new StringSyncValue(() -> {
            if (machine == null) return "";
            IProcess process = machine.getInterface(IProcess.class);
            if (process == null) return "";
            ErrorState workError = process.canWork();
            if (workError != null) return workError.toString();
            ErrorState progError = process.canProgress();
            if (progError != null) return progError.toString();
            return "";
        });
        syncManager.syncValue("error_state", errorSync);

        panel.child(new ErrorIndicatorWidget().syncHandler("error_state").tooltipBuilder(tip -> {
            String err = errorSync.getValue();
            if (err != null && !err.isEmpty()) {
                tip.addLine(IKey.str("\u00a7c" + err));
            }
        }).tooltipAutoUpdate(true).pos(BUTTON_COLUMN_X, PLAYER_INV_Y + 40).size(18, 18));

        IProcess process = machine != null ? machine.getInterface(IProcess.class) : null;
        BooleanSyncValue enabledSync = new BooleanSyncValue(
                () -> process != null && process.isMachineEnabled(),
                val -> { if (process != null) process.setMachineEnabled(val); });

        panel.child(
                new ToggleButton().value(enabledSync).overlay(true, POWER_SWITCH_ON).overlay(false, POWER_SWITCH_OFF)
                        .tooltipBuilder(true, tip -> tip.addLine(IKey.lang("genetics.gui.machine_enabled")))
                        .tooltipBuilder(false, tip -> tip.addLine(IKey.lang("genetics.gui.machine_disabled")))
                        .size(18, 18).pos(BUTTON_COLUMN_X, PLAYER_INV_Y + 58));
    }

    public static String getSlotHint(Machine machine, int slotId, String fallback) {
        if (machine == null) return fallback;
        ComponentInventorySlots inv = machine.getComponent(ComponentInventorySlots.class);
        if (inv != null) {
            InventorySlot slot = inv.getSlot(slotId);
            if (slot != null && slot.getValidator() != null) {
                String tip = slot.getValidator().getTooltip();
                if (tip != null && !tip.isEmpty()) {
                    return tip;
                }
            }
        }
        return fallback;
    }

    public static class MachineGuiContext {

        public final ModularPanel panel;
        public final Machine machine;
        public final InvWrapper inv;
        public final ITankMachine tanks;

        MachineGuiContext(ModularPanel panel, Machine machine, InvWrapper inv, ITankMachine tanks) {
            this.panel = panel;
            this.machine = machine;
            this.inv = inv;
            this.tanks = tanks;
        }
    }

    public static MachineGuiContext createMachinePanel(PosGuiData data, PanelSyncManager syncManager, String panelId,
            int panelHeight) {
        TileEntity te = data.getTileEntity();
        InvWrapper inv = new InvWrapper((IInventory) te);
        ITankMachine tanks = te instanceof ITankMachine ? (ITankMachine) te : null;

        Machine machine = null;
        String title = "";
        if (te instanceof TileEntityMachine tem) {
            machine = tem.getMachine();
            title = machine.getPackage().getGuiDisplayName();
        }

        ModularPanel panel = ModularPanel.defaultPanel(panelId, PANEL_WIDTH, panelHeight);
        syncManager.registerSlotGroup("machine", 9);
        panel.child(new TextWidget<>(IKey.str(title)).pos(PAD, 6).size(PANEL_WIDTH - PAD * 2, 9));

        return new MachineGuiContext(panel, machine, inv, tanks);
    }
}
