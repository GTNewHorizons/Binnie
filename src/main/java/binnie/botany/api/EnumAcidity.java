package binnie.botany.api;

public enum EnumAcidity {

    ACID,
    NEUTRAL,
    ALKALINE;

    /**
     * Cached values() array for frequent read-only operations, the array should NOT be mutated.
     */
    public static final EnumAcidity[] VALUES = values();

    public String getID() {
        return name().toLowerCase();
    }
}
