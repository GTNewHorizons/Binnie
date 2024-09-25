package binnie.extratrees;

import static binnie.extratrees.ExtraTrees.MODID;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = MODID)
public class ExtraTreesConfig {

    @Config.Comment("Uses reflection to convert the Forestry lemon tree to the Citrus family.")
    @Config.DefaultBoolean(true)
    public static boolean alterLemon;
}
