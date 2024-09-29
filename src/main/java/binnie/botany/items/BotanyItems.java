package binnie.botany.items;

import binnie.botany.Botany;
import binnie.core.item.IItemMisc;
import binnie.core.util.I18N;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum BotanyItems implements IItemMisc {

    AshPowder("powderAsh"),
    PulpPowder("powderPulp"),
    MulchPowder("powderMulch"),
    SulphurPowder("powderSulphur"),
    FertiliserPowder("powderFertiliser"),
    CompostPowder("powderCompost"),
    Mortar("mortar"),
    Weedkiller("weedkiller");

    private IIcon icon;
    private final String name;
    private final String iconPath;

    BotanyItems(String name) {
        this.name = name;
        this.iconPath = name;
    }

    @Override
    public IIcon getIcon(ItemStack itemStack) {
        return icon;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = Botany.proxy.getIcon(register, iconPath);
    }

    @Override
    public String getName(ItemStack itemStack) {
        return I18N.localise("botany.item." + name + ".name");
    }

    @Override
    public ItemStack get(int count) {
        return new ItemStack(Botany.misc, count, ordinal());
    }

}
