package binnie.core.craftgui.minecraft;

import net.minecraft.util.IIcon;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;

public enum GUIIcon {

    ArrowUp("arrow-up"),
    ArrowDown("arrow-down"),
    ArrowLeft("arrow-left"),
    ArrowRight("arrow-right"),
    ArrowUpLeft("arrow-upleft"),
    ArrowUpRight("arrow-upright"),
    ArrowRightUp("arrow-rightup"),
    ArrowRightDown("arrow-rightdown"),
    ArrowDownRight("arrow-downright"),
    ArrowDownLeft("arrow-downleft"),
    ArrowLeftDown("arrow-leftdown"),
    ArrowLeftUp("arrow-leftup");

    final String path;
    BinnieIcon icon;

    GUIIcon(String path) {
        this.path = path;
    }

    public void register() {
        icon = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/" + path);
    }

    public IIcon getIcon() {
        return icon.getIcon();
    }
}
