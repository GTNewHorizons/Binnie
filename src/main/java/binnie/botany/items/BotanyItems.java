package binnie.botany.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import binnie.botany.Botany;
import binnie.core.item.IItemMisc;
import binnie.core.util.I18N;

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
    public void addInformation(List<String> data) {
        // ignored
    }

    @Override
    public String getName(ItemStack itemStack) {
        return I18N.localise("botany.item." + name + ".name");
    }

    @Override
    public ItemStack get(int count) {
        return new ItemStack(Botany.misc, count, ordinal());
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
