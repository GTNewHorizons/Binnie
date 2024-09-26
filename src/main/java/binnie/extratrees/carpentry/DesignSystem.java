package binnie.extratrees.carpentry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.item.ExtraTreeItems;

public enum DesignSystem implements IDesignSystem {

    Wood,
    Glass;

    private final Map<Integer, IIcon> primary;
    private final Map<Integer, IIcon> secondary;

    DesignSystem() {
        primary = new HashMap<>();
        secondary = new HashMap<>();
        DesignerManager.instance.registerDesignSystem(this);
    }

    @Override
    public IDesignMaterial getDefaultMaterial() {
        return switch (this) {
            case Glass -> GlassType.get(0);
            case Wood -> PlankType.ExtraTreePlanks.Fir;
        };
    }

    @Override
    public IDesignMaterial getDefaultMaterial2() {
        return switch (this) {
            case Glass -> GlassType.get(1);
            case Wood -> PlankType.ExtraTreePlanks.Whitebeam;
        };
    }

    @Override
    public IDesignMaterial getMaterial(int id) {
        return switch (this) {
            case Glass -> GlassType.get(id);
            case Wood -> CarpentryManager.carpentryInterface.getWoodMaterial(id);
        };
    }

    @Override
    public int getMaterialIndex(IDesignMaterial id) {
        return switch (this) {
            case Glass -> GlassType.getIndex(id);
            case Wood -> CarpentryManager.carpentryInterface.getCarpentryWoodIndex(id);
        };
    }

    public String getTexturePath() {
        return switch (this) {
            case Glass -> "glass";
            case Wood -> "patterns";
        };
    }

    @Override
    public IDesignMaterial getMaterial(ItemStack stack) {
        return switch (this) {
            case Glass -> GlassType.get(stack);
            case Wood -> CarpentryManager.carpentryInterface.getWoodMaterial(stack);
        };
    }

    @Override
    public ItemStack getAdhesive() {
        return switch (this) {
            case Glass -> ExtraTreeItems.GlassFitting.get(1);
            case Wood -> ExtraTreeItems.WoodWax.get(1);
        };
    }

    @Override
    public IIcon getPrimaryIcon(IPattern pattern) {
        if (pattern instanceof EnumPattern) {
            return primary.get(((EnumPattern) pattern).ordinal());
        }
        return null;
    }

    @Override
    public IIcon getSecondaryIcon(IPattern pattern) {
        if (pattern instanceof EnumPattern) {
            return secondary.get(((EnumPattern) pattern).ordinal());
        }
        return null;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (final EnumPattern pattern : EnumPattern.VALUES) {
            primary.put(
                    pattern.ordinal(),
                    BinnieCore.proxy.getIcon(
                            register,
                            getMod().getModID(),
                            getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0"));
            secondary.put(
                    pattern.ordinal(),
                    BinnieCore.proxy.getIcon(
                            register,
                            getMod().getModID(),
                            getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1"));
        }
    }

    public AbstractMod getMod() {
        return ExtraTrees.instance;
    }
}
