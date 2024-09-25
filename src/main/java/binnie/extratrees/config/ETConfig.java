package binnie.extratrees.config;

import static binnie.extratrees.ExtraTrees.MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = MODID)
public class ETConfig {

    @Config.Comment("Uses reflection to convert the Forestry lemon tree to the Citrus family.")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean alterLemon;
}
