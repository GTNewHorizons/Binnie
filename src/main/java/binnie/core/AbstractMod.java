package binnie.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import binnie.core.gui.IBinnieGUID;
import binnie.core.mod.parser.FieldParser;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.network.IPacketProvider;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.proxy.IProxyCore;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public abstract class AbstractMod implements IPacketProvider, IInitializable {

    private SimpleNetworkWrapper wrapper;
    private final LinkedHashSet<Field> fields;
    protected final List<IInitializable> modules;

    public AbstractMod() {
        fields = new LinkedHashSet<>();
        modules = new ArrayList<>();
        BinnieCore.registerMod(this);
    }

    public boolean isActive() {
        return true;
    }

    @Override
    public abstract String getChannel();

    @Override
    public IPacketID[] getPacketIDs() {
        return new IPacketID[0];
    }

    public IBinnieGUID[] getGUIDs() {
        return new IBinnieGUID[0];
    }

    public abstract IProxyCore getProxy();

    public abstract String getModID();

    public SimpleNetworkWrapper getNetworkWrapper() {
        return wrapper;
    }

    protected abstract Class<? extends BinniePacketHandler> getPacketHandler();

    @Override
    public void preInit() {
        if (!isActive()) {
            return;
        }

        getProxy().preInit();
        for (IInitializable module : modules) {
            module.preInit();
        }

        // TODO what does it mean?
        Collections.addAll(fields, getClass().getFields());
        for (Class<?> cls : getClass().getClasses()) {
            Collections.addAll(fields, getClass().getFields());
        }
        for (IInitializable module : modules) {
            Collections.addAll(fields, module.getClass().getFields());
        }
        for (Field field4 : fields) {
            try {
                FieldParser.preInitParse(field4, this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void init() {
        if (!isActive()) {
            return;
        }
        getProxy().init();

        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(getChannel());
        wrapper.registerMessage(getPacketHandler(), MessageBinnie.class, 1, Side.CLIENT);
        wrapper.registerMessage(getPacketHandler(), MessageBinnie.class, 1, Side.SERVER);
        for (IInitializable module : modules) {
            module.init();
        }

        for (Field field : fields) {
            try {
                FieldParser.initParse(field, this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void postInit() {
        if (!isActive()) {
            return;
        }

        getProxy().postInit();
        for (IInitializable module : modules) {
            module.postInit();
        }

        for (Field field : fields) {
            try {
                FieldParser.postInitParse(field, this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void addModule(IInitializable init) {
        modules.add(init);
        init.registerEventHandler();
    }
}
