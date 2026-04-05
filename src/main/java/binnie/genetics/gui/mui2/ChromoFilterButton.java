package binnie.genetics.gui.mui2;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widget.Widget;

import forestry.api.genetics.IChromosomeType;

public class ChromoFilterButton extends Widget<ChromoFilterButton> implements Interactable {

    private final String label;
    private final GeneScrollWidget scrollWidget;
    private final IChromosomeType chromosomeType;

    public ChromoFilterButton(String label, GeneScrollWidget scrollWidget, IChromosomeType chromosomeType, int width) {
        this.label = label;
        this.scrollWidget = scrollWidget;
        this.chromosomeType = chromosomeType;
        size(width, 11);
    }

    private boolean isActive() {
        if (chromosomeType == null) {
            return scrollWidget.getChromosomeFilter() == null;
        }
        return scrollWidget.getChromosomeFilter() == chromosomeType;
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_CURRENT_BIT);

        int w = getArea().width;
        int h = getArea().height;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        if (isActive()) {
            GL11.glColor4f(0.18f, 0.35f, 0.63f, 1f);
        } else if (isHovering()) {
            GL11.glColor4f(0.25f, 0.25f, 0.25f, 1f);
        } else {
            GL11.glColor4f(0.17f, 0.17f, 0.17f, 1f);
        }
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertex(0, h, 0);
        t.addVertex(w, h, 0);
        t.addVertex(w, 0, 0);
        t.addVertex(0, 0, 0);
        t.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        int textColor = isActive() ? 0xFFFFFF : 0xAAAAAA;
        fr.drawString(label, 3, (h - 8) / 2, textColor);

        GL11.glPopAttrib();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    @Override
    public Result onMousePressed(int mouseButton) {
        if (mouseButton != 0) return Result.IGNORE;
        scrollWidget.setChromosomeFilter(chromosomeType);
        return Result.SUCCESS;
    }

    @Override
    public boolean canHover() {
        return true;
    }
}
