package binnie.extrabees.worldgen;

import java.util.ArrayList;
import java.util.List;

import forestry.api.apiculture.IHiveDrop;

public enum EnumHiveType {

    WATER,
    ROCK,
    NETHER,
    MARBLE;

    public List<IHiveDrop> drops;

    EnumHiveType() {
        drops = new ArrayList<>();
    }
}
