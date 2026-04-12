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
import binnie.genetics.machine.isolator.Isolator;

public class IsolatorUI {

    private static final int PANEL_WIDTH = 256;
    private static final int PANEL_HEIGHT = 165;
    private static final int PLAYER_INV_Y = 82;
    private static final int PLAYER_INV_X = 47;
    private static final int BUTTON_COLUMN_X = 232;
    private static final int ENERGY_BAR_HEIGHT = 81;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper
                .createMachinePanel(data, syncManager, "isolator", PANEL_WIDTH, PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Isolator.SLOT_RESERVE[0], "Individual");
        String enzymeHint = GeneticsGuiHelper.getSlotHint(machine, Isolator.SLOT_ENZYME, "Enzyme");
        String vialHint = GeneticsGuiHelper.getSlotHint(machine, Isolator.SLOT_SEQUENCER_VIAL, "Empty Sequencer");

        // Ethanol tank
        String ethanolHint = GeneticsGuiHelper.getTankHint(ctx.tanks, Isolator.TANK_ETHANOL, "Ethanol");
        panel.child(
                new FluidSlot().alwaysShowFull(false).syncHandler(
                        new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Isolator.TANK_ETHANOL), machine))
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.accepts", ethanolHint)))
                        .pos(PAD, y).size(18, 54));

        // Reserve slots
        panel.child(
                SlotGroupWidget.builder().row("I").row("I").row("I")
                        .key(
                                'I',
                                i -> new ItemSlot().background(VANILLA_SLOT)
                                        .slot(new ModularSlot(ctx.inv, Isolator.SLOT_RESERVE[i]).slotGroup("machine"))
                                        .tooltipBuilder(tip -> {
                                            tip.addLine(IKey.lang("genetics.gui.slot.input"));
                                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
                                        }))
                        .build().pos(29, y));

        // Target slot
        ModularSlot targetSlot = new ModularSlot(ctx.inv, Isolator.SLOT_TARGET).slotGroup("machine");
        targetSlot.canPut(false);
        panel.child(new ItemSlot().background(VANILLA_SLOT).slot(targetSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.processing"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
        }).pos(51, y));

        // Process animation
        GeneticsGuiHelper.addProcessAnimation(
                panel,
                syncManager,
                machine,
                ISOLATOR_PROCESS_BASE,
                ISOLATOR_PROCESS_FULL,
                73,
                y + 1,
                95,
                17,
                142);

        // Result slot
        ModularSlot resultSlot = new ModularSlot(ctx.inv, Isolator.SLOT_RESULT).slotGroup("machine");
        resultSlot.canPut(false);
        panel.child(
                new ItemSlot().background(VANILLA_SLOT).slot(resultSlot)
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.isolated_gene"))).pos(172, y));

        // Finished slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Isolator.SLOT_FINISHED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().background(VANILLA_SLOT).slot(slot)
                    .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.output")));
        }).build().pos(194, y));

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
                        }).pos(172, yBottom));

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

        GeneticsGuiHelper
                .addButtonColumn(panel, syncManager, machine, BUTTON_COLUMN_X, PLAYER_INV_Y, ENERGY_BAR_HEIGHT);

        panel.child(GeneticsGuiHelper.vanillaPlayerInventory(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
