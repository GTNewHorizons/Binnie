package binnie.extratrees.config;

import static binnie.extratrees.ExtraTrees.EB_MOD_NAME;
import static binnie.extratrees.ExtraTrees.ET_MODID;

import net.minecraft.client.gui.GuiScreen;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;

public class ETConfigGUI extends SimpleGuiConfig {

    public ETConfigGUI(GuiScreen parent) throws ConfigException {
        super(parent, ETConfig.class, ET_MODID, EB_MOD_NAME);
    }
}
