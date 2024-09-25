package binnie.extrabees.config;

import static binnie.extrabees.ExtraBees.EB_MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = EB_MODID)
public class EBConfigMachines {

    @Config.Comment("Percentage chance of Isolator consuming bee. 0 never consumes, 1 always does.")
    @Config.RangeFloat(min = 0f, max = 1f)
    @Config.DefaultFloat(0.05f)
    public static float isolatorConsumptionChance;
}
