package binnie.extratrees;

import static binnie.extratrees.ExtraTrees.EB_MOD_NAME;
import static binnie.extratrees.ExtraTrees.ET_MODID;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.Tags;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.block.decor.BlockHedge;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.carpentry.BlockCarpentry;
import binnie.extratrees.carpentry.BlockStainedDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.config.ETConfig;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.core.ModuleCore;
import binnie.extratrees.genetics.ModuleGenetics;
import binnie.extratrees.item.ModuleItems;
import binnie.extratrees.machines.ModuleMachine;
import binnie.extratrees.nei.IMCForNEI;
import binnie.extratrees.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = ET_MODID,
        name = EB_MOD_NAME,
        version = Tags.VERSION,
        useMetadata = true,
        dependencies = "after:BinnieCore")
public class ExtraTrees extends AbstractMod {

    public static final String ET_MODID = "ExtraTrees";
    public static final String EB_MOD_NAME = "Extra Trees";

    @Mod.Instance("ExtraTrees")
    public static ExtraTrees instance;

    @SidedProxy(clientSide = "binnie.extratrees.proxy.ProxyClient", serverSide = "binnie.extratrees.proxy.ProxyServer")
    public static Proxy proxy;

    static {
        try {
            ConfigurationManager.registerConfig(ETConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item itemDictionary;
    public static Item itemDictionaryLepi;
    public static Item itemMisc;
    public static Item itemFood;
    public static Block blockStairs;
    public static Block blockLog;
    public static BlockCarpentry blockCarpentry;
    public static Block blockPlanks;
    public static Block blockShrubLeaves;
    public static Block blockMachine;
    public static Block blockFence;
    public static BlockCarpentry blockPanel;
    public static Block blockSlab;
    public static Block blockDoubleSlab;
    public static Item itemHammer;
    public static Item itemDurableHammer;
    public static Block blockGate;
    public static Block blockDoor;
    public static BlockMultiFence blockMultiFence;
    public static BlockHedge blockHedge;
    public static BlockStainedDesign blockStained;
    public static int doorRenderId;
    public static int branchRenderId;
    public static int fenceID;
    public static int stairsID;

    public ExtraTrees() {
        ExtraTrees.instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addModule(new ModuleBlocks());
        addModule(new ModuleItems());
        addModule(new ModuleGenetics());
        addModule(new ModuleCarpentry());
        addModule(new ModuleMachine());
        addModule(new ModuleCore());
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
        return ExtraTreesGUID.values();
    }

    @Override
    public String getChannel() {
        return "ET";
    }

    @Override
    public IProxyCore getProxy() {
        return ExtraTrees.proxy;
    }

    // Concerningly, this isn't the same as the MODID
    @Override
    public String getModID() {
        return "extratrees";
    }

    @Override
    protected Class<? extends BinniePacketHandler> getPacketHandler() {
        return PacketHandler.class;
    }

    @Override
    public boolean isActive() {
        return BinnieCore.isExtraTreesActive();
    }

    public static class PacketHandler extends BinniePacketHandler {

        public PacketHandler() {
            super(ExtraBees.instance);
        }
    }
}
