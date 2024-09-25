package binnie.core.machines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import binnie.core.BinnieCore;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageTileNBT;
import binnie.core.network.packet.PacketPayload;
import cpw.mods.fml.relauncher.Side;
import forestry.api.core.INBTTagable;

public class Machine implements INetworkedEntity, INBTTagable, INetwork.TilePacketSync, IMachine, INetwork.GuiNBT {

    private final MachinePackage machinePackage;
    private final Map<Class<?>, List<MachineComponent>> componentInterfaceMap = new HashMap<>();
    private final Map<Class<? extends MachineComponent>, MachineComponent> componentMap = new HashMap<>();
    private final TileEntity tile;
    private boolean queuedInventoryUpdate = false;
    private GameProfile owner;
    private final MachineUtil machineUtil = new MachineUtil(this);

    public Machine(MachinePackage pack, TileEntity tile) {
        this.tile = tile;
        pack.createMachine(this);
        machinePackage = pack;
    }

    @Override
    public void addComponent(MachineComponent component) {
        if (component == null) {
            throw new NullPointerException("Can't have a null machine component!");
        }
        component.setMachine(this);
        componentMap.put(component.getClass(), component);
        for (Class<?> inter : component.getComponentInterfaces()) {
            if (!componentInterfaceMap.containsKey(inter)) {
                componentInterfaceMap.put(inter, new ArrayList<>());
            }
            componentInterfaceMap.get(inter).add(component);
        }
    }

    public Collection<MachineComponent> getComponents() {
        return componentMap.values();
    }

    public <T extends MachineComponent> T getComponent(Class<T> componentClass) {
        if (hasComponent(componentClass)) {
            return componentClass.cast(componentMap.get(componentClass));
        }
        return null;
    }

    @Override
    public <T> T getInterface(Class<T> cls) {
        if (hasInterface(cls)) {
            return getInterfaces(cls).get(0);
        }
        if (cls.isInstance(getPackage())) {
            return cls.cast(getPackage());
        }

        for (MachineComponent component : getComponents()) {
            if (cls.isInstance(component)) {
                return cls.cast(component);
            }
        }
        return null;
    }

    @Override
    public <T> List<T> getInterfaces(Class<T> cls) {
        ArrayList<T> interfaces = new ArrayList<>();
        if (!hasInterface(cls)) {
            return interfaces;
        }
        for (MachineComponent component : componentInterfaceMap.get(cls)) {
            interfaces.add(cls.cast(component));
        }
        return interfaces;
    }

    public boolean hasInterface(Class<?> interfaceClass) {
        return componentInterfaceMap.containsKey(interfaceClass);
    }

    public boolean hasComponent(Class<? extends MachineComponent> componentClass) {
        return componentMap.containsKey(componentClass);
    }

    @Override
    public TileEntity getTileEntity() {
        return tile;
    }

    public Side getSide() {
        return BinnieCore.proxy.isSimulating(getTileEntity().getWorldObj()) ? Side.SERVER : Side.CLIENT;
    }

    @Override
    public void writeToPacket(PacketPayload payload) {
        for (MachineComponent component : getComponents()) {
            if (component instanceof INetworkedEntity ne) {
                ne.writeToPacket(payload);
            }
        }
    }

    @Override
    public void readFromPacket(PacketPayload payload) {
        for (MachineComponent component : getComponents()) {
            if (component instanceof INetworkedEntity ne) {
                ne.readFromPacket(payload);
            }
        }
    }

    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        for (IInteraction.RightClick component : getInterfaces(IInteraction.RightClick.class)) {
            component.onRightClick(world, player, x, y, z);
        }
    }

    @Override
    public void markDirty() {
        queuedInventoryUpdate = true;
    }

    public void onUpdate() {
        if (BinnieCore.proxy.isSimulating(getWorld())) {
            for (MachineComponent component : getComponents()) {
                component.onUpdate();
            }
        } else {
            for (IRender.DisplayTick renders : getInterfaces(IRender.DisplayTick.class)) {
                TileEntity tileEntity = getTileEntity();
                renders.onDisplayTick(
                        getWorld(),
                        tileEntity.xCoord,
                        tileEntity.yCoord,
                        tileEntity.zCoord,
                        getWorld().rand);
            }
        }

        if (queuedInventoryUpdate) {
            for (MachineComponent component : getComponents()) {
                component.onInventoryUpdate();
            }
            queuedInventoryUpdate = false;
        }
    }

    public IInventory getInventory() {
        return getInterface(IInventory.class);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        for (MachineComponent component : getComponents()) {
            component.readFromNBT(nbttagcompound);
        }
        owner = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("owner"));
        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        for (MachineComponent component : getComponents()) {
            component.writeToNBT(nbttagcompound);
        }
        if (owner != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTUtil.func_152460_a(nbt, owner);
            nbttagcompound.setTag("owner", nbt);
        }
    }

    @Override
    public MachinePackage getPackage() {
        return machinePackage;
    }

    public static IMachine getMachine(Object inventory) {
        if (inventory instanceof IMachine m) {
            return m;
        }
        if (inventory instanceof TileEntityMachine tem) {
            return tem.getMachine();
        }
        if (inventory instanceof MachineComponent mc) {
            return mc.getMachine();
        }
        return null;
    }

    public static <T> T getInterface(Class<T> interfac, Object inventory) {
        IMachine machine = getMachine(inventory);
        if (machine != null) {
            return machine.getInterface(interfac);
        }
        if (interfac.isInstance(inventory)) {
            return interfac.cast(inventory);
        }
        return null;
    }

    @Override
    public MachineUtil getMachineUtil() {
        return machineUtil;
    }

    @Override
    public World getWorld() {
        return getTileEntity().getWorldObj();
    }

    public void onBlockDestroy() {
        for (MachineComponent component : getComponents()) {
            component.onDestruction();
        }
    }

    @Override
    public GameProfile getOwner() {
        return owner;
    }

    @Override
    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        syncToNBT(nbt);
        if (nbt.hasNoTags()) {
            return null;
        }
        MessageTileNBT tileNbt = new MessageTileNBT(
                BinnieCorePacketID.TileDescriptionSync.ordinal(),
                getTileEntity(),
                nbt);
        return BinnieCore.instance.getNetworkWrapper().getPacketFrom(tileNbt.getMessage());
    }

    @Override
    public void syncToNBT(NBTTagCompound nbt) {
        for (INetwork.TilePacketSync comp : getInterfaces(INetwork.TilePacketSync.class)) {
            comp.syncToNBT(nbt);
        }
    }

    @Override
    public void syncFromNBT(NBTTagCompound nbt) {
        for (INetwork.TilePacketSync comp : getInterfaces(INetwork.TilePacketSync.class)) {
            comp.syncFromNBT(nbt);
        }
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        for (INetwork.RecieveGuiNBT recieve : getInterfaces(INetwork.RecieveGuiNBT.class)) {
            recieve.recieveGuiNBT(side, player, name, nbt);
        }
    }

    @Override
    public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
        for (INetwork.SendGuiNBT recieve : getInterfaces(INetwork.SendGuiNBT.class)) {
            recieve.sendGuiNBT(nbts);
        }
    }
}
