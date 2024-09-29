package binnie.core.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IItemMisc extends IItemEnum {

    IIcon getIcon(ItemStack itemStack);

    @SideOnly(Side.CLIENT)
    void registerIcons(IIconRegister register);
}
