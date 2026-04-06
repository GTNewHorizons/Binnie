package binnie.genetics.gui.mui2;

import static binnie.genetics.gui.mui2.GeneticsGuiHelper.*;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import binnie.core.machines.Machine;
import binnie.genetics.gui.mui2.GeneticsGuiHelper.MachineGuiContext;
import binnie.genetics.machine.incubator.Incubator;

public class IncubatorUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "incubator", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String queueHint = GeneticsGuiHelper.getSlotHint(machine, Incubator.SLOT_QUEUE[0], "Incubation Input");

        // Input tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Incubator.TANK_INPUT)))
                        .pos(PAD, y).size(18, 54));

        // Queue slots
        panel.child(
                SlotGroupWidget.builder().row("I").row("I").row("I")
                        .key(
                                'I',
                                i -> new ItemSlot()
                                        .slot(new ModularSlot(ctx.inv, Incubator.SLOT_QUEUE[i]).slotGroup("machine"))
                                        .tooltipBuilder(tip -> {
                                            tip.addLine(IKey.lang("genetics.gui.slot.input_queue"));
                                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", queueHint));
                                        }))
                        .build().pos(29, y));

        // Incubator slot
        ModularSlot incSlot = new ModularSlot(ctx.inv, Incubator.SLOT_INCUBATOR).slotGroup("machine");
        incSlot.canPut(false);
        panel.child(
                new ItemSlot().slot(incSlot)
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.incubating"))).pos(65, y + 18));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 87, y + 21, 28, 12);

        // Output slots
        panel.child(SlotGroupWidget.builder().row("I").row("I").row("I").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Incubator.SLOT_OUTPUT[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(133, y));

        // Output tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Incubator.TANK_OUTPUT)))
                        .pos(151, y).size(18, 54));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
