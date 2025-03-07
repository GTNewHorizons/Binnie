package binnie.core.machines;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;

import binnie.Binnie;
import binnie.core.AbstractMod;
import cpw.mods.fml.common.registry.GameRegistry;

public class MachineGroup {

    private final AbstractMod mod;
    private final String blockName;
    private final String uid;
    private final Map<String, MachinePackage> packages;
    private final Map<Integer, MachinePackage> packagesID;
    private final BlockMachine block;
    public boolean customRenderer;
    private boolean renderedTileEntity;

    public MachineGroup(AbstractMod mod, String uid, String blockName, IMachineType[] types) {
        packages = new LinkedHashMap<>();
        packagesID = new LinkedHashMap<>();
        customRenderer = true;
        renderedTileEntity = true;
        this.mod = mod;
        this.uid = uid;
        this.blockName = blockName;

        for (IMachineType type : types) {
            if (type.getPackageClass() != null) {
                try {
                    MachinePackage pack = type.getPackageClass().newInstance();
                    pack.assignMetadata(type.ordinal());
                    pack.setActive(true);
                    addPackage(pack);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create machine package " + type, e);
                }
            }
        }
        Binnie.Machine.registerMachineGroup(this);
        block = new BlockMachine(this, blockName);
        GameRegistry.registerBlock(block, ItemMachine.class, blockName);
    }

    private void addPackage(MachinePackage pack) {
        packages.put(pack.getUID(), pack);
        packagesID.put(pack.getMetadata(), pack);
        pack.setGroup(this);
    }

    public Collection<MachinePackage> getPackages() {
        return packages.values();
    }

    public BlockMachine getBlock() {
        return block;
    }

    public MachinePackage getPackage(int metadata) {
        return packagesID.get(metadata);
    }

    public MachinePackage getPackage(String name) {
        return packages.get(name);
    }

    public String getUID() {
        return mod.getModID() + "." + uid;
    }

    public String getShortUID() {
        return uid;
    }

    boolean isTileEntityRenderered() {
        return renderedTileEntity;
    }

    public void renderAsBlock() {
        renderedTileEntity = false;
    }

    public void setCreativeTab(CreativeTabs tab) {
        block.setCreativeTab(tab);
    }

    public AbstractMod getMod() {
        return mod;
    }
}
