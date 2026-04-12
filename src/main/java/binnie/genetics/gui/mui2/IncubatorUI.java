package binnie.genetics.gui.mui2;

import static binnie.genetics.gui.mui2.GeneticsGuiHelper.*;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
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
        String inputHint = GeneticsGuiHelper.getTankHint(ctx.tanks, Incubator.TANK_INPUT, "Liquid");
        panel.child(
                new FluidSlot().alwaysShowFull(false)
                        .syncHandler(
                                new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Incubator.TANK_INPUT), machine))
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.accepts", inputHint)))
                        .pos(PAD, y).size(18, 54));

        // Queue slots
        panel.child(
                SlotGroupWidget.builder().row("I").row("I").row("I")
                        .key(
                                'I',
                                i -> new ItemSlot().background(VANILLA_SLOT)
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
                new ItemSlot().background(VANILLA_SLOT).slot(incSlot)
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.incubating"))).pos(65, y + 18));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 87, y + 21, 28, 12, "genetics.incubator");

        // Output slots
        panel.child(SlotGroupWidget.builder().row("I").row("I").row("I").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Incubator.SLOT_OUTPUT[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().background(VANILLA_SLOT).slot(slot)
                    .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(133, y));

        // Output tank
        String outputHint = GeneticsGuiHelper.getTankHint(ctx.tanks, Incubator.TANK_OUTPUT, "Liquid");
        panel.child(
                new FluidSlot().alwaysShowFull(false).syncHandler(
                        new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Incubator.TANK_OUTPUT), machine))
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.accepts", outputHint)))
                        .pos(151, y).size(18, 54));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine, BUTTON_COLUMN_X, PLAYER_INV_Y, 54);

        panel.child(GeneticsGuiHelper.vanillaPlayerInventory(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
