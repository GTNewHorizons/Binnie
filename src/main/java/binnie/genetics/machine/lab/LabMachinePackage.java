package binnie.genetics.machine.lab;

import net.minecraft.client.renderer.RenderBlocks;

import binnie.core.machines.Machine;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.ComponentGUIHolder;
import binnie.genetics.machine.MachineRendererLab;
import binnie.genetics.machine.PackageGeneticBase;

public class LabMachinePackage extends PackageGeneticBase {

    public LabMachinePackage() {
        super("labMachine", GeneticsTexture.LabMachine, 0xffffff, false);
    }

    @Override
    public void createMachine(Machine machine) {
        new ComponentGUIHolder(machine);
    }

    @Override
    public void renderMachine(Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
        MachineRendererLab.instance.renderMachine(machine, renderTexture, x, y, z);
    }
}
