package binnie.core.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;

public class ItemGenesis extends Item {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = BinnieCore.proxy.getIcon(register, "genesis");
    }

    public ItemGenesis() {
        final String name = "genesis";

        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName(name);
        setMaxStackSize(1);

        GameRegistry.registerItem(this, name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        BinnieCore.proxy
                .openGui(BinnieCoreGUI.Genesis, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        return itemStack;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        return I18N.localise("binniecore.item.genesis.name");
    }
}
