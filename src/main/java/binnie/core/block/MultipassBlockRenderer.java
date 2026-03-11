package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import binnie.core.BinnieCore;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MultipassBlockRenderer implements ISimpleBlockRenderingHandler {

    public static MultipassBlockRenderer instance;
    private static int layer = 0;
    private static boolean rendering = false;

    public MultipassBlockRenderer() {
        MultipassBlockRenderer.instance = this;
    }

    public static int getLayer() {
        return MultipassBlockRenderer.layer;
    }

    public static boolean isRendering() {
        return MultipassBlockRenderer.rendering;
    }

    private static int getPassesSafe(Block block) {
        try {
            return ((IMultipassBlock) block).getNumberOfPasses();
        } catch (Throwable t) {
            return 1;
        }
    }

    private static int getColorSafe(Block block, int meta) {
        try {
            if (block instanceof IMultipassBlock) {
                try {
					java.lang.reflect.Method m = block.getClass().getMethod("colorMultiplier", int.class);
					Object out = m.invoke(block, Integer.valueOf(meta));
                    if (out instanceof Integer) {
                        return ((Integer) out).intValue();
                    }
                } catch (Throwable ignored) {
                }
            }
            return block.getRenderColor(meta);
        } catch (Throwable t) {
            return 16777215;
        }
    }
    @Override
    public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);

        MultipassBlockRenderer.rendering = true;
        MultipassBlockRenderer.layer = 0;
        while (MultipassBlockRenderer.layer < getPassesSafe(block)) {
            renderItem(block, renderer, meta);
            MultipassBlockRenderer.layer++;
        }
        MultipassBlockRenderer.layer = 0;
        MultipassBlockRenderer.rendering = false;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        boolean r = true;
        MultipassBlockRenderer.rendering = true;
        MultipassBlockRenderer.layer = 0;
        while (MultipassBlockRenderer.layer < getPassesSafe(block)) {
            try {
                r = renderer.renderStandardBlock(block, x, y, z);
            } catch (Throwable t) {
                return false;
            }
            MultipassBlockRenderer.layer++;
        }
        MultipassBlockRenderer.layer = 0;
        MultipassBlockRenderer.rendering = false;
        return r;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    @Override
    public int getRenderId() {
        return BinnieCore.multipassRenderID;
    }

    public void renderItem(Block block, RenderBlocks renderer, int meta) {
        setColor(getColorSafe(block, meta));
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
        tessellator.draw();
    }

    public void setColor(int l) {
        float f = (l >> 16 & 0xFF) / 255.0f;
        float f2 = (l >> 8 & 0xFF) / 255.0f;
        float f3 = (l & 0xFF) / 255.0f;
        GL11.glColor3f(f, f2, f3);
    }
}
