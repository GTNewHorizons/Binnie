package binnie.extratrees.config;

import static binnie.extratrees.ExtraTrees.MODID;
import static binnie.extratrees.ExtraTrees.MOD_NAME;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class ETConfigGUI extends SimpleGuiConfig {
    public ETConfigGUI(GuiScreen parent) throws ConfigException {
        super(parent, ETConfig.class, MODID, MOD_NAME);
    }
}
