package binnie.genetics.gui.mui2;

import static binnie.genetics.gui.mui2.GeneticsGuiHelper.*;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import binnie.core.machines.Machine;
import binnie.genetics.gui.mui2.GeneticsGuiHelper.MachineGuiContext;
import binnie.genetics.machine.splicer.Splicer;

public class SplicerUI {

    private static final int PANEL_HEIGHT = 176;
    private static final int SPLICER_PLAYER_INV_Y = 93;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "splicer", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String serumHint = GeneticsGuiHelper.getSlotHint(machine, Splicer.SLOT_SERUM_RESERVE[0], "Serum");
        String individualHint = GeneticsGuiHelper.getSlotHint(machine, Splicer.SLOT_RESERVE[0], "Individual");

        // Serum row
        int serumX = 31;

        // Serum reserve slots
        panel.child(
                SlotGroupWidget.builder().row("II").key(
                        'I',
                        i -> new ItemSlot().background(slotBackground(ICON_SERUM_INPUT))
                                .slot(new ModularSlot(ctx.inv, Splicer.SLOT_SERUM_RESERVE[i]).slotGroup("machine"))
                                .tooltipBuilder(tip -> {
                                    tip.addLine(IKey.lang("genetics.gui.slot.serum_input"));
                                    tip.addLine(IKey.lang("genetics.gui.slot.accepts", serumHint));
                                }))
                        .build().pos(serumX, y));

        // Arrow: reserve -> vial
        panel.child(new TextWidget<>(IKey.str(">")).pos(serumX + 38, y + 4).size(8, 10));

        // Serum vial slot
        ModularSlot serumVialSlot = new ModularSlot(ctx.inv, Splicer.SLOT_SERUM_VIAL).slotGroup("machine");
        serumVialSlot.canPut(false);
        panel.child(
                new ItemSlot().background(slotBackground(ICON_SERUM_OUTPUT)).slot(serumVialSlot).tooltipBuilder(tip -> {
                    tip.addLine(IKey.lang("genetics.gui.slot.active_serum"));
                    tip.addLine(IKey.lang("genetics.gui.slot.accepts", serumHint));
                }).pos(serumX + 48, y));

        // Arrow: vial -> expended
        panel.child(new TextWidget<>(IKey.str(">")).pos(serumX + 68, y + 4).size(8, 10));

        // Serum expended slots
        panel.child(SlotGroupWidget.builder().row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Splicer.SLOT_SERUM_EXPENDED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().background(slotBackground(ICON_SERUM_INPUT)).slot(slot)
                    .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.spent_serum")));
        }).build().pos(serumX + 78, y));

        int y2 = y + 30;

        // Reserve slots
        panel.child(
                SlotGroupWidget.builder().row("II").row("II")
                        .key(
                                'I',
                                i -> new ItemSlot().background(VANILLA_SLOT)
                                        .slot(new ModularSlot(ctx.inv, Splicer.SLOT_RESERVE[i]).slotGroup("machine"))
                                        .tooltipBuilder(tip -> {
                                            tip.addLine(IKey.lang("genetics.gui.slot.individual_input"));
                                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", individualHint));
                                        }))
                        .build().pos(PAD, y2));

        // Target slot
        ModularSlot targetSlot = new ModularSlot(ctx.inv, Splicer.SLOT_TARGET).slotGroup("machine");
        targetSlot.canPut(false);
        panel.child(new ItemSlot().background(VANILLA_SLOT).slot(targetSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.splicing"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", individualHint));
        }).pos(63, y2 + 9));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 85, y2 + 12, 28, 12);

        // Finished slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Splicer.SLOT_FINISHED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().background(VANILLA_SLOT).slot(slot)
                    .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(133, y2));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine, BUTTON_COLUMN_X, SPLICER_PLAYER_INV_Y, 66);

        panel.child(GeneticsGuiHelper.vanillaPlayerInventory(PLAYER_INV_X, SPLICER_PLAYER_INV_Y));

        return panel;
    }
}
