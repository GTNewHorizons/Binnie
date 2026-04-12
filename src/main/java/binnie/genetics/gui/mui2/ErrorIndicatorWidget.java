package binnie.genetics.gui.mui2;

import com.cleanroommc.modularui.api.value.IStringValue;
import com.cleanroommc.modularui.api.value.ISyncOrValue;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.StringValue;
import com.cleanroommc.modularui.widget.Widget;

public class ErrorIndicatorWidget extends Widget<ErrorIndicatorWidget> {

    private static final UITexture ERROR_ICON = UITexture.fullImage("gregtech", "gui/picture/stalled_electricity");

    private IStringValue<?> stringValue;

    @Override
    public void onInit() {
        if (this.stringValue == null) {
            this.stringValue = new StringValue("");
        }
    }

    @Override
    public boolean isValidSyncOrValue(ISyncOrValue syncOrValue) {
        return syncOrValue.isTypeOrEmpty(IStringValue.class);
    }

    @Override
    protected void setSyncOrValue(ISyncOrValue syncOrValue) {
        super.setSyncOrValue(syncOrValue);
        this.stringValue = syncOrValue.castNullable(IStringValue.class);
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> entry) {
        String error = stringValue != null ? stringValue.getStringValue() : "";
        boolean hasError = error != null && !error.isEmpty();

        if (hasError) {
            ERROR_ICON.draw(0, 0, getArea().width, getArea().height);
        }
    }

    public ErrorIndicatorWidget value(IStringValue<?> value) {
        setSyncOrValue(ISyncOrValue.orEmpty(value));
        return this;
    }
}
