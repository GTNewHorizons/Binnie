package binnie.extratrees.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammer extends Item implements IToolHammer {

    private final String name;
    protected boolean isDurableHammer;

    public ItemHammer(boolean durable) {
        name = durable ? "durableHammer" : "hammer";

        isDurableHammer = durable;
        setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName(name);
        setMaxStackSize(1);
        setMaxDamage(durable ? 1562 : 251);

        GameRegistry.registerItem(this, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = ExtraTrees.proxy.getIcon(register, name);
    }

    @Override
    public String getItemStackDisplayName(ItemStack i) {
        return isDurableHammer ? I18N.localise("extratrees.item.masterCarpentryHammer.name")
                : I18N.localise("extratrees.item.carpentryHammer.name");
    }

    @Override
    public void onHammerUsed(ItemStack stack, EntityPlayer player) {
        stack.damageItem(1, player);
    }
}
