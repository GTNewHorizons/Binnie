package binnie.extratrees;

import static binnie.extratrees.ExtraTrees.EB_MOD_NAME;
import static binnie.extratrees.ExtraTrees.ET_MODID;

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
import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

@Mod(
        modid = ET_MODID,
        name = EB_MOD_NAME,
        version = Tags.VERSION,
        useMetadata = true,
        dependencies = "after:BinnieCore",
        guiFactory = "binnie.extratrees.config.ETConfigGUIFactory")
public class ExtraTrees extends AbstractMod {

    public static final String ET_MODID = "extratrees";
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

    @Mod.EventHandler
    public void missingMappings(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping m : event.getAll()) {
            final String[] name = m.name.split(":");
            if (name.length < 2) continue;

            if (name[0].equals("ExtraTrees")) {
                if (m.type == GameRegistry.Type.BLOCK) m.remap(GameRegistry.findBlock(ET_MODID, name[1]));
                else m.remap(GameRegistry.findItem(ET_MODID, name[1]));
            }
        }
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

    @Override
    public String getModID() {
        return ET_MODID;
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
