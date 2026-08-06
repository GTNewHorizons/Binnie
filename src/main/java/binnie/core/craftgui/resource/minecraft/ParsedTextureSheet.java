package binnie.core.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

class ParsedTextureSheet implements IBinnieTexture {

    private final String name;
    private final String modid;
    private final String path;
    private final BinnieResource texture;

    public ParsedTextureSheet(String name, String modid, String path) {
        this.name = name;
        this.modid = modid;
        this.path = path;
        texture = new BinnieResource(modid, ResourceType.GUI, path);
    }

    @Override
    public BinnieResource getTexture() {
        return texture;
    }
}
