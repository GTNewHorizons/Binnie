package binnie.genetics.gui.mui2;

import static binnie.genetics.gui.mui2.GeneticsGuiHelper.*;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.mojang.authlib.GameProfile;

import binnie.core.machines.Machine;
import binnie.core.util.I18N;
import binnie.genetics.gui.mui2.GeneticsGuiHelper.MachineGuiContext;
import binnie.genetics.machine.sequencer.Sequencer;

public class SequencerUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "sequencer", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Sequencer.SLOT_RESERVE[0], "Unsequenced");
        String dyeHint = GeneticsGuiHelper.getSlotHint(machine, Sequencer.SLOT_DYE_INDEX, "Fluorescent Dye");

        // Reserve slots
        panel.child(
                SlotGroupWidget.builder().row("II").row("II")
                        .key(
                                'I',
                                i -> new ItemSlot().background(slotBackground(ICON_SEQUENCER_VIAL))
                                        .slot(new ModularSlot(ctx.inv, Sequencer.SLOT_RESERVE[i]).slotGroup("machine"))
                                        .tooltipBuilder(tip -> {
                                            tip.addLine(IKey.lang("genetics.gui.slot.input"));
                                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
                                        }))
                        .build().pos(PAD, y));

        // Target slot
        ModularSlot targetSlot = new ModularSlot(ctx.inv, Sequencer.SLOT_TARGET_INDEX).slotGroup("machine");
        targetSlot.canPut(false);
        panel.child(
                new ItemSlot().background(slotBackground(ICON_SEQUENCER_VIAL_FILLED)).slot(targetSlot)
                        .tooltipBuilder(tip -> {
                            tip.addLine(IKey.lang("genetics.gui.slot.sequencing"));
                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
                        }).pos(69, y + 10));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 96, y + 14, 28, 12, "genetics.sequencer");

        // Done slot
        ModularSlot doneSlot = new ModularSlot(ctx.inv, Sequencer.SLOT_DONE_INDEX).slotGroup("machine");
        doneSlot.canPut(false);
        panel.child(
                new ItemSlot().background(VANILLA_SLOT).slot(doneSlot)
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output"))).pos(133, y + 10));

        // Dye slot
        panel.child(
                new ItemSlot().background(slotBackground(ICON_DYE))
                        .slot(new ModularSlot(ctx.inv, Sequencer.SLOT_DYE_INDEX).slotGroup("machine"))
                        .tooltipBuilder(tip -> {
                            tip.addLine(IKey.lang("genetics.gui.slot.dye"));
                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", dyeHint));
                        }).pos(PAD, y + 40));

        // Dye charge bar
        GeneticsGuiHelper.addChargeBar(
                panel,
                syncManager,
                machine,
                Sequencer.SLOT_DYE_INDEX,
                "Dye Remaining",
                "charge_dye",
                PAD,
                y + 58);

        StringSyncValue seqOwnerSync = new StringSyncValue(() -> {
            if (machine == null) return "";
            GameProfile owner = machine.getOwner();
            return owner != null && owner.getName() != null ? owner.getName() : "";
        });
        syncManager.syncValue("seq_owner", seqOwnerSync);

        panel.child(new TextWidget<>(IKey.dynamic(() -> {
            String name = seqOwnerSync.getValue();
            if (name != null && !name.isEmpty()) {
                return I18N.localise("genetics.machine.sequencer.gui.sequensedBy", name);
            }
            return I18N.localise("genetics.machine.sequencer.gui.willNotSave");
        })).pos(30, y + 48).size(135, 9));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(GeneticsGuiHelper.vanillaPlayerInventory(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
