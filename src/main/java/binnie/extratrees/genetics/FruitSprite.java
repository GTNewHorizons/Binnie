package binnie.extratrees.genetics;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import binnie.Binnie;
import binnie.core.resource.BinnieIcon;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.IIconProvider;

public enum FruitSprite implements IIconProvider {

    Tiny,
    Small,
    Average,
    Large,
    Larger,
    Pear;

    /**
     * Cached values() array for frequent read-only operations, the array should NOT be mutated.
     */
    public static final FruitSprite[] VALUES = values();
    private BinnieIcon icon;

    public short getIndex() {
        return (short) (ordinal() + 4200);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(short texUID) {
        int index = texUID - 4200;
        if (index >= 0 && index < VALUES.length) {
            return VALUES[index].icon.getIcon();
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon = Binnie.Resource.getBlockIcon(ExtraTrees.instance, "fruit/" + toString().toLowerCase());
    }
}
