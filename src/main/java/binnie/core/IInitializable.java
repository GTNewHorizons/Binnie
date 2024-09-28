package binnie.core;

public interface IInitializable {

    default void preInit() {}

    default void init() {}

    default void postInit() {}
}
