package binnie.core.block;

import java.util.List;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IBlockMetadata extends ITileEntityProvider {

    default int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
        return TileEntityMetadata.getItemDamage(itemStack);
    }

    default int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    String getBlockName(ItemStack itemStack);

    default void addBlockTooltip(ItemStack itemStack, List<String> tooltip) {}

    void dropAsStack(World world, int x, int y, int z, ItemStack itemStack);
}
