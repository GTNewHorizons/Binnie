package binnie.genetics.machine;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.cleanroommc.modularui.factory.GuiFactories;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import binnie.core.machines.IMui2MachineGuiProvider;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.gui.mui2.IMui2MachineGui;

public class ComponentGeneticGUI extends MachineComponent implements IInteraction.RightClick, IMui2MachineGuiProvider {

    private static final Map<GeneticsGUI, IMui2MachineGui> MUI2_GUIS = new EnumMap<>(GeneticsGUI.class);

    public GeneticsGUI id;

    public ComponentGeneticGUI(Machine machine, GeneticsGUI id) {
        super(machine);
        this.id = id;
    }

    public static void registerMui2Gui(GeneticsGUI id, IMui2MachineGui gui) {
        MUI2_GUIS.put(id, gui);
    }

    public static boolean hasMui2Gui(GeneticsGUI id) {
        return MUI2_GUIS.containsKey(id);
    }

    @Override
    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        if (MUI2_GUIS.containsKey(id)) {
            if (!world.isRemote) {
                GuiFactories.tileEntity().open(player, x, y, z);
            }
        } else {
            Genetics.proxy.openGui(id, player, x, y, z);
        }
    }

    @Override
    public ModularPanel buildMui2Panel(PosGuiData data, PanelSyncManager syncManager) {
        IMui2MachineGui gui = MUI2_GUIS.get(id);
        if (gui != null) {
            return gui.buildPanel(data, syncManager);
        }
        return null;
    }
}
