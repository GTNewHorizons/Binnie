package binnie.botany.api;

public enum EnumMoisture {

    DRY,
    NORMAL,
    DAMP;

    /**
     * Cached values() array for frequent read-only operations, the array should NOT be mutated.
     */
    public static final EnumMoisture[] VALUES = values();

    public String getID() {
        return name().toLowerCase();
    }
}
