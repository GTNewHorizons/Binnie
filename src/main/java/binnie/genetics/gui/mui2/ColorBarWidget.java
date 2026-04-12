package binnie.genetics.gui.mui2;

import java.util.function.DoubleSupplier;

import com.cleanroommc.modularui.api.value.IDoubleValue;
import com.cleanroommc.modularui.api.value.ISyncOrValue;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.DoubleValue;
import com.cleanroommc.modularui.widget.Widget;

import codechicken.nei.recipe.GuiCraftingRecipe;

public class ColorBarWidget extends Widget<ColorBarWidget> implements Interactable {

    private int bgColor = 0xFF333333;
    private int fillColor = 0xFF00CC00;
    private int borderColor = 0xFF373737;
    private int highlightColor = 0xFFE0E0E0;
    private boolean vertical = true;
    private boolean bordered = true;
    private IDoubleValue<?> doubleValue;
    private String neiHandlerId;

    @Override
    public void onInit() {
        if (this.doubleValue == null) {
            this.doubleValue = new DoubleValue(0.0);
        }
    }

    @Override
    public boolean isValidSyncOrValue(ISyncOrValue syncOrValue) {
        return syncOrValue.isTypeOrEmpty(IDoubleValue.class);
    }

    @Override
    protected void setSyncOrValue(ISyncOrValue syncOrValue) {
        super.setSyncOrValue(syncOrValue);
        this.doubleValue = syncOrValue.castNullable(IDoubleValue.class);
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> entry) {
        int w = getArea().width;
        int h = getArea().height;

        if (bordered) {
            // Inset shadow matching vanilla slot style: dark top/left, light bottom/right
            GuiDraw.drawRect(0, 0, w, 1, borderColor);
            GuiDraw.drawRect(0, 0, 1, h, borderColor);
            GuiDraw.drawRect(0, h - 1, w, 1, highlightColor);
            GuiDraw.drawRect(w - 1, 0, 1, h, highlightColor);
            GuiDraw.drawRect(1, 1, w - 2, h - 2, bgColor);
            float progress = Math.max(0f, Math.min(1f, (float) doubleValue.getDoubleValue()));
            if (progress > 0) {
                if (vertical) {
                    int fillH = (int) ((h - 2) * progress);
                    GuiDraw.drawRect(1, h - 1 - fillH, w - 2, fillH, fillColor);
                } else {
                    int fillW = (int) ((w - 2) * progress);
                    GuiDraw.drawRect(1, 1, fillW, h - 2, fillColor);
                }
            }
        } else {
            GuiDraw.drawRect(0, 0, w, h, bgColor);

            float progress = Math.max(0f, Math.min(1f, (float) doubleValue.getDoubleValue()));
            if (progress > 0) {
                if (vertical) {
                    int fillH = (int) (h * progress);
                    GuiDraw.drawRect(0, h - fillH, w, fillH, fillColor);
                } else {
                    int fillW = (int) (w * progress);
                    GuiDraw.drawRect(0, 0, fillW, h, fillColor);
                }
            }
        }
    }

    public ColorBarWidget value(IDoubleValue<?> value) {
        setSyncOrValue(ISyncOrValue.orEmpty(value));
        return this;
    }

    public ColorBarWidget progress(DoubleSupplier supplier) {
        return value(new DoubleValue.Dynamic(supplier, null));
    }

    public ColorBarWidget backgroundColor(int color) {
        this.bgColor = color;
        return this;
    }

    public ColorBarWidget fillColor(int color) {
        this.fillColor = color;
        return this;
    }

    public ColorBarWidget borderColor(int color) {
        this.borderColor = color;
        return this;
    }

    public ColorBarWidget highlightColor(int color) {
        this.highlightColor = color;
        return this;
    }

    public ColorBarWidget bordered(boolean bordered) {
        this.bordered = bordered;
        return this;
    }

    public ColorBarWidget vertical() {
        this.vertical = true;
        return this;
    }

    public ColorBarWidget horizontal() {
        this.vertical = false;
        return this;
    }

    public ColorBarWidget neiTransferRect(String id) {
        this.neiHandlerId = id;
        return this;
    }

    @Override
    public Interactable.Result onMousePressed(int mouseButton) {
        if (neiHandlerId != null) {
            GuiCraftingRecipe.openRecipeGui(neiHandlerId);
            return Interactable.Result.SUCCESS;
        }
        return Interactable.super.onMousePressed(mouseButton);
    }
}
