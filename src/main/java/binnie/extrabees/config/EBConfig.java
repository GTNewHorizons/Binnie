package binnie.extrabees.config;

import static binnie.extrabees.ExtraBees.EB_MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = EB_MODID)
public class EBConfig {

    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean canQuarryMineHives;

    @Config.Comment("Attempt to generate this many hives per chunk.")
    @Config.RangeInt(min = 0)
    @Config.DefaultInt(1)
    public static int waterHiveRate;

    @Config.Comment("Attempt to generate this many hives per chunk.")
    @Config.RangeInt(min = 0)
    @Config.DefaultInt(2)
    public static int rockHiveRate;

    @Config.Comment("Attempt to generate this many hives per chunk.")
    @Config.RangeInt(min = 0)
    @Config.DefaultInt(2)
    public static int netherHiveRate;
}
