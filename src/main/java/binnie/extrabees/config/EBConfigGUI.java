package binnie.extrabees.config;

import static binnie.extrabees.ExtraBees.EB_MODID;
import static binnie.extrabees.ExtraBees.EB_MOD_NAME;

import net.minecraft.client.gui.GuiScreen;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;

public class EBConfigGUI extends SimpleGuiConfig {

    public EBConfigGUI(GuiScreen parent) throws ConfigException {
        super(parent, EB_MODID, EB_MOD_NAME, false, EBConfig.class, EBConfigMachines.class);
    }
}
