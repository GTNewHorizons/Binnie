package binnie.extrabees.config;

import static binnie.extrabees.ExtraBees.EB_MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = EB_MODID)
public class EBConfig {

    @Config.DefaultBoolean(true)
    @Config.RequiresMcRestart
    public static boolean canQuarryMineHives;

    @Config.DefaultInt(1)
    public static int waterHiveRate;

    @Config.DefaultInt(2)
    public static int rockHiveRate;

    @Config.DefaultInt(2)
    public static int netherHiveRate;

    @Config.Comment("Marble hives don't appear to be implemented in code, so this does nothing.")
    @Config.DefaultInt(2)
    public static int marbleHiveRate;
}
