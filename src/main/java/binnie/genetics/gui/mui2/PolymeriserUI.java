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
import binnie.genetics.machine.polymeriser.Polymeriser;

public class PolymeriserUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "polymeriser", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String serumHint = GeneticsGuiHelper.getSlotHint(machine, Polymeriser.SLOT_SERUM_RESERVE[0], "Empty Serum");
        String goldHint = GeneticsGuiHelper.getSlotHint(machine, Polymeriser.SLOT_GOLD, "Gold Nugget");

        // Bacteria tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Polymeriser.TANK_BACTERIA)))
                        .pos(PAD, y).size(18, 28));

        // DNA tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Polymeriser.TANK_DNA)))
                        .pos(PAD, y + 30).size(18, 28));

        // Serum reserve slots
        panel.child(
                SlotGroupWidget.builder().row("II").row("II").key(
                        'I',
                        i -> new ItemSlot().background(slotBackground(ICON_SERUM_INPUT))
                                .slot(new ModularSlot(ctx.inv, Polymeriser.SLOT_SERUM_RESERVE[i]).slotGroup("machine"))
                                .tooltipBuilder(tip -> {
                                    tip.addLine(IKey.lang("genetics.gui.slot.empty_serum"));
                                    tip.addLine(IKey.lang("genetics.gui.slot.accepts", serumHint));
                                }))
                        .build().pos(29, y));

        // Serum slot
        ModularSlot serumSlot = new ModularSlot(ctx.inv, Polymeriser.SLOT_SERUM).slotGroup("machine");
        serumSlot.canPut(false);
        panel.child(new ItemSlot().background(slotBackground(ICON_SERUM_OUTPUT)).slot(serumSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.replicating"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", serumHint));
        }).pos(74, y + 9));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 96, y + 12, 28, 12);

        // Gold slot
        panel.child(
                new ItemSlot().background(slotBackground(ICON_NUGGET))
                        .slot(new ModularSlot(ctx.inv, Polymeriser.SLOT_GOLD).slotGroup("machine"))
                        .tooltipBuilder(tip -> {
                            tip.addLine(IKey.lang("genetics.gui.slot.catalyst"));
                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", goldHint));
                        }).pos(74, y + 29));

        // Finished slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Polymeriser.SLOT_SERUM_FINISHED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().slot(slot)
                    .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.filled_serum")));
        }).build().pos(133, y));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
