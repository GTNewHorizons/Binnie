package binnie.core.machines;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

/**
 * Interface for machine components that can provide a MUI2 panel. Implement this on a {@link MachineComponent} to
 * enable MUI2 GUI for a machine.
 */
public interface IMui2MachineGuiProvider {

    ModularPanel buildMui2Panel(PosGuiData data, PanelSyncManager syncManager);
}
