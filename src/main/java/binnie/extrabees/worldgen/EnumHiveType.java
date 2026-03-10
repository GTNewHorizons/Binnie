package binnie.extrabees.worldgen;

import java.util.ArrayList;
import java.util.List;

import forestry.api.apiculture.IHiveDrop;

public enum EnumHiveType {

    WATER,
    ROCK,
    NETHER,
    MARBLE;

    /**
     * Cached values() array for frequent read-only operations, the array should NOT be mutated.
     */
    public static final EnumHiveType[] VALUES = values();
    public final List<IHiveDrop> drops;

    EnumHiveType() {
        drops = new ArrayList<>();
    }
}
