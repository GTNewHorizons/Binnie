package binnie.core.config;

import com.gtnewhorizon.gtnhlib.config.SimpleGuiFactory;
import net.minecraft.client.gui.GuiScreen;

public class ModsConfigGUIFactory implements SimpleGuiFactory {
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ModsConfigGUI.class;
    }
}
