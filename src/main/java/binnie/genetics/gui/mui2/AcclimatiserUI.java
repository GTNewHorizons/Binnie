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
import binnie.genetics.machine.acclimatiser.Acclimatiser;

public class AcclimatiserUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "acclimatiser", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Acclimatiser.SLOT_RESERVE[0], "Individual");
        String acclimatiserHint = GeneticsGuiHelper
                .getSlotHint(machine, Acclimatiser.SLOT_ACCLIMATISER[0], "Tolerance Item");

        // Reserve slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Acclimatiser.SLOT_RESERVE[i]).slotGroup("machine");
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> {
                tip.addLine(IKey.lang("genetics.gui.slot.input"));
                tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
            });
        }).build().pos(PAD, y));

        // Target slot
        ModularSlot targetSlot = new ModularSlot(ctx.inv, Acclimatiser.SLOT_TARGET).slotGroup("machine");
        targetSlot.canPut(false);
        panel.child(new ItemSlot().slot(targetSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.acclimatising"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
        }).pos(79, y + 9));

        // Acclimatiser item slots
        panel.child(
                SlotGroupWidget.builder().row("III").key(
                        'I',
                        i -> new ItemSlot()
                                .slot(new ModularSlot(ctx.inv, Acclimatiser.SLOT_ACCLIMATISER[i]).slotGroup("machine"))
                                .tooltipBuilder(tip -> {
                                    tip.addLine(IKey.lang("genetics.gui.slot.tolerance_item"));
                                    tip.addLine(IKey.lang("genetics.gui.slot.accepts", acclimatiserHint));
                                }))
                        .build().pos(61, y + 36));

        // Done slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Acclimatiser.SLOT_DONE[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(133, y));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
