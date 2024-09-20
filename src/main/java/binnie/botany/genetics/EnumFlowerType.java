package binnie.botany.genetics;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlowerType;

public enum EnumFlowerType implements IFlowerType {

    DANDELION,
    POPPY,
    ORCHID,
    ALLIUM,
    BLUET,
    TULIP,
    DAISY,
    CORNFLOWER,
    PANSY,
    IRIS,
    LAVENDER(2),
    VIOLA,
    DAFFODIL,
    DAHLIA,
    PEONY(2),
    ROSE(2),
    LILAC(2),
    HYDRANGEA(2),
    FOXGLOVE(2),
    ZINNIA,
    MUMS,
    MARIGOLD,
    GERANIUM,
    AZALEA,
    PRIMROSE,
    ASTER,
    CARNATION,
    LILY,
    YARROW,
    PETUNIA,
    AGAPANTHUS,
    FUCHSIA,
    DIANTHUS,
    FORGET,
    ANEMONE,
    AQUILEGIA,
    EDELWEISS,
    SCABIOUS,
    CONEFLOWER,
    GAILLARDIA,
    AURICULA,
    CAMELLIA(2),
    GOLDENROD(2),
    ALTHEA(2),
    PENSTEMON(2),
    DELPHINIUM(2),
    HOLLYHOCK(2);

    private final int sections;
    private final IIcon[] stem;
    private final IIcon[] petal;
    private final IIcon[] variant;
    private final IIcon[] unflowered;
    private IIcon seedStem;
    private IIcon seedPetal;
    private IIcon seedVariant;
    private IIcon pollenStem;
    private IIcon pollenPetal;
    private IIcon pollenVariant;
    private IIcon blank;

    EnumFlowerType() {
        this(1);
    }

    EnumFlowerType(int sections) {
        this.sections = sections;
        stem = new IIcon[sections];
        petal = new IIcon[sections];
        variant = new IIcon[sections];
        unflowered = new IIcon[sections];
    }

    @Override
    public IIcon getStem(EnumFlowerStage stage, boolean flowered, int section) {
        if (stage == EnumFlowerStage.SEED) {
            return seedStem;
        }
        if (stage == EnumFlowerStage.POLLEN) {
            return pollenStem;
        }
        return stem[section % sections];
    }

    @Override
    public IIcon getPetalIcon(EnumFlowerStage stage, boolean flowered, int section) {
        if (stage == EnumFlowerStage.SEED) {
            return seedPetal;
        }
        if (stage == EnumFlowerStage.POLLEN) {
            return pollenPetal;
        }
        if (flowered) {
            return petal[section % sections];
        }
        return unflowered[section % sections];
    }

    @Override
    public IIcon getVariantIcon(EnumFlowerStage stage, boolean flowered, int section) {
        if (stage == EnumFlowerStage.SEED) {
            return seedVariant;
        }
        if (stage == EnumFlowerStage.POLLEN) {
            return pollenVariant;
        }
        return flowered ? variant[section % sections] : blank;
    }

    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < sections; ++i) {
            String suf = (i == 0) ? "" : ("" + (i + 1));
            String pre = (sections == 1) ? "" : "double/";
            String id = pre + toString().toLowerCase() + suf;
            stem[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".0");
            petal[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".1");
            variant[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".2");
            unflowered[i] = Botany.proxy.getIcon(register, "flowers/" + id + ".3");
        }

        blank = Botany.proxy.getIcon(register, "flowers/blank");
        seedStem = Botany.proxy.getIcon(register, "flowers/seed.0");
        seedPetal = Botany.proxy.getIcon(register, "flowers/seed.1");
        seedVariant = Botany.proxy.getIcon(register, "flowers/seed.2");
        pollenStem = Botany.proxy.getIcon(register, "flowers/pollen.0");
        pollenPetal = Botany.proxy.getIcon(register, "flowers/pollen.1");
        pollenVariant = Botany.proxy.getIcon(register, "flowers/pollen.2");
    }

    @Override
    public int getID() {
        return ordinal();
    }

    @Override
    public int getSections() {
        return sections;
    }

    public IIcon getBlank() {
        return blank;
    }
}
