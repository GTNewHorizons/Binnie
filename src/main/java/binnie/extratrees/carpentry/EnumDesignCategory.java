package binnie.extratrees.carpentry;

import java.util.ArrayList;
import java.util.List;

import binnie.core.util.I18N;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;

public enum EnumDesignCategory implements IDesignCategory {

    Design("designsEmblems"),
    Stripes("squaresStripes"),
    Edges("edges"),
    Barred("bars"),
    Letters("letters"),
    Diagonal("diagonals");

    private final String name;
    private final List<IDesign> designs;

    EnumDesignCategory(String name) {
        this.name = name;
        designs = new ArrayList<>();
        CarpentryManager.carpentryInterface.registerDesignCategory(this);
    }

    @Override
    public String getName() {
        return I18N.localise("botany.design.category." + name);
    }

    @Override
    public List<IDesign> getDesigns() {
        return designs;
    }

    @Override
    public void addDesign(IDesign design) {
        designs.add(design);
    }

    @Override
    public String getId() {
        return toString().toLowerCase();
    }
}
