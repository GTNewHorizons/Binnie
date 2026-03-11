package binnie.botany.api;

public enum EnumSoilType {

    SOIL,
    LOAM,
    FLOWERBED;

    /**
     * Cached values() array for frequent read-only operations, the array should NOT be mutated.
     */
    public static final EnumSoilType[] VALUES = values();

    public String getID() {
        return name().toLowerCase();
    }
}
