package binnie.botany.api;

public enum EnumFlowerStage {

    FLOWER("Flower"),
    SEED("Seed"),
    POLLEN("Pollen"),
    NONE("NONE");

    private final String name;

    EnumFlowerStage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
