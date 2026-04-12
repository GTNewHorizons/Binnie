package binnie.genetics.gui.mui2;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

/**
 * Interface for providing a MUI2 panel for a genetics machine. Register implementations via
 * {@link binnie.genetics.machine.ComponentGeneticGUI#registerMui2Gui}.
 */
@FunctionalInterface
public interface IMui2MachineGui {

    ModularPanel buildPanel(PosGuiData data, PanelSyncManager syncManager);
}
