package binnie.core.machines.power;

public interface IProcess extends IErrorStateSource {

    float getEnergyPerTick();

    String getTooltip();

    boolean isInProgress();

    ProcessInfo getInfo();

    /***
     * Returns true if the current target got worked on at least once.
     */
    default boolean workedOnTarget() {
        return false;
    }
}
