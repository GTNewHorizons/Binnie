package binnie.core.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class DamageItems extends Item {

    private final String[] NAMES;
    private final IIcon[] ICONS;
    private final String modid;

    protected DamageItems(CreativeTabs tab, String modid, String... names) {
        final String name = "misc";

        setCreativeTab(tab);
        setHasSubtypes(true);
        setUnlocalizedName(name);

        NAMES = names;
        ICONS = new IIcon[names.length];
        this.modid = modid;
        GameRegistry.registerItem(this, name);
    }

    public ItemStack getStack(int count, int damage) {
        return new ItemStack(this, count, damage);
    }

    /**
     * @param name One of the names passed to the constructor
     * @return The unlocalized name string
     */
    public String getUnlocalizedName(String name) {
        return modid + ".item." + name + ".name";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item thiz, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < NAMES.length; ++i) {
            list.add(new ItemStack(thiz, 1, i));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        final int damage = stack.getItemDamage();
        return (damage < NAMES.length) ? getUnlocalizedName(NAMES[damage]) : "null";
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconFromDamage(stack.getItemDamage());
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return (damage < ICONS.length && damage >= 0) ? ICONS[damage] : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < NAMES.length; ++i) {
            ICONS[i] = register.registerIcon(modid + ":" + NAMES[i]);
        }
    }
}
