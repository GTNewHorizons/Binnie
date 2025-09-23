package binnie.core.craftgui.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.mojang.authlib.GameProfile;

import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.EnumHighlighting;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IErrorStateSource;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.ProcessInfo;
import binnie.core.machines.power.TankInfo;
import binnie.core.machines.transfer.TransferRequest;
import binnie.core.network.packet.MessageContainerUpdate;
import cpw.mods.fml.relauncher.Side;

public class ContainerCraftGUI extends Container {

    private final Window window;
    private final Map<String, NBTTagCompound> syncedNBT = new HashMap<>();
    private final Map<String, NBTTagCompound> sentNBT = new HashMap<>();
    private final Map<Integer, TankInfo> syncedTanks = new HashMap<>();
    private PowerInfo syncedPower = new PowerInfo();
    private ProcessInfo syncedProcess = new ProcessInfo();
    private int errorType = 0;
    private ErrorState error = null;
    private int mousedOverSlotNumber = -1;

    public ContainerCraftGUI(Window window) {
        this.window = window;

        if (!window.isServer()) return;

        IMachine machine = Machine.getMachine(window.getInventory());
        if (machine == null) return;

        GameProfile user = machine.getOwner();
        if (user == null) return;

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("username", user.getName());
        sendNBTToClient("username", nbt);
    }

    @Override
    protected Slot addSlotToContainer(Slot slot) {
        return super.addSlotToContainer(slot);
    }

    private Side getSide() {
        return window.isServer() ? Side.SERVER : Side.CLIENT;
    }

    @Override
    public Slot getSlot(int index) {
        return index < 0 || index >= inventorySlots.size() ? null : inventorySlots.get(index);
    }

    @Override
    public void putStackInSlot(int index, ItemStack stack) {
        if (getSlot(index) != null) {
            getSlot(index).putStack(stack);
        }
    }

    @Override
    public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack) {
        for (int i = 0; i < par1ArrayOfItemStack.length; ++i) {
            if (getSlot(i) != null) {
                getSlot(i).putStack(par1ArrayOfItemStack[i]);
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        WindowInventory inventory = window.getWindowInventory();

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (!inventory.dispenseOnClose(i)) continue;

            final ItemStack stack = inventory.getStackInSlot(i);
            if (stack == null) continue;

            final ItemStack newStack = new TransferRequest(stack, player.inventory).transfer(true);
            if (newStack == null) continue;

            player.dropPlayerItemWithRandomChoice(newStack, false);
        }
    }

    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
        Slot slot = getSlot(slotNum);
        if (slot instanceof CustomSlot && ((CustomSlot) slot).handleClick()) {
            ((CustomSlot) slot).onSlotClick(this, mouseButton, modifier, player);
            return player.inventory.getItemStack();
        }
        return super.slotClick(slotNum, mouseButton, modifier, player);
    }

    public void sendNBTToClient(String key, NBTTagCompound nbt) {
        syncedNBT.put(key, nbt);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (player instanceof EntityPlayerMP playerMP) {
            if (!crafters.contains(playerMP)) {
                crafters.add(playerMP);
            }
            sentNBT.clear();
        }
        IInventory inventory = window.getInventory();
        return inventory == null || inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        return shiftClick(player, slotID);
    }

    private ItemStack shiftClick(EntityPlayer player, int index) {
        TransferRequest request = getShiftClickRequest(player, index);
        if (request == null) {
            return null;
        }

        ItemStack stack = request.transfer(true);
        Slot shiftClickedSlot = inventorySlots.get(index);
        shiftClickedSlot.putStack(stack);
        shiftClickedSlot.onSlotChanged();
        return null;
    }

    private TransferRequest getShiftClickRequest(EntityPlayer player, int index) {
        if (index < 0) return null;

        Slot shiftClickedSlot = inventorySlots.get(index);
        ItemStack itemstack = null;
        if (shiftClickedSlot.getHasStack()) {
            itemstack = shiftClickedSlot.getStack().copy();
        }

        IInventory playerInventory = player.inventory;
        IInventory containerInventory = window.getInventory();
        IInventory windowInventory = window.getWindowInventory();
        IInventory fromPlayer = (containerInventory == null) ? windowInventory : containerInventory;
        int[] target = new int[36];
        for (int i = 0; i < 36; ++i) {
            target[i] = i;
        }

        TransferRequest request;
        if (shiftClickedSlot.inventory == playerInventory) {
            request = new TransferRequest(itemstack, fromPlayer).setOrigin(shiftClickedSlot.inventory);
        } else {
            request = new TransferRequest(itemstack, playerInventory).setOrigin(shiftClickedSlot.inventory)
                    .setTargetSlots(target);
        }

        if (window instanceof IWindowAffectsShiftClick iWindowAffectsShiftClick) {
            iWindowAffectsShiftClick.alterRequest(request);
        }
        return request;
    }

    @SuppressWarnings("UnusedReturnValue")
    public ItemStack tankClick(EntityPlayer player, int slotID) {
        if (player.inventory.getItemStack() == null) return null;

        ItemStack heldItem = player.inventory.getItemStack().copy();
        IInventory windowInventory = window.getInventory();
        heldItem = new TransferRequest(heldItem, windowInventory).setOrigin(player.inventory).setTargetSlots(new int[0])
                .setTargetTanks(new int[] { slotID }).transfer(true);

        player.inventory.setItemStack(heldItem);

        if (player instanceof EntityPlayerMP entityPlayerMP) {
            entityPlayerMP.updateHeldItem();
        }

        return heldItem;
    }

    public void handleNBT(Side side, EntityPlayer player, String name, NBTTagCompound action) {
        switch (side) {
            case SERVER -> {
                switch (name) {
                    case "tank-click" -> tankClick(player, action.getByte("id"));
                    case "slot-reg" -> {
                        int type = action.getByte("t");
                        int index = action.getShort("i");
                        int slotNumber = action.getShort("n");
                        createSlot(InventoryType.values()[type % 4], index, slotNumber);

                        for (ICrafting crafterObject : crafters) {
                            crafterObject.sendContainerAndContentsToPlayer(this, getInventory());
                        }
                    }
                }
            }
            case CLIENT -> {
                switch (name) {
                    case "power-update" -> onPowerUpdate(action);
                    case "process-update" -> onProcessUpdate(action);
                    case "error-update" -> onErrorUpdate(action);
                    case "mouse-over-slot" -> onMouseOverSlot(player, action);
                    case "shift-click-info" -> onReceiveShiftClickHighlights(player, action);
                    default -> {
                        if (name.contains("tank-update")) onTankUpdate(action);
                    }
                }
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        IInventory inventory = window.getInventory();
        ITankMachine tanks = Machine.getInterface(ITankMachine.class, inventory);
        IPoweredMachine powered = Machine.getInterface(IPoweredMachine.class, inventory);
        IErrorStateSource error = Machine.getInterface(IErrorStateSource.class, inventory);
        IProcess process = Machine.getInterface(IProcess.class, inventory);

        if (window.isServer() && tanks != null) {
            final TankInfo[] tankInfos = tanks.getTankInfos();
            for (int i = 0; i < tankInfos.length; ++i) {
                final TankInfo tank = tankInfos[i];
                if (this.getTankInfo(i).equals(tank)) continue;
                syncedNBT.put("tank-update-" + i, createTankNBT(i, tank));
                syncedTanks.put(i, tank);
            }
        }

        if (window.isServer()) {
            if (powered != null) syncedNBT.put("power-update", createPowerNBT(powered.getPowerInfo()));
            if (process != null) syncedNBT.put("process-update", createProcessNBT(process.getInfo()));
            if (error != null) syncedNBT.put("error-update", createErrorNBT(error));
        }

        INetwork.SendGuiNBT machineSync = Machine.getInterface(INetwork.SendGuiNBT.class, inventory);

        if (machineSync != null) machineSync.sendGuiNBT(syncedNBT);

        Map<String, NBTTagCompound> sentThisTime = new HashMap<>();
        for (Map.Entry<String, NBTTagCompound> nbt : syncedNBT.entrySet()) {
            final NBTTagCompound nbtValue = nbt.getValue();
            final String nbtKey = nbt.getKey();
            final NBTTagCompound nbtValuePrev = sentNBT.get(nbtKey);

            nbtValue.setString("type", nbtKey);

            if (nbtValue.equals(nbtValuePrev)) continue;

            for (Object crafter : crafters) {
                if (crafter instanceof EntityPlayerMP playerMP) {
                    final MessageContainerUpdate packet = new MessageContainerUpdate(nbtValue);
                    BinnieCore.proxy.sendToPlayer(packet, playerMP);
                }
            }

            sentThisTime.put(nbtKey, nbtValue);
        }

        sentNBT.putAll(sentThisTime);
        syncedNBT.clear();
    }

    private NBTTagCompound createErrorNBT(IErrorStateSource error) {
        NBTTagCompound nbt = new NBTTagCompound();
        ErrorState state = null;
        if (error.canWork() != null) {
            nbt.setByte("type", (byte) 0);
            state = error.canWork();
        } else if (error.canProgress() != null) {
            nbt.setByte("type", (byte) 1);
            state = error.canProgress();
        }

        if (state != null) {
            state.writeToNBT(nbt);
        }
        return nbt;
    }

    public NBTTagCompound createPowerNBT(PowerInfo powerInfo) {
        NBTTagCompound nbt = new NBTTagCompound();
        powerInfo.writeToNBT(nbt);
        return nbt;
    }

    public NBTTagCompound createProcessNBT(ProcessInfo powerInfo) {
        NBTTagCompound nbt = new NBTTagCompound();
        powerInfo.writeToNBT(nbt);
        return nbt;
    }

    public NBTTagCompound createTankNBT(int tank, TankInfo tankInfo) {
        NBTTagCompound nbt = new NBTTagCompound();
        tankInfo.writeToNBT(nbt);
        nbt.setByte("tank", (byte) tank);
        return nbt;
    }

    public void onTankUpdate(NBTTagCompound nbt) {
        int tankID = nbt.getByte("tank");
        TankInfo tank = new TankInfo();
        tank.readFromNBT(nbt);
        syncedTanks.put(tankID, tank);
    }

    public void onProcessUpdate(NBTTagCompound nbt) {
        (syncedProcess = new ProcessInfo()).readFromNBT(nbt);
    }

    public void onPowerUpdate(NBTTagCompound nbt) {
        syncedPower = new PowerInfo();
        syncedPower.readFromNBT(nbt);
    }

    public PowerInfo getPowerInfo() {
        return syncedPower;
    }

    public ProcessInfo getProcessInfo() {
        return syncedProcess;
    }

    public TankInfo getTankInfo(int tank) {
        return syncedTanks.containsKey(tank) ? syncedTanks.get(tank) : new TankInfo();
    }

    public void onErrorUpdate(NBTTagCompound nbt) {
        errorType = nbt.getByte("type");
        if (nbt.hasKey("name")) {
            error = new ErrorState("", "");
            error.readFromNBT(nbt);
        } else {
            error = null;
        }
    }

    public ErrorState getErrorState() {
        return error;
    }

    public int getErrorType() {
        return errorType;
    }

    public CustomSlot[] getCustomSlots() {
        List<CustomSlot> slots = new ArrayList<>();
        for (Object object : inventorySlots) {
            if (object instanceof CustomSlot) {
                slots.add((CustomSlot) object);
            }
        }
        return slots.toArray(new CustomSlot[0]);
    }

    public void setMouseOverSlot(Slot slot) {
        if (slot.slotNumber == mousedOverSlotNumber) return;

        mousedOverSlotNumber = slot.slotNumber;
        ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).clear();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("slot", (short) slot.slotNumber);
        window.sendClientAction("mouse-over-slot", nbt);
    }

    private void onMouseOverSlot(EntityPlayer player, NBTTagCompound data) {
        int slotnumber = data.getShort("slot");
        TransferRequest request = getShiftClickRequest(player, slotnumber);
        if (request == null) {
            return;
        }

        request.transfer(false);
        NBTTagCompound nbt = new NBTTagCompound();
        List<Integer> slots = new ArrayList<>();
        for (TransferRequest.TransferSlot tslot : request.getInsertedSlots()) {
            Slot slot = getSlot(tslot.inventory, tslot.id);
            if (slot != null) {
                slots.add(slot.slotNumber);
            }
        }

        int[] array = new int[slots.size()];
        for (int i = 0; i < slots.size(); ++i) {
            array[i] = slots.get(i);
        }

        nbt.setIntArray("slots", array);
        nbt.setShort("origin", (short) slotnumber);
        syncedNBT.put("shift-click-info", nbt);
    }

    private void onReceiveShiftClickHighlights(EntityPlayer player, NBTTagCompound data) {
        ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).clear();
        for (int slotIndex : data.getIntArray("slots")) {
            ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).add(slotIndex);
        }
    }

    private CustomSlot getSlot(IInventory inventory, int id) {
        for (Object o : inventorySlots) {
            CustomSlot slot = (CustomSlot) o;
            if (slot.inventory == inventory && slot.getSlotIndex() == id) {
                return slot;
            }
        }
        return null;
    }

    public void receiveNBT(Side side, EntityPlayer player, NBTTagCompound action) {
        String name = action.getString("type");
        handleNBT(side, player, name, action);
        window.receiveGuiNBT(getSide(), player, name, action);
        INetwork.ReceiveGuiNBT machine = Machine.getInterface(INetwork.ReceiveGuiNBT.class, window.getInventory());
        if (machine == null) return;
        machine.receiveGuiNBT(getSide(), player, name, action);
    }

    public Slot getOrCreateSlot(InventoryType type, int index) {
        IInventory inventory = getInventory(type);
        Slot slot = getSlot(inventory, index);
        if (slot == null) {
            slot = new CustomSlot(inventory, index);
            addSlotToContainer(slot);
        }

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("t", (byte) type.ordinal());
        nbt.setShort("i", (short) index);
        nbt.setShort("n", (short) slot.slotNumber);
        window.sendClientAction("slot-reg", nbt);
        return slot;
    }

    protected IInventory getInventory(InventoryType type) {
        return switch (type) {
            case Machine -> window.getInventory();
            case Player -> window.getPlayer().inventory;
            case Window -> window.getWindowInventory();
            default -> null;
        };
    }

    private void createSlot(InventoryType type, int index, int slotNumber) {
        if (inventorySlots.get(slotNumber) != null) return;

        IInventory inventory = getInventory(type);
        Slot slot = new CustomSlot(inventory, index);
        slot.slotNumber = slotNumber;
        inventorySlots.add(slotNumber, slot);
        inventoryItemStacks.add(slotNumber, null);
    }
}
