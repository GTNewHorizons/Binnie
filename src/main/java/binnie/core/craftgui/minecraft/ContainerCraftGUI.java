package binnie.core.craftgui.minecraft;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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

    // Action type is one of the action constants below
    public final static String ACTION_TYPE = "type";

    // Action constants
    public final static String ACTION_BATCH = "batch";
    public final static String SLOT_REG = "slot-reg";
    public final static String TANK_CLICK = "tank-click";
    public final static String TANK_UPDATE = "tank-update-";
    public final static String POWER_UPDATE = "power-update";
    public final static String PROCESS_UPDATE = "process-update";
    public final static String ERROR_UPDATE = "error-update";
    public final static String MOUSE_OVER_SLOT = "mouse-over-slot";
    public final static String SHIFT_CLICK_INFO = "shift-click-info";

    // Error type is one of the error constants below
    public final static String ERROR_TYPE = "type";

    // Error constants
    public static final byte CANNOT_WORK = (byte) 0;
    public static final byte CANNOT_PROGRESS = (byte) 1;

    // Slot constants
    public static final String SLOT_TYPE = "t";
    public static final String SLOT_INDEX = "i";
    public static final String SLOT_NUMBER = "n";

    // Uncategorized constants
    public static final String ACTIONS = "actions";
    private static final String USERNAME = "username";
    private static final String SLOT_ID = "id";
    private static final String SLOT = "slot";
    private static final String SLOTS = "slots";
    private static final String TANK = "tank";
    private static final String ORIGIN = "origin";
    private static final String NAME = "name";

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

        // WARNING: The client side expects `ArrayList` and the server expects `ListMap`
        inventoryItemStacks = new ListMap<>();
        inventorySlots = new ListMap<>();

        IInventory entityInventory = window.getInventory();
        IMachine machine = Machine.getMachine(entityInventory);
        if (machine == null) return;

        GameProfile user = machine.getOwner();
        if (user == null) return;

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(USERNAME, user.getName());
        sendNBTToClient(USERNAME, nbt);
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
        final Slot slot = getSlot(index);
        if (slot != null) slot.putStack(stack);
    }

    @Override
    public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack) {
        for (int i = 0; i < par1ArrayOfItemStack.length; ++i) {
            final Slot slot = getSlot(i);
            if (slot != null) slot.putStack(par1ArrayOfItemStack[i]);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        WindowInventory windowInventory = window.getWindowInventory();
        InventoryPlayer playerInventory = player.inventory;

        for (int i = 0; i < windowInventory.getSizeInventory(); ++i) {
            if (!windowInventory.dispenseOnClose(i)) continue;

            final ItemStack stack = windowInventory.getStackInSlot(i);
            if (stack == null) continue;

            final ItemStack newStack = new TransferRequest(stack, playerInventory).transfer(true);
            if (newStack == null) continue;

            player.dropPlayerItemWithRandomChoice(newStack, false);
        }
    }

    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
        Slot slot = getSlot(slotNum);
        if (slot instanceof CustomSlot customSlot && customSlot.handleClick()) {
            customSlot.onSlotClick(this, mouseButton, modifier, player);
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
        IInventory entityInventory = window.getInventory();
        return entityInventory == null || entityInventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        shiftClick(player, slotID);
        return null;
    }

    private void shiftClick(EntityPlayer player, int index) {
        TransferRequest request = getShiftClickRequest(player, index);
        if (request == null) return;
        ItemStack stack = request.transfer(true);
        Slot shiftClickedSlot = inventorySlots.get(index);
        shiftClickedSlot.putStack(stack);
        shiftClickedSlot.onSlotChanged();
    }

    private TransferRequest getShiftClickRequest(EntityPlayer player, int index) {
        if (index < 0) return null;

        Slot shiftClickedSlot = inventorySlots.get(index);
        ItemStack itemstack = null;
        if (shiftClickedSlot.getHasStack()) {
            itemstack = shiftClickedSlot.getStack().copy();
        }

        IInventory playerInventory = player.inventory;
        IInventory entityInventory = window.getInventory();
        IInventory windowInventory = window.getWindowInventory();
        IInventory fromPlayer = (entityInventory == null) ? windowInventory : entityInventory;
        int[] target = new int[36];
        for (int i = 0; i < target.length; ++i) {
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
        IInventory entityInventory = window.getInventory();
        heldItem = new TransferRequest(heldItem, entityInventory).setOrigin(player.inventory).setTargetSlots(new int[0])
                .setTargetTanks(new int[] { slotID }).transfer(true);

        player.inventory.setItemStack(heldItem);

        if (player instanceof EntityPlayerMP entityPlayerMP) {
            entityPlayerMP.updateHeldItem();
        }

        return heldItem;
    }

    public void handleNBT(Side side, EntityPlayer player, NBTTagCompound action) {
        final String actionType = action.getString(ACTION_TYPE);

        if (side == Side.SERVER) {
            switch (actionType) {
                case TANK_CLICK -> tankClick(player, action.getByte(SLOT_ID));
                case SLOT_REG -> {
                    final int slotType = action.getByte(SLOT_TYPE);
                    final int slotIndex = action.getShort(SLOT_INDEX);
                    final int slotNumber = action.getShort(SLOT_NUMBER);

                    createSlot(InventoryType.values()[slotType % 4], slotIndex, slotNumber);

                    for (ICrafting crafterObject : crafters) {
                        crafterObject.sendContainerAndContentsToPlayer(this, getInventory());
                    }
                }
            }
        }

        switch (actionType) {
            case POWER_UPDATE -> onPowerUpdate(action);
            case PROCESS_UPDATE -> onProcessUpdate(action);
            case ERROR_UPDATE -> onErrorUpdate(action);
            case MOUSE_OVER_SLOT -> onMouseOverSlot(player, action);
            case SHIFT_CLICK_INFO -> onReceiveShiftClickHighlights(action);
            default -> {
                if (actionType.contains(TANK_UPDATE)) onTankUpdate(action);
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        IInventory entityInventory = window.getInventory();
        ITankMachine tanks = Machine.getInterface(ITankMachine.class, entityInventory);
        IPoweredMachine powered = Machine.getInterface(IPoweredMachine.class, entityInventory);
        IErrorStateSource error = Machine.getInterface(IErrorStateSource.class, entityInventory);
        IProcess process = Machine.getInterface(IProcess.class, entityInventory);

        if (window.isServer() && tanks != null) {
            final TankInfo[] tankInfos = tanks.getTankInfos();
            for (int i = 0; i < tankInfos.length; ++i) {
                final TankInfo tank = tankInfos[i];
                if (this.getTankInfo(i).equals(tank)) continue;
                syncedNBT.put(TANK_UPDATE + i, createTankNBT(i, tank));
                syncedTanks.put(i, tank);
            }
        }

        if (window.isServer()) {
            if (powered != null) syncedNBT.put(POWER_UPDATE, createPowerNBT(powered.getPowerInfo()));
            if (process != null) syncedNBT.put(PROCESS_UPDATE, createProcessNBT(process.getInfo()));
            if (error != null) syncedNBT.put(ERROR_UPDATE, createErrorNBT(error));
        }

        INetwork.SendGuiNBT machineSync = Machine.getInterface(INetwork.SendGuiNBT.class, entityInventory);

        if (machineSync != null) machineSync.sendGuiNBT(syncedNBT);

        Map<String, NBTTagCompound> sentThisTime = new HashMap<>();
        NBTTagList actions = new NBTTagList();

        for (Map.Entry<String, NBTTagCompound> entry : syncedNBT.entrySet()) {
            final String actionType = entry.getKey();
            final NBTTagCompound action = entry.getValue();
            final NBTTagCompound actionPrev = sentNBT.get(actionType);

            action.setString(ACTION_TYPE, actionType);

            if (action.equals(actionPrev)) continue;

            actions.appendTag(action);
            sentThisTime.put(actionType, action);
        }

        NBTTagCompound actionsBatch = new NBTTagCompound();
        actionsBatch.setString(ACTION_TYPE, ACTION_BATCH);
        actionsBatch.setTag(ACTIONS, actions);

        for (ICrafting crafter : crafters) {
            if (crafter instanceof EntityPlayerMP playerMP) {
                final MessageContainerUpdate packet = new MessageContainerUpdate(actionsBatch);
                BinnieCore.proxy.sendToPlayer(packet, playerMP);
            }
        }

        sentNBT.putAll(sentThisTime);
        syncedNBT.clear();
    }

    private NBTTagCompound createErrorNBT(IErrorStateSource error) {
        NBTTagCompound nbt = new NBTTagCompound();
        ErrorState state = null;
        if (error.canWork() != null) {
            nbt.setByte(ERROR_TYPE, CANNOT_WORK);
            state = error.canWork();
        } else if (error.canProgress() != null) {
            nbt.setByte(ERROR_TYPE, CANNOT_PROGRESS);
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
        nbt.setByte(TANK, (byte) tank);
        return nbt;
    }

    public void onTankUpdate(NBTTagCompound nbt) {
        int tankID = nbt.getByte(TANK);
        TankInfo tank = new TankInfo();
        tank.readFromNBT(nbt);
        syncedTanks.put(tankID, tank);
    }

    public void onProcessUpdate(NBTTagCompound nbt) {
        syncedProcess = new ProcessInfo();
        syncedProcess.readFromNBT(nbt);
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
        errorType = nbt.getByte(ERROR_TYPE);
        if (nbt.hasKey(NAME)) {
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
        nbt.setShort(SLOT, (short) slot.slotNumber);
        window.sendClientAction(MOUSE_OVER_SLOT, nbt);
    }

    private void onMouseOverSlot(EntityPlayer player, NBTTagCompound data) {
        int slotNumber = data.getShort(SLOT);
        TransferRequest request = getShiftClickRequest(player, slotNumber);
        if (request == null) {
            return;
        }

        request.transfer(false);
        NBTTagCompound nbt = new NBTTagCompound();
        List<Integer> slots = new ArrayList<>();
        for (TransferRequest.TransferSlot transferSlot : request.getInsertedSlots()) {
            Slot slot = getSlot(transferSlot.inventory, transferSlot.id);
            if (slot != null) {
                slots.add(slot.slotNumber);
            }
        }

        int[] array = new int[slots.size()];
        for (int i = 0; i < slots.size(); ++i) {
            array[i] = slots.get(i);
        }

        nbt.setIntArray(SLOTS, array);
        nbt.setShort(ORIGIN, (short) slotNumber);
        syncedNBT.put(SHIFT_CLICK_INFO, nbt);
    }

    private void onReceiveShiftClickHighlights(NBTTagCompound data) {
        ControlSlot.highlighting.get(EnumHighlighting.SHIFT_CLICK).clear();
        for (int slotIndex : data.getIntArray(SLOTS)) {
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
        String actionType = action.getString(ACTION_TYPE);
        if (actionType.equals(ACTION_BATCH)) {
            NBTTagList actionsBatch = action.getTagList(ACTIONS, TAG_COMPOUND);
            for (int i = 0; i < actionsBatch.tagCount(); i++) {
                receiveNBT(side, player, actionsBatch.getCompoundTagAt(i));
            }
            return;
        }
        handleNBT(side, player, action);
        window.receiveGuiNBT(getSide(), player, actionType, action);
        INetwork.ReceiveGuiNBT machine = Machine.getInterface(INetwork.ReceiveGuiNBT.class, window.getInventory());
        if (machine == null) return;
        machine.receiveGuiNBT(getSide(), player, actionType, action);
    }

    public Slot getOrCreateSlot(InventoryType inventoryType, int slotIndex) {
        final IInventory inventory = getInventory(inventoryType);
        Slot slot = getSlot(inventory, slotIndex);

        if (slot == null) {
            slot = new CustomSlot(inventory, slotIndex);
            addSlotToContainer(slot);
        }

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
