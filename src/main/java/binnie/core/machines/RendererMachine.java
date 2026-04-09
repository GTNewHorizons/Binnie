package binnie.core.machines;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import binnie.Binnie;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

// Hmm. It's used class
public class RendererMachine extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTick) {
        renderTileEntity((TileEntityMachine) entity, x, y, z, partialTick);
    }

    private void renderTileEntity(TileEntityMachine entity, double x, double y, double z, float partialTick) {
        if (entity != null && entity.getMachine() != null) {
            MachinePackage machinePackage = entity.getMachine().getPackage();
            machinePackage.renderMachine(entity.getMachine(), x, y, z, partialTick);
        }
    }

    private void renderInvBlock(Block block, int i, int j) {
        TileEntity entity = block.createTileEntity(null, i);
        renderTileEntity((TileEntityMachine) entity, 0.0, -0.1, 0.0, 0.0625f);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (modelID == Binnie.Machine.getMachineRenderID()) {
            renderInvBlock(block, metadata, modelID);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        TileEntityMachine tile = (TileEntityMachine) world.getTileEntity(x, y, z);
        if (tile != null && tile.getMachine() != null
                && tile.getMachine().getPackage() != null
                && tile.getMachine().getPackage().getGroup() != null
                && !tile.getMachine().getPackage().getGroup().customRenderer) {
            renderTileEntity(tile, x, y, z, 1.0f);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Binnie.Machine.getMachineRenderID();
    }
}
