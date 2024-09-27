package binnie.genetics;

import static binnie.genetics.item.GeneticsMisc.Items.EmptySerum;

import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GeneticsCreativeTab extends CreativeTabs {

    public static CreativeTabs instance = new GeneticsCreativeTab();

    public GeneticsCreativeTab() {
        super("Genetics");
    }

    @Override
    public ItemStack getIconItemStack() {
        return EmptySerum.get(1);
    }

    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTabLabel() {
        return I18N.localise("extrabees.tab.genetics");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return null;
    }
}
