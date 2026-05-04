package binnie.genetics.config;

import static binnie.genetics.Genetics.GENETICS_MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = GENETICS_MODID)
public class GeneticsConfig {

    @Config.Comment("Percentage chance of Isolator consuming bee. 0 never consumes, 1 always does.")
    @Config.RangeFloat(min = 0f, max = 1f)
    @Config.DefaultFloat(0.05f)
    public static float isolatorConsumptionChance;
}
