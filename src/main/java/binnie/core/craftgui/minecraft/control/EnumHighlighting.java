package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.minecraft.MinecraftTooltip;

public enum EnumHighlighting {

    ERROR,
    WARNING,
    HELP,
    SHIFT_CLICK;

    int getColour() {
        return switch (this) {
            case ERROR -> MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR);
            case HELP -> MinecraftTooltip.getOutline(Tooltip.Type.HELP);
            case SHIFT_CLICK -> 0xffff00;
            case WARNING -> MinecraftTooltip.getOutline(MinecraftTooltip.Type.WARNING);
        };
    }
}
