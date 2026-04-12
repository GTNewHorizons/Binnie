package binnie.genetics.gui.mui2;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.utils.item.InvWrapper;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.ToggleButton;
import com.mojang.authlib.GameProfile;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.ProcessInfo;
import codechicken.nei.recipe.GuiCraftingRecipe;

public class GeneticsGuiHelper {

    public static final int PANEL_WIDTH = 198;
    public static final int PAD = 7;
    public static final int PLAYER_INV_X = 7;
    public static final int ENERGY_BAR_WIDTH = 18;
    public static final int CONTENT_Y = 18;
    public static final int PLAYER_INV_Y = 83;
    public static final int BUTTON_COLUMN_X = 174;

    private static final UITexture VANILLA_BACKGROUND = UITexture.builder()
            .location("modularui", "gui/background/vanilla_background").imageSize(195, 136).adaptable(4)
            .name("binnie_vanilla_background").build();

    public static final UITexture VANILLA_SLOT = UITexture.builder().location("modularui", "gui/slot/item")
            .imageSize(18, 18).adaptable(1).name("binnie_vanilla_slot").build();

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

    // Process animation textures (half-texel UV inset for GL_LINEAR filtering)
    public static final UITexture ISOLATOR_PROCESS_BASE = smoothProcessTexture(
            "extrabees",
            "gui/processes",
            0,
            218,
            142,
            17);
    public static final UITexture ISOLATOR_PROCESS_FULL = smoothProcessTexture(
            "extrabees",
            "gui/processes",
            0,
            201,
            142,
            17);
    public static final UITexture GENEPOOL_PROCESS_BASE = smoothProcessTexture(
            "extrabees",
            "gui/processes",
            64,
            0,
            79,
            21);
    public static final UITexture GENEPOOL_PROCESS_FULL = smoothProcessTexture(
            "extrabees",
            "gui/processes",
            64,
            21,
            79,
            21);
    public static final UITexture POLYMERISER_PROCESS_BASE = smoothProcessTexture(
            "genetics",
            "gui/process",
            126,
            170,
            110,
            79);
    public static final UITexture POLYMERISER_PROCESS_FULL = smoothProcessTexture(
            "genetics",
            "gui/process",
            126,
            91,
            110,
            79);
    public static final UITexture INOCULATOR_PROCESS_BASE = smoothProcessTexture(
            "genetics",
            "gui/process2",
            0,
            72,
            142,
            72);
    public static final UITexture INOCULATOR_PROCESS_FULL = smoothProcessTexture(
            "genetics",
            "gui/process2",
            0,
            0,
            142,
            72);

    private static UITexture smoothProcessTexture(String domain, String path, int x, int y, int w, int h) {
        final float size = 256f;
        return UITexture.builder().location(domain, path).imageSize(256, 256)
                .subAreaUV((x + 0.5f) / size, (y + 0.5f) / size, (x + w - 0.5f) / size, (y + h - 0.5f) / size).build();
    }

    public static IDrawable slotBackground(UITexture icon) {
        return IDrawable.of(VANILLA_SLOT, icon.asIcon().margin(1));
    }

    public static SlotGroupWidget vanillaPlayerInventory(int x, int y) {
        return SlotGroupWidget.playerInventory((index, slot) -> slot.background(VANILLA_SLOT)).pos(x, y);
    }

    public static void addButtonColumn(ModularPanel panel, PanelSyncManager syncManager, Machine machine) {
        addButtonColumn(panel, syncManager, machine, BUTTON_COLUMN_X, PLAYER_INV_Y);
    }

    public static void addButtonColumn(ModularPanel panel, PanelSyncManager syncManager, Machine machine,
            int playerInvY) {
        addButtonColumn(panel, syncManager, machine, BUTTON_COLUMN_X, playerInvY);
    }

    public static void addButtonColumn(ModularPanel panel, PanelSyncManager syncManager, Machine machine,
            int buttonColumnX, int playerInvY) {
        addButtonColumn(panel, syncManager, machine, buttonColumnX, playerInvY, playerInvY - CONTENT_Y - 4);
    }

    public static void addButtonColumn(ModularPanel panel, PanelSyncManager syncManager, Machine machine,
            int buttonColumnX, int playerInvY, int energyBarHeight) {
        addEnergyBar(panel, syncManager, machine, buttonColumnX, CONTENT_Y, energyBarHeight);

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
        }).tooltipAutoUpdate(true).pos(buttonColumnX, playerInvY + 18).size(18, 18));

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
        }).tooltipAutoUpdate(true).pos(buttonColumnX, playerInvY + 36).size(18, 18));

        IProcess process = machine != null ? machine.getInterface(IProcess.class) : null;
        BooleanSyncValue enabledSync = new BooleanSyncValue(
                () -> process != null && process.isMachineEnabled(),
                val -> { if (process != null) process.setMachineEnabled(val); });

        panel.child(
                new ToggleButton().value(enabledSync).overlay(true, POWER_SWITCH_ON).overlay(false, POWER_SWITCH_OFF)
                        .tooltipBuilder(true, tip -> tip.addLine(IKey.lang("genetics.gui.machine_enabled")))
                        .tooltipBuilder(false, tip -> tip.addLine(IKey.lang("genetics.gui.machine_disabled")))
                        .size(18, 18).pos(buttonColumnX, playerInvY + 58));
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
            int width, int height, String neiHandlerId) {
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
                        .horizontal().neiTransferRect(neiHandlerId).tooltipBuilder(tip -> {
                            int pct = (int) (progressSync.getDoubleValue() * 100);
                            tip.addLine(IKey.lang("genetics.gui.progress", pct));
                            if (neiHandlerId != null) {
                                tip.addLine(IKey.str("Recipes"));
                            }
                        }).tooltipAutoUpdate(true).pos(x, y).size(width, height));
    }

    public static void addProcessAnimation(ModularPanel panel, PanelSyncManager syncManager, Machine machine,
            UITexture emptyTexture, UITexture fullTexture, int x, int y, int width, int height, int imageWidth,
            String neiHandlerId) {
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

        final ResourceLocation texLoc = fullTexture.location;
        panel.child(
                new NeiProgressWidget(neiHandlerId, texLoc).texture(emptyTexture, fullTexture, imageWidth)
                        .direction(ProgressWidget.Direction.RIGHT).syncHandler("progress").tooltipBuilder(tip -> {
                            int pct = (int) (progressSync.getDoubleValue() * 100);
                            tip.addLine(IKey.lang("genetics.gui.progress", pct));
                            if (neiHandlerId != null) {
                                tip.addLine(IKey.str("Recipes"));
                            }
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

    public static String getTankHint(ITankMachine tanks, int tankIndex, String fallback) {
        if (tanks == null) return fallback;
        TankSlot slot = tanks.getTankSlot(tankIndex);
        if (slot != null && slot.getValidator() != null) {
            String tip = slot.getValidator().getTooltip();
            if (tip != null && !tip.isEmpty()) {
                return tip;
            }
        }
        return fallback;
    }

    private static class NeiProgressWidget extends ProgressWidget implements Interactable {

        private final String neiHandlerId;
        private final ResourceLocation texLoc;

        NeiProgressWidget(String neiHandlerId, ResourceLocation texLoc) {
            this.neiHandlerId = neiHandlerId;
            this.texLoc = texLoc;
        }

        @Override
        public void draw(ModularGuiContext context, WidgetThemeEntry<?> entry) {
            Minecraft.getMinecraft().renderEngine.bindTexture(texLoc);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            super.draw(context, entry);
            Minecraft.getMinecraft().renderEngine.bindTexture(texLoc);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }

        @Override
        public Interactable.Result onMousePressed(int mouseButton) {
            if (neiHandlerId != null) {
                GuiCraftingRecipe.openRecipeGui(neiHandlerId);
                return Interactable.Result.SUCCESS;
            }
            return Interactable.super.onMousePressed(mouseButton);
        }
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
        return createMachinePanel(data, syncManager, panelId, PANEL_WIDTH, panelHeight);
    }

    public static MachineGuiContext createMachinePanel(PosGuiData data, PanelSyncManager syncManager, String panelId,
            int panelWidth, int panelHeight) {
        TileEntity te = data.getTileEntity();
        InvWrapper inv = new InvWrapper((IInventory) te);
        ITankMachine tanks = te instanceof ITankMachine ? (ITankMachine) te : null;

        Machine machine = null;
        String title = "";
        if (te instanceof TileEntityMachine tem) {
            machine = tem.getMachine();
            title = machine.getPackage().getGuiDisplayName();
        }

        ModularPanel panel = ModularPanel.defaultPanel(panelId, panelWidth, panelHeight);
        panel.background(VANILLA_BACKGROUND);
        syncManager.registerSlotGroup("machine", 9);
        panel.child(new TextWidget<>(IKey.str(title)).color(0x404040).pos(PAD, 6).size(panelWidth - PAD * 2, 9));

        return new MachineGuiContext(panel, machine, inv, tanks);
    }
}
