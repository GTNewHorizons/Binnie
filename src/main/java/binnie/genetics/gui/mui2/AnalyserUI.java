package binnie.genetics.gui.mui2;

import static binnie.genetics.gui.mui2.GeneticsGuiHelper.*;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import binnie.core.machines.Machine;
import binnie.genetics.gui.mui2.GeneticsGuiHelper.MachineGuiContext;
import binnie.genetics.machine.analyser.Analyser;

public class AnalyserUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "analyser", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Analyser.SLOT_RESERVE[0], "Unanalysed Individual");
        String dyeHint = GeneticsGuiHelper.getSlotHint(machine, Analyser.SLOT_DYE, "Dye");

        // Reserve slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Analyser.SLOT_RESERVE[i]).slotGroup("machine");
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> {
                tip.addLine(IKey.lang("genetics.gui.slot.input"));
                tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
            });
        }).build().pos(PAD, y));

        // Dye slot
        ModularSlot dyeSlot = new ModularSlot(ctx.inv, Analyser.SLOT_DYE).slotGroup("machine");
        panel.child(new ItemSlot().background(slotBackground(ICON_DYE)).slot(dyeSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.dye"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", dyeHint));
        }).pos(52, y + 18));

        // Dye charge bar
        GeneticsGuiHelper.addChargeBar(
                panel,
                syncManager,
                machine,
                Analyser.SLOT_DYE,
                "Dye Remaining",
                "charge_dye",
                52,
                y + 36);

        // Target slot
        ModularSlot targetSlot = new ModularSlot(ctx.inv, Analyser.SLOT_TARGET).slotGroup("machine");
        targetSlot.canPut(false);
        panel.child(new ItemSlot().slot(targetSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.analysing"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
        }).pos(74, y + 18));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 96, y + 21, 28, 12);

        // Finished slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Analyser.SLOT_FINISHED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(133, y));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
