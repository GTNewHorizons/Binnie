package binnie.extrabees;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.Tags;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.config.EBConfig;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ModuleCore;
import binnie.extrabees.genetics.ModuleGenetics;
import binnie.extrabees.liquids.ModuleLiquids;
import binnie.extrabees.products.CombItems;
import binnie.extrabees.products.ModuleProducts;
import binnie.extrabees.proxy.ExtraBeesProxy;
import binnie.extrabees.worldgen.ModuleGeneration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
        modid = ExtraBees.EB_MODID,
        name = ExtraBees.EB_MOD_NAME,
        version = Tags.VERSION,
        useMetadata = true,
        dependencies = "after:BinnieCore",
        guiFactory = "binnie.extrabees.config.EBConfigGUIFactory")
public class ExtraBees extends AbstractMod {

    public static final String EB_MODID = "extrabees";
    public static final String EB_MOD_NAME = "Extra Bees";

    @Mod.Instance("ExtraBees")
    public static ExtraBees instance;

    @SidedProxy(
            clientSide = "binnie.extrabees.proxy.ExtraBeesProxyClient",
            serverSide = "binnie.extrabees.proxy.ExtraBeesProxyServer")
    public static ExtraBeesProxy proxy;

    static {
        try {
            ConfigurationManager.registerConfig(EBConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }

    public static Block hive;
    public static Material materialBeehive;
    public static Block ectoplasm;

    public ExtraBees() {
        ExtraBees.instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addModule(new ModuleCore());
        addModule(new ModuleProducts());
        addModule(new ModuleGenetics());
        addModule(new ModuleGeneration());
        addModule(new ModuleLiquids());
        addModule(new ModuleApiary());
        preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDictionary.registerOre("ingotIron", Items.iron_ingot);
        OreDictionary.registerOre("ingotGold", Items.gold_ingot);
        OreDictionary.registerOre("gemDiamond", Items.diamond);
        OreDictionary.registerOre("gemEmerald", Items.emerald);

        CombItems.addSubtypes();

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

            if (name[0].equals("ExtraBees")) {
                if (m.type == GameRegistry.Type.BLOCK) m.remap(GameRegistry.findBlock(EB_MODID, name[1]));
                else m.remap(GameRegistry.findItem(EB_MODID, name[1]));
            }
        }
    }

    @Override
    public IBinnieGUID[] getGUIDs() {
        return ExtraBeeGUID.values();
    }

    @Override
    public IProxyCore getProxy() {
        return ExtraBees.proxy;
    }

    @Override
    public String getChannel() {
        return "EB";
    }

    @Override
    public String getModID() {
        return EB_MODID;
    }

    @Override
    protected Class<? extends BinniePacketHandler> getPacketHandler() {
        return PacketHandler.class;
    }

    @Override
    public boolean isActive() {
        return BinnieCore.isExtraBeesActive();
    }

    public static class PacketHandler extends BinniePacketHandler {

        public PacketHandler() {
            super(ExtraBees.instance);
        }
    }
}
