package binnie.core.resource;

import net.minecraft.util.ResourceLocation;

import binnie.core.AbstractMod;

public class BinnieResource {

    String mod;
    private final ResourceType type;
    String path;
    private final String shortPath;
    private final ResourceLocation resourceLocation;

    public BinnieResource(AbstractMod mod, ResourceType type, String path) {
        this(mod.getModID(), type, path);
    }

    public BinnieResource(String modid, ResourceType type, String path) {
        mod = modid;
        this.type = type;
        this.path = path;
        shortPath = "textures/" + type + "/" + path;
        resourceLocation = new ResourceLocation(modid, shortPath);
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public String getShortPath() {
        return shortPath;
    }
}
