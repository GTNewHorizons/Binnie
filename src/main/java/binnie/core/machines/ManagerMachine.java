package binnie.core.machines;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import forestry.api.core.INBTTagable;

public class ManagerMachine extends ManagerBase {

    private final Map<Class<?>, Class<?>[]> componentInterfaceMap = new HashMap<>();
    private final Map<String, MachineGroup> machineGroups = new HashMap<>();
    private final Map<Integer, Class<?>> networkIDToComponent = new HashMap<>();
    private final Map<Class<?>, Integer> componentToNetworkID = new HashMap<>();
    private int nextNetworkID = 0;
    private int machineRenderID;

    public ManagerMachine() {}

    public void registerMachineGroup(MachineGroup group) {
        machineGroups.put(group.getUID(), group);
    }

    public MachineGroup getGroup(String name) {
        return machineGroups.get(name);
    }

    public MachinePackage getPackage(String group, String name) {
        MachineGroup machineGroup = getGroup(group);
        return (machineGroup == null) ? null : machineGroup.getPackage(name);
    }

    private void registerComponentClass(Class<? extends MachineComponent> component) {
        if (componentInterfaceMap.containsKey(component)) {
            return;
        }

        Set<Class<?>> interfaces = new HashSet<>();
        for (Class<?> currentClass = component; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Collections.addAll(interfaces, currentClass.getInterfaces());
        }

        interfaces.remove(INBTTagable.class);
        componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
        int networkID = nextNetworkID++;
        networkIDToComponent.put(networkID, component);
        componentToNetworkID.put(component, networkID);
    }

    public int getNetworkID(Class<?> component) {
        return componentToNetworkID.get(component);
    }

    public Class<?> getComponentClass(int networkID) {
        return networkIDToComponent.get(networkID);
    }

    public int getMachineRenderID() {
        return machineRenderID;
    }

    @Override
    public void init() {
        machineRenderID = BinnieCore.proxy.getUniqueRenderID();
        SlotValidator.IconBee = new ValidatorIcon(BinnieCore.instance, "validator/bee.0", "validator/bee.1");
        SlotValidator.IconFrame = new ValidatorIcon(BinnieCore.instance, "validator/frame.0", "validator/frame.1");
        SlotValidator.IconCircuit = new ValidatorIcon(
                BinnieCore.instance,
                "validator/circuit.0",
                "validator/circuit.1");
        SlotValidator.IconBlock = new ValidatorIcon(BinnieCore.instance, "validator/block.0", "validator/block.1");
    }

    @Override
    public void postInit() {
        BinnieCore.proxy.registerBlockRenderer(BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
        BinnieCore.proxy.registerTileEntity(
                TileEntityMachine.class,
                "binnie.tile.machine",
                BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
    }

    public Class<?>[] getComponentInterfaces(Class<? extends MachineComponent> clss) {
        if (!componentInterfaceMap.containsKey(clss)) {
            registerComponentClass(clss);
        }
        return componentInterfaceMap.get(clss);
    }
}
