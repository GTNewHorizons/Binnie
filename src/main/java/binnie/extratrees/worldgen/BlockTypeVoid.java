package binnie.extratrees.worldgen;

import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;

public class BlockTypeVoid extends BlockType {

    public BlockTypeVoid() {
        super(null, 0);
    }

    @Override
    public void setBlock(World world, ITreeGenData tree, int x, int y, int z) {
        world.setBlockToAir(x, y, z);
        if (world.getTileEntity(x, y, z) != null) {
            world.removeTileEntity(x, y, z);
        }
    }
}
