package binnie.core.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import binnie.Binnie;
import binnie.core.machines.base.TileEntityMachineBase;
import binnie.core.machines.component.IInteraction;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.PacketPayload;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMachine extends TileEntityMachineBase implements INetworkedEntity, IGuiHolder<PosGuiData> {

    private Machine machine;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (machine != null) {
            machine.onUpdate();
        }
    }

    public TileEntityMachine(MachinePackage pack) {
        setMachine(pack);
    }

    public TileEntityMachine() {
        // ignored
    }

    public void setMachine(MachinePackage pack) {
        if (pack != null) {
            machine = new Machine(pack, this);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        String name = nbtTagCompound.getString("name");
        String group = nbtTagCompound.getString("group");
        MachinePackage pack = Binnie.Machine.getPackage(group, name);
        if (pack == null) {
            invalidate();
            return;
        }

        setMachine(pack);
        getMachine().readFromNBT(nbtTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        String name = machine.getPackage().getUID();
        String group = machine.getPackage().getGroup().getUID();
        nbtTagCompound.setString("group", group);
        nbtTagCompound.setString("name", name);
        getMachine().writeToNBT(nbtTagCompound);
    }

    @Override
    public void writeToPacket(PacketPayload payload) {
        machine.writeToPacket(payload);
    }

    @Override
    public void readFromPacket(PacketPayload payload) {
        machine.readFromPacket(payload);
    }

    public Machine getMachine() {
        return machine;
    }

    public void onBlockDestroy() {
        if (getMachine() != null) {
            getMachine().onBlockDestroy();
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        if (getMachine() == null) return null;
        NBTTagCompound nbt = new NBTTagCompound();
        getMachine().syncToNBT(nbt);
        if (nbt.hasNoTags()) return null;
        binnie.core.network.packet.MessageTileNBT tileNbt = new binnie.core.network.packet.MessageTileNBT(
                binnie.core.network.BinnieCorePacketID.TileDescriptionSync.ordinal(),
                this,
                nbt);
        return binnie.core.BinnieCore.instance.getNetworkWrapper().getPacketFrom(tileNbt.getMessage());
    }

    @Override
    public void invalidate() {
        super.invalidate();
        for (IInteraction.Invalidation c : getMachine().getInterfaces(IInteraction.Invalidation.class)) {
            c.onInvalidation();
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        for (IInteraction.ChunkUnload c : getMachine().getInterfaces(IInteraction.ChunkUnload.class)) {
            c.onChunkUnload();
        }
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        if (machine == null) return ModularPanel.defaultPanel("empty");
        IMui2MachineGuiProvider provider = machine.getInterface(IMui2MachineGuiProvider.class);
        if (provider != null) {
            return provider.buildMui2Panel(data, syncManager);
        }
        return ModularPanel.defaultPanel("empty");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModularScreen createScreen(PosGuiData data, ModularPanel mainPanel) {
        String modId = "binnie";
        if (machine != null && machine.getPackage().getGroup() != null) {
            modId = machine.getPackage().getGroup().getMod().getModID();
        }
        ModularScreen screen = new ModularScreen(modId, mainPanel);
        if (Loader.isModLoaded("gregtech")) {
            screen.useTheme("gregtech:standard");
        }
        return screen;
    }
}
