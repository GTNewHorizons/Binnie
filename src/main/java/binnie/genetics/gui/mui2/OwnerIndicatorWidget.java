package binnie.genetics.gui.mui2;

import com.cleanroommc.modularui.api.value.IStringValue;
import com.cleanroommc.modularui.api.value.ISyncOrValue;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.StringValue;
import com.cleanroommc.modularui.widget.Widget;

public class OwnerIndicatorWidget extends Widget<OwnerIndicatorWidget> {

    private static final UITexture OWNER_ICON = UITexture.builder().location("binniecore", "gui/craftgui-controls")
            .imageSize(256, 256).subAreaXYWH(132, 4, 16, 16).build();

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
        String owner = stringValue != null ? stringValue.getStringValue() : "";
        if (owner != null && !owner.isEmpty()) {
            OWNER_ICON.draw(0, 0, getArea().width, getArea().height);
        }
    }
}
