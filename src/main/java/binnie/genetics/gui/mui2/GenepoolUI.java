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
import binnie.genetics.machine.genepool.Genepool;

public class GenepoolUI {

    private static final int PANEL_HEIGHT = 166;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "genepool", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Genepool.SLOT_RESERVE[0], "Individual");
        String enzymeHint = GeneticsGuiHelper.getSlotHint(machine, Genepool.SLOT_ENZYME, "Enzyme");

        // Ethanol tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Genepool.TANK_ETHANOL)))
                        .pos(PAD, y).size(18, 54));

        // Reserve slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Genepool.SLOT_RESERVE[i]).slotGroup("machine");
            return new ItemSlot().slot(slot).tooltipBuilder(tip -> {
                tip.addLine(IKey.lang("genetics.gui.slot.input"));
                tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
            });
        }).build().pos(29, y));

        // Enzyme slot
        ModularSlot enzymeSlot = new ModularSlot(ctx.inv, Genepool.SLOT_ENZYME).slotGroup("machine");
        panel.child(new ItemSlot().background(slotBackground(ICON_ENZYME)).slot(enzymeSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.enzyme"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", enzymeHint));
        }).pos(72, y + 18));

        // Enzyme charge bar
        GeneticsGuiHelper.addChargeBar(
                panel,
                syncManager,
                machine,
                Genepool.SLOT_ENZYME,
                "Enzyme Remaining",
                "charge_enzyme",
                72,
                y + 36);

        // Bee slot
        ModularSlot beeSlot = new ModularSlot(ctx.inv, Genepool.SLOT_BEE).slotGroup("machine");
        beeSlot.canPut(false);
        panel.child(new ItemSlot().slot(beeSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.processing"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
        }).pos(94, y + 18));

        // Progress bar
        GeneticsGuiHelper.addProgressBar(panel, syncManager, machine, 116, y + 21, 28, 12);

        // DNA tank
        panel.child(
                new FluidSlot().syncHandler(new FluidSlotSyncHandler(ctx.tanks.getTank(Genepool.TANK_DNA))).pos(151, y)
                        .size(18, 54));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine);

        panel.child(SlotGroupWidget.playerInventory(false).pos(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
