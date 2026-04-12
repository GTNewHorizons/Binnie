package binnie.genetics.gui.mui2;

import static binnie.genetics.gui.mui2.GeneticsGuiHelper.*;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.slot.FluidSlot;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import binnie.core.machines.Machine;
import binnie.genetics.gui.mui2.GeneticsGuiHelper.MachineGuiContext;
import binnie.genetics.machine.polymeriser.Polymeriser;

public class PolymeriserUI {

    private static final int PANEL_WIDTH = 198;
    private static final int PANEL_HEIGHT = 179;
    private static final int PLAYER_INV_Y = 96;
    private static final int PLAYER_INV_X = PAD;
    private static final int BUTTON_COLUMN_X = 174;

    public static ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager) {
        MachineGuiContext ctx = GeneticsGuiHelper
                .createMachinePanel(data, syncManager, "polymeriser", PANEL_WIDTH, PANEL_HEIGHT);
        ModularPanel panel = ctx.panel;
        Machine machine = ctx.machine;

        int y = CONTENT_Y;

        String serumHint = GeneticsGuiHelper.getSlotHint(machine, Polymeriser.SLOT_SERUM_RESERVE[0], "Empty Serum");
        String goldHint = GeneticsGuiHelper.getSlotHint(machine, Polymeriser.SLOT_GOLD, "Gold Nugget");

        // Serum reserve slots
        panel.child(
                SlotGroupWidget.builder().row("I").row("I").row("I").row("I").key(
                        'I',
                        i -> new ItemSlot().background(slotBackground(ICON_SERUM_INPUT))
                                .slot(new ModularSlot(ctx.inv, Polymeriser.SLOT_SERUM_RESERVE[i]).slotGroup("machine"))
                                .tooltipBuilder(tip -> {
                                    tip.addLine(IKey.lang("genetics.gui.slot.empty_serum"));
                                    tip.addLine(IKey.lang("genetics.gui.slot.accepts", serumHint));
                                }))
                        .build().pos(PAD, y));

        // Arrow: input -> replicating
        panel.child(new TextWidget<>(IKey.str(">")).color(0x404040).pos(32, y + 4).size(8, 10));

        // Serum/replicating slot
        ModularSlot serumSlot = new ModularSlot(ctx.inv, Polymeriser.SLOT_SERUM).slotGroup("machine");
        serumSlot.canPut(false);
        panel.child(new ItemSlot().background(slotBackground(ICON_SERUM_OUTPUT)).slot(serumSlot).tooltipBuilder(tip -> {
            tip.addLine(IKey.lang("genetics.gui.slot.replicating"));
            tip.addLine(IKey.lang("genetics.gui.slot.accepts", serumHint));
        }).pos(46, y));

        // Fluid tanks
        int tankY = y + 18;
        int tankH = 54;
        String bacteriaHint = GeneticsGuiHelper.getTankHint(ctx.tanks, Polymeriser.TANK_BACTERIA, "Bacteria");
        String dnaHint = GeneticsGuiHelper.getTankHint(ctx.tanks, Polymeriser.TANK_DNA, "DNA");
        panel.child(
                new FluidSlot().background(VANILLA_SLOT).alwaysShowFull(false).syncHandler(
                        new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Polymeriser.TANK_BACTERIA), machine))
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.accepts", bacteriaHint)))
                        .pos(28, tankY).size(18, tankH));

        panel.child(
                new FluidSlot().background(VANILLA_SLOT).alwaysShowFull(false)
                        .syncHandler(
                                new ValidatedFluidSlotSyncHandler(ctx.tanks.getTankSlot(Polymeriser.TANK_DNA), machine))
                        .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.accepts", dnaHint)))
                        .pos(46, tankY).size(18, tankH));

        // Gold/catalyst slot
        panel.child(
                new ItemSlot().background(slotBackground(ICON_NUGGET))
                        .slot(new ModularSlot(ctx.inv, Polymeriser.SLOT_GOLD).slotGroup("machine"))
                        .tooltipBuilder(tip -> {
                            tip.addLine(IKey.lang("genetics.gui.slot.catalyst"));
                            tip.addLine(IKey.lang("genetics.gui.slot.accepts", goldHint));
                        }).pos(PLAYER_INV_X + 9 * 18 - 18, PLAYER_INV_Y - 4 - 18));

        // Process animation
        GeneticsGuiHelper.addProcessAnimation(
                panel,
                syncManager,
                machine,
                POLYMERISER_PROCESS_BASE,
                POLYMERISER_PROCESS_FULL,
                64,
                y,
                68,
                49,
                110);

        // Output slots
        panel.child(SlotGroupWidget.builder().row("II").row("II").key('I', i -> {
            ModularSlot slot = new ModularSlot(ctx.inv, Polymeriser.SLOT_SERUM_FINISHED[i]).slotGroup("machine");
            slot.canPut(false);
            return new ItemSlot().background(VANILLA_SLOT).slot(slot)
                    .tooltipBuilder(tip -> tip.addLine(IKey.lang("genetics.gui.slot.filled_serum")));
        }).build().pos(PLAYER_INV_X + 9 * 18 - 36, y));

        GeneticsGuiHelper.addButtonColumn(panel, syncManager, machine, BUTTON_COLUMN_X, PLAYER_INV_Y);

        panel.child(GeneticsGuiHelper.vanillaPlayerInventory(PLAYER_INV_X, PLAYER_INV_Y));

        return panel;
    }
}
