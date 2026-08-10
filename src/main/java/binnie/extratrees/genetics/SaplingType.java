package binnie.extratrees.genetics;

import net.minecraft.util.IIcon;

public enum SaplingType {

    DEFAULT,
    JUNGLE,
    CONIFER,
    FRUIT,
    POPLAR,
    PALM,
    SHRUB;

    public static final SaplingType[] VALUES = values();

    public IIcon[] icon;
}
