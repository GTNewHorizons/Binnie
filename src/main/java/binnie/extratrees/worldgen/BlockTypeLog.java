package binnie.extratrees.worldgen;

import net.minecraft.world.World;

import binnie.extratrees.block.ILogType;
import forestry.api.world.ITreeGenData;

public class BlockTypeLog extends BlockType {

    protected ILogType log;

    public BlockTypeLog(ILogType log) {
        super(null, 0);
        this.log = log;
    }

    @Override
    public void setBlock(World world, ITreeGenData tree, int x, int y, int z) {
        log.placeBlock(world, x, y, z);
    }
}
