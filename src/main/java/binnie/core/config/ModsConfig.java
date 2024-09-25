package binnie.core.config;

import static binnie.core.BinnieCore.CORE_MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = CORE_MODID)
public class ModsConfig {

    @Config.Comment("Enables the Extra Bees Mod.")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean extraBees;

    @Config.Comment("Enables the Extra Trees Mod.")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean extraTrees;

    @Config.Comment("Enables the Botany Mod.")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean botany;

    @Config.Comment("Enables the Genetics Mod.")
    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean genetics;
}
