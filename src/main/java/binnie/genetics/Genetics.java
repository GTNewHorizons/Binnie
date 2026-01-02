package binnie.genetics;

import binnie.core.AbstractMod;
import binnie.core.Tags;
import binnie.core.gui.IBinnieGUID;
import binnie.core.item.ItemMisc;
import binnie.core.machines.MachineGroup;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsPacket;
import binnie.genetics.item.ItemAnalyst;
import binnie.genetics.item.ItemDatabase;
import binnie.genetics.item.ItemMasterRegistry;
import binnie.genetics.item.ItemRegistry;
import binnie.genetics.item.ItemSequence;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;
import binnie.genetics.item.ModuleItem;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.nei.IMCForNEI;
import binnie.genetics.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = "Genetics",
        name = "Genetics",
        version = Tags.VERSION,
        useMetadata = true,
        dependencies = "after:BinnieCore")
public class Genetics extends AbstractMod {

    @Mod.Instance("Genetics")
    public static Genetics instance;

    @SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
    public static Proxy proxy;

    public static final String CHANNEL = "GEN";

    public static ItemSerumArray itemSerumArray = null;
    public static ItemMisc itemGenetics;
    public static ItemSerum itemSerum;
    public static ItemSequence itemSequencer;
    public static ItemDatabase database;
    public static ItemAnalyst analyst;
    public static ItemRegistry registry;
    public static ItemMasterRegistry masterRegistry;

    public static MachineGroup packageGenetic;
    public static MachineGroup packageAdvGenetic;
    public static MachineGroup packageLabMachine;

    public Genetics() {
        Genetics.instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addModule(new ModuleItem());
        addModule(new ModuleMachine());
        IMCForNEI.IMCSender();
        preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        postInit();
    }

    @Override
    public IBinnieGUID[] getGUIDs() {
        return GeneticsGUI.values();
    }

    @Override
    public IPacketID[] getPacketIDs() {
        return GeneticsPacket.values();
    }

    @Override
    public String getChannel() {
        return CHANNEL;
    }

    @Override
    public IProxyCore getProxy() {
        return Genetics.proxy;
    }

    @Override
    public String getModID() {
        return "genetics";
    }

    @Override
    protected Class<? extends BinniePacketHandler> getPacketHandler() {
        return PacketHandler.class;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public static class PacketHandler extends BinniePacketHandler {

        public PacketHandler() {
            super(Genetics.instance);
        }
    }
}
