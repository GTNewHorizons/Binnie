package binnie.extratrees.core;

import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;

public enum ExtraTreeTexture implements IBinnieTexture {

    Gui(ResourceType.GUI, "gui"),
    Nursery(ResourceType.Tile, "Nursery");

    public static final String carpenterTexture = "extratrees/carpenter_";
    public static final String panelerTexture = "extratrees/paneler_";
    public static final String tileworkerTexture = "extratrees/tileworker_";
    public static final String incubatorTexture = "extratrees/incubator_";
    public static final String lumbermillTexture = "extratrees/sawmill_";
    public static final String pressTexture = "extratrees/press_";
    public static final String distilleryTexture = "extratrees/distillery_";
    public static final String breweryTexture = "extratrees/brewery_";
    public static final String infuserTexture = "extratrees/infuser_";

    private final String texture;
    private final ResourceType type;

    ExtraTreeTexture(ResourceType base, String texture) {
        this.texture = texture;
        type = base;
    }

    @Override
    public BinnieResource getTexture() {
        return Binnie.Resource.getPNG(ExtraTrees.instance, type, texture);
    }
}
