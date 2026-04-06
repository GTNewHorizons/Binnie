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
import binnie.genetics.machine.isolator.Isolator;

public class IsolatorUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "isolator", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Isolator.SLOT_RESERVE[0], "Individual");
        String enzymeHint = GeneticsGuiHelper.getSlotHint(machine, Isolator.SLOT_ENZYME, "Enzyme");
        String vialHint = GeneticsGuiHelper.getSlotHint(machine, Isolator.SLOT_SEQUENCER_VIAL, "Empty Sequencer");

        // Ethanol tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Isolator.TANK_ETHANOL)))
                        .pos(PAD, y).size(18, 54));

        // Reserve slots
        panel.child(
                SlotGroupWidget.builder().row("I").row("I").row("I")
                        .key(
                                'I',
                                i -> new ItemSlot()
                                        .slot(new ModularSlot(ctx.inv, Isolator.SLOT_RESERVE[i]).slotGroup("machine"))
                                        .tooltipBuilder(tip -> {
                                            tip.addLine(IKey.lang("genetics.gui.slot.input"));
                                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
                                        }))
                        .build().pos(29, y));

        // Target slot
        ModularSlot targetSlot = new ModularSlot(ctx.inv, Isolator.SLOT_TARGET).slotGroup("machine");
        targetSlot.canPut(false);
        panel.child(new ItemSlot().slot(targetSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.processing"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
        }).pos(51, y));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 73, y + 3, 28, 12);

        // Result slot
        ModularSlot resultSlot = new ModularSlot(ctx.inv, Isolator.SLOT_RESULT).slotGroup("machine");
        resultSlot.canPut(false);
        panel.child(
                new ItemSlot().slot(resultSlot)
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.isolated_gene"))).pos(105, y));

        // Finished slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Isolator.SLOT_FINISHED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(133, y));

        int yBottom = y + 36;

        // Enzyme slot
        panel.child(
                new ItemSlot().background(slotBackground(ICON_ENZYME))
                        .slot(new ModularSlot(ctx.inv, Isolator.SLOT_ENZYME).slotGroup("machine"))
                        .tooltipBuilder(tip -> {
                            tip.addLine(IKey.lang("genetics.gui.slot.enzyme"));
                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", enzymeHint));
                        }).pos(51, yBottom));

        // Sequencer vial slot
        panel.child(
                new ItemSlot().background(slotBackground(ICON_SEQUENCER_VIAL))
                        .slot(new ModularSlot(ctx.inv, Isolator.SLOT_SEQUENCER_VIAL).slotGroup("machine"))
                        .tooltipBuilder(tip -> {
                            tip.addLine(IKey.lang("genetics.gui.slot.sequencer_vial"));
                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", vialHint));
                        }).pos(105, yBottom));

        // Enzyme charge bar
        GeneticsGuiHelper.addChargeBar(
                panel,
                syncManager,
                machine,
                Isolator.SLOT_ENZYME,
                "Enzyme Remaining",
                "charge_enzyme",
                51,
                yBottom + 18);

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
