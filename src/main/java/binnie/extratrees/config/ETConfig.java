package binnie.extratrees.config;

import static binnie.extratrees.ExtraTrees.ET_MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = ET_MODID)
public class ETConfig {

    @Config.Comment("Uses reflection to convert the Forestry lemon tree to the Citrus family.")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean alterLemon;
}
