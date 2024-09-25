package binnie.core.config;

import static binnie.core.BinnieCore.CORE_MODID;
import static binnie.core.BinnieCore.CORE_MOD_NAME;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class ModsConfigGUI extends SimpleGuiConfig {
    public ModsConfigGUI(GuiScreen parent) throws ConfigException {
        super(parent, ModsConfig.class, CORE_MODID, CORE_MOD_NAME);
    }
}
