package binnie.core.machines.storage;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.util.I18N;

enum Compartment implements IMachineType {

    Compartment(StandardCompartment.PackageCompartment.class),
    CompartmentCopper(StandardCompartment.PackageCompartmentCopper.class),
    CompartmentBronze(StandardCompartment.PackageCompartmentBronze.class),
    CompartmentIron(StandardCompartment.PackageCompartmentIron.class),
    CompartmentGold(StandardCompartment.PackageCompartmentGold.class),
    CompartmentDiamond(StandardCompartment.PackageCompartmentDiamond.class);

    final Class<? extends MachinePackage> clss;

    Compartment(Class<? extends MachinePackage> clss) {
        this.clss = clss;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return clss;
    }

    public ItemStack get(int i) {
        return new ItemStack(BinnieCore.packageCompartment.getBlock(), i, ordinal());
    }

    public abstract static class PackageCompartment extends MachinePackage {

        static final int ROWS_PER_TAB = 5;

        private final BinnieResource renderTexture;
        private final int tabs;
        private final int slotsPerTab;

        protected PackageCompartment(String uid, IBinnieTexture renderTexture, int tabs, int slotsPerTab) {
            super(uid, false);
            this.renderTexture = renderTexture.getTexture();
            this.tabs = tabs;
            this.slotsPerTab = slotsPerTab;
        }

        @Override
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, tabs, slotsPerTab);
        }

        @Override
        public void addTooltip(List<String> tooltip) {
            final int slotsPerRow = slotsPerTab / ROWS_PER_TAB;
            final int slotsTotal = this.tabs * slotsPerTab;
            tooltip.add(
                    I18N.localise(
                            "binniecore.machine.storage.tooltip.size",
                            slotsPerRow,
                            ROWS_PER_TAB,
                            this.tabs,
                            slotsTotal));
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public boolean isTickable() {
            return false;
        }

        @Override
        public void renderMachine(Machine machine, double x, double y, double z, float partialTick) {
            MachineRendererCompartment.instance.renderMachine(machine, renderTexture, x, y, z);
        }
    }
}
