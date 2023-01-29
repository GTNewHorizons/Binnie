package binnie.extratrees.block;

import net.minecraft.util.IIcon;

import binnie.extratrees.api.IDesignMaterial;

public interface IPlankType extends IDesignMaterial {

    IIcon getIcon();

    String getDescription();
}
