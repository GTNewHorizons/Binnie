package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenDefault extends WorldGenTree {

    public WorldGenDefault(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
