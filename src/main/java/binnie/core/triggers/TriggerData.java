package binnie.core.triggers;

import java.util.Map;

import buildcraft.api.statements.ITriggerExternal;

public class TriggerData implements Map.Entry<ITriggerExternal, Boolean> {

    private final ITriggerExternal key;
    private Boolean value;

    public TriggerData(ITriggerExternal key, Boolean value) {
        if (key == null) {
            throw new NullPointerException();
        }
        this.key = key;
        this.value = value;
    }

    @Override
    public ITriggerExternal getKey() {
        return key;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public Boolean setValue(Boolean value) {
        Boolean old = this.value;
        this.value = value;
        return old;
    }
}
