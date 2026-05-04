package binnie.core.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import binnie.core.BinnieCore;

@Config(modid = BinnieCore.CORE_MODID)
public class BCConfig {

    @Config.Comment("If true, disables all compartment storage machines (no blocks/recipes will be registered).")
    @Config.DefaultBoolean(false)
    @Config.RequiresMcRestart
    public static boolean disableCompartments;
}
