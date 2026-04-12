package binnie.genetics.gui.mui2;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.TankSlot;

/**
 * {@link FluidSlotSyncHandler} that validates fluids against the {@link TankSlot} and notifies the machine on changes.
 */
public class ValidatedFluidSlotSyncHandler extends FluidSlotSyncHandler {

    private final TankSlot tankSlot;
    @Nullable
    private final Machine machine;

    public ValidatedFluidSlotSyncHandler(TankSlot tankSlot, @Nullable Machine machine) {
        super(tankSlot.getTank());
        this.tankSlot = tankSlot;
        this.machine = machine;
    }

    @Override
    protected void fillFluid(@NotNull FluidStack heldFluid, boolean processFullStack) {
        if (!tankSlot.isValid(heldFluid)) {
            return;
        }
        super.fillFluid(heldFluid, processFullStack);
        if (machine != null) machine.markDirty();
    }

    @Override
    protected void drainFluid(@Nullable FluidStack heldFluid, boolean processFullStack) {
        super.drainFluid(heldFluid, processFullStack);
        if (machine != null) machine.markDirty();
    }
}
