package binnie.genetics.gui.mui2;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.screen.RichTooltip;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.Widget;

import binnie.Binnie;
import binnie.genetics.api.IGene;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import forestry.api.genetics.IAlleleSpecies;

public class GeneIconWidget extends Widget<GeneIconWidget> implements Interactable {

    private final IGene gene;
    private final PanelSyncManager syncManager;

    public GeneIconWidget(IGene gene, PanelSyncManager syncManager) {
        this.gene = gene;
        this.syncManager = syncManager;
        size(16, 16);
        tooltipBuilder(this::buildTooltip);
    }

    private void buildTooltip(RichTooltip tooltip) {
        String cName = Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getChromosomeName(gene.getChromosome());
        tooltip.addLine(IKey.str(cName + ": " + gene.getName()));
        if (gene.getAllele() instanceof IAlleleSpecies) {
            String authority = ((IAlleleSpecies) gene.getAllele()).getAuthority();
            tooltip.addLine(IKey.lang("binniecore.gui.database.branch.discoveredBy", authority));
        }
        ItemStack held = syncManager.getCursorItem();
        if (canFill(held)) {
            tooltip.addLine(IKey.lang("genetics.gui.geneBank.tooltip.assignGene"));
            IGene existingGene = Engineering.getGene(held, gene.getChromosome().ordinal());
            if (existingGene != null) {
                String dName = Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getChromosomeName(gene.getChromosome());
                tooltip.addLine(IKey.lang("genetics.gui.geneBank.tooltip.willReplace", dName, existingGene.getName()));
            }
        }
    }

    private boolean canFill(ItemStack stack) {
        return stack != null && stack.stackSize == 1
                && Engineering.isGeneAcceptor(stack)
                && Engineering.canAcceptGene(stack, gene);
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_CURRENT_BIT);

        ItemStack held = syncManager.getCursorItem();
        if (isHovering() && canFill(held)) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            drawQuad(0, 0, 16, 16);
            GL11.glColor4f(0.267f, 0.267f, 0.267f, 1f);
            drawQuad(1, 1, 15, 15);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        IIcon icon = GeneticsTexture.dnaIcon.getIcon();
        if (icon != null) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            net.minecraft.client.Minecraft.getMinecraft().getTextureManager()
                    .bindTexture(net.minecraft.client.renderer.texture.TextureMap.locationItemsTexture);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 16, 0, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(16, 16, 0, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(16, 0, 0, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(0, 0, 0, icon.getMinU(), icon.getMinV());
            tessellator.draw();
        }

        GL11.glPopAttrib();
    }

    private static void drawQuad(int x1, int y1, int x2, int y2) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(x1, y2, 0);
        tessellator.addVertex(x2, y2, 0);
        tessellator.addVertex(x2, y1, 0);
        tessellator.addVertex(x1, y1, 0);
        tessellator.draw();
    }

    @Override
    public Result onMousePressed(int mouseButton) {
        if (mouseButton != 0) {
            return Result.IGNORE;
        }
        ItemStack held = syncManager.getCursorItem();
        if (!canFill(held)) {
            return Result.IGNORE;
        }

        NBTTagCompound geneNBT = new NBTTagCompound();
        gene.writeToNBT(geneNBT);

        syncManager.callSyncedAction("gene-select", buf -> {
            NBTTagCompound action = new NBTTagCompound();
            action.setTag("gene", geneNBT);
            try {
                buf.writeNBTTagCompoundToBuffer(action);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return Result.SUCCESS;
    }

    @Override
    public boolean canHover() {
        return true;
    }
}
