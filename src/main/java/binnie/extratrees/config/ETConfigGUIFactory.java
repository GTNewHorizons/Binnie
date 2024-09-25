package binnie.extratrees.config;

import com.gtnewhorizon.gtnhlib.config.SimpleGuiFactory;
import net.minecraft.client.gui.GuiScreen;

public class ETConfigGUIFactory implements SimpleGuiFactory {

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ETConfigGUI.class;
    }
}
