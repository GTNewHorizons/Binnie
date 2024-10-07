package binnie.core.machines.inventory;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.Collection;
import java.util.EnumSet;

import net.minecraftforge.common.util.ForgeDirection;

import binnie.core.util.I18N;

public class MachineSide {

    private static final EnumSet<ForgeDirection> SIDES = EnumSet
            .of(ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);
    private static final EnumSet<ForgeDirection> ALL = EnumSet
            .of(UP, DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);

    // Spotless spams this into two lines, not great
    // spotless:off
    private static final String[] SIDE_NAMES = new String[] {
        "binniecore.gui.side.down",
        "binniecore.gui.side.up",
        "binniecore.gui.side.north",
        "binniecore.gui.side.south",
        "binniecore.gui.side.west",
        "binniecore.gui.side.east"
    };
    // spotless:on

    public static String asString(Collection<ForgeDirection> sides) {
        if (sides.containsAll(MachineSide.ALL)) {
            return I18N.localise("binniecore.gui.side.any");
        }
        if (sides.isEmpty()) {
            return I18N.localise("binniecore.gui.side.none");
        }

        StringBuilder text = new StringBuilder();

        if (sides.containsAll(MachineSide.SIDES)) {
            text.append(I18N.localise("binniecore.gui.side.sides"));

            if (sides.contains(ForgeDirection.UP)) {
                text.append(", ");
                text.append(I18N.localise(SIDE_NAMES[UP.ordinal()]));
            }

            if (sides.contains(ForgeDirection.DOWN)) {
                text.append(", ");
                text.append(I18N.localise(SIDE_NAMES[DOWN.ordinal()]));
            }
        } else {
            for (ForgeDirection side : sides) {
                if (text.length() > 0) text.append(", ");

                text.append(I18N.localise(SIDE_NAMES[side.ordinal()]));
            }
        }
        return text.toString();
    }
}
