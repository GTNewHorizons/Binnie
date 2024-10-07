package binnie.extratrees.machines.designer;

import java.util.Locale;

import net.minecraft.item.ItemStack;

import binnie.botany.Botany;
import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.core.ExtraTreeTexture;

public enum DesignerType {

    WOODWORKER(ExtraTreeTexture.carpenterTexture),
    PANELWORKER(ExtraTreeTexture.panelerTexture),
    GLASSWORKER(ExtraTreeTexture.panelerTexture),
    TILEWORKER(ExtraTreeTexture.tileworkerTexture);

    public final String name;
    public final String texture;

    DesignerType(String texture) {
        this.name = name().toLowerCase(Locale.ENGLISH);
        this.texture = texture;
    }

    public IDesignSystem getSystem() {
        return switch (this) {
            case GLASSWORKER -> DesignSystem.Glass;
            case TILEWORKER -> CeramicDesignSystem.instance;
            default -> DesignSystem.Wood;
        };
    }

    public ItemStack getBlock(IDesignMaterial type1, IDesignMaterial type2, IDesign design) {
        int stackSize = 2;
        if (design == EnumDesign.Blank) {
            type2 = type1;
            stackSize = 1;
        }

        ItemStack stack = ModuleCarpentry.getItemStack(getBlock(), type1, type2, design);
        stack.stackSize = stackSize;
        return stack;
    }

    private BlockDesign getBlock() {
        return switch (this) {
            case GLASSWORKER -> ExtraTrees.blockStained;
            case PANELWORKER -> ExtraTrees.blockPanel;
            case TILEWORKER -> Botany.ceramicTile;
            default -> ExtraTrees.blockCarpentry;
        };
    }

    public ItemStack getDisplayStack(IDesign design) {
        return getBlock(getSystem().getDefaultMaterial(), getSystem().getDefaultMaterial2(), design);
    }

    public String getMaterialTooltip() {
        return switch (this) {
            case GLASSWORKER -> I18N.localise("extratrees.machine.glassworker.material");
            case PANELWORKER -> I18N.localise("extratrees.machine.panelworker.material");
            case TILEWORKER -> I18N.localise("extratrees.machine.tileworker.material");
            case WOODWORKER -> I18N.localise("extratrees.machine.woodworker.material");
        };
    }
}
