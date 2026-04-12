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
import binnie.genetics.machine.genepool.Genepool;

public class GenepoolUI {

    private static final int PANEL_HEIGHT = 159;
    private static final int PLAYER_INV_Y = 76;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper.createMachinePanel(data, syncManager, "genepool", PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String reserveHint = GeneticsGuiHelper.getSlotHint(machine, Genepool.SLOT_RESERVE[0], "Individual");
        String enzymeHint = GeneticsGuiHelper.getSlotHint(machine, Genepool.SLOT_ENZYME, "Enzyme");

        // Ethanol tank
        String ethanolHint = GeneticsGuiHelper.getTankHint(ctx.tanks, Genepool.TANK_ETHANOL, "Ethanol");
        panel.child(
                new FluidSlot().alwaysShowFull(false).syncHandler(
                        new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Genepool.TANK_ETHANOL), machine))
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.accepts", ethanolHint)))
                        .pos(PAD, y).size(18, 54));

        // Reserve slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Genepool.SLOT_RESERVE[i]).slotGroup("machine");
            return new ItemSlot().background(VANILLA_SLOT).slot(slot).tooltipBuilder(tip -> {
                tip.addLine(IKey.lang("genetics.gui.slot.input"));
                tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
            });
        }).build().pos(29, y));

        // Processing and enzyme slots
        int centerY = y + 27;
        int pairTop = centerY - 18;

        ModularSlot beeSlot = new ModularSlot(ctx.inv, Genepool.SLOT_BEE).slotGroup("machine");
        beeSlot.canPut(false);
        panel.child(new ItemSlot().background(VANILLA_SLOT).slot(beeSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.processing"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", reserveHint));
        }).pos(72, pairTop));

        ModularSlot enzymeSlot = new ModularSlot(ctx.inv, Genepool.SLOT_ENZYME).slotGroup("machine");
        panel.child(new ItemSlot().background(slotBackground(ICON_ENZYME)).slot(enzymeSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.enzyme"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", enzymeHint));
        }).pos(72, pairTop + 18));

        // Enzyme charge bar
        GeneticsGuiHelper.addChargeBar(
                panel,
                syncManager,
                machine,
                Genepool.SLOT_ENZYME,
                "Enzyme Remaining",
                "charge_enzyme",
                72,
                pairTop + 36);

        // Process animation
        GeneticsGuiHelper.addProcessAnimation(
                panel,
                syncManager,
                machine,
                GENEPOOL_PROCESS_BASE,
                GENEPOOL_PROCESS_FULL,
                92,
                centerY - 8,
                60,
                16,
                79);

        // DNA tank (output)
        panel.child(
                new FluidSlot().alwaysShowFull(false)
                        .syncHandler(
                                new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Genepool.TANK_DNA), machine))
                        .pos(154, y).size(18, 54));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine, PLAYER_INV_Y);

        panel.child(GeneticsGuiHelper.vanillaPlayerInventory(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
