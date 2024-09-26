package binnie.botany.genetics;

import forestry.api.genetics.IAlleleInteger;

public class AlleleColor implements IAlleleInteger {

    private final String uid;
    private final int value;
    private final EnumFlowerColor color;

    public AlleleColor(EnumFlowerColor color, String uid, int value) {
        this.color = color;
        this.uid = uid;
        this.value = value;
    }

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public String getName() {
        return color.getName();
    }

    @Override
    public int getValue() {
        return value;
    }

    public EnumFlowerColor getColor() {
        return color;
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }
}
