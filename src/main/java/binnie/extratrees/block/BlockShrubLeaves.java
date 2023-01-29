package binnie.extratrees.block;

import net.minecraft.world.World;

import forestry.arboriculture.blocks.BlockForestryLeaves;

public class BlockShrubLeaves extends BlockForestryLeaves {

    public BlockShrubLeaves() {
        super();
    }

    @Override
    public void beginLeavesDecay(World world, int x, int y, int z) {
        // ignored, no able decay
    }
}
