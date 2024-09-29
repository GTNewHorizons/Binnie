package binnie.genetics.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.GeneticsCreativeTab;
import binnie.genetics.core.GeneticsGUI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAnalyst extends Item {

    public ItemAnalyst() {
        final String name = "analyst";

        setCreativeTab(GeneticsCreativeTab.instance);
        setUnlocalizedName(name);
        setMaxStackSize(1);

        GameRegistry.registerItem(this, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = Genetics.proxy.getIcon(register, "analyst");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        Genetics.proxy.openGui(GeneticsGUI.Analyst, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18N.localise("genetics.item.analyst.name");
    }
}
