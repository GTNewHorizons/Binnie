package binnie.core.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class PositionedStackAdv extends PositionedStack {

    private final List<String> tooltip = new ArrayList<>();

    public PositionedStackAdv(Object object, int x, int y) {
        super(object, x, y);
    }

    public PositionedStackAdv(Object object, int x, int y, List<String> tooltip) {
        super(object, x, y);
        this.addToTooltip(tooltip);
    }

    public Rectangle getRect() {
        return new Rectangle(this.relx - 1, this.rely - 1, 18, 18);
    }

    public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip) {
        if (!this.tooltip.isEmpty()) {
            currenttip.addAll(this.tooltip);
        }
        return currenttip;
    }

    public PositionedStackAdv addToTooltip(List<String> lines) {
        this.tooltip.addAll(lines);
        return this;
    }

    public PositionedStackAdv addToTooltip(String line) {
        this.tooltip.add(line);
        return this;
    }
}
