package binnie.extratrees.block;

import net.minecraft.util.IIcon;

import binnie.core.util.I18N;

public enum DoorType {

    STANDARD("standard"),
    SOLID("solid"),
    DOUBLE("double"),
    FULL("full");

    final String id;
    IIcon iconDoorLower;
    IIcon iconDoorUpper;
    IIcon iconDoorLowerFlip;
    IIcon iconDoorUpperFlip;
    IIcon iconItem;

    DoorType(String id) {
        this.id = id;
    }

    public String getName() {
        return I18N.localise("extratrees.block.door.type." + id);
    }
}
