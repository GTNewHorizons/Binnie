package binnie.core.genetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeModifierLogic {

    private final Map<EnumBeeModifier, Float[]> modifiers;
    private final List<EnumBeeBooleanModifier> booleanModifiers;

    public BeeModifierLogic() {
        modifiers = new HashMap<>();
        booleanModifiers = new ArrayList<>();
    }

    public float getModifier(EnumBeeModifier modifier, float currentModifier) {
        if (modifier == EnumBeeModifier.PRODUCTION) {
            if (!modifiers.containsKey(modifier)) {
                return 0.0f;
            }
            Float[] values = modifiers.get(modifier);
            float offset = values[0], max = values[1];
            if (offset < 0) {// negative modifiers should always apply
                return offset;
            }
            if (currentModifier >= max) {// don't decrease modifiers already over the max, just skip increasing it
                return 0.0f;
            }
            return Math.min(offset, max - currentModifier);
        } else {
            if (!modifiers.containsKey(modifier)) {
                return 1.0f;
            }

            Float[] values = modifiers.get(modifier);
            float mult = values[0];
            float max = values[1];
            if (max >= 1.0f) {
                if (max <= currentModifier) {
                    return 1.0f;
                }
                return Math.min(max / currentModifier, mult);
            }

            if (max >= currentModifier) {
                return 1.0f;
            }
            return Math.max(max / currentModifier, mult);
        }

    }

    public boolean getModifier(EnumBeeBooleanModifier modifier) {
        return booleanModifiers.contains(modifier);
    }

    public void setModifier(EnumBeeBooleanModifier modifier) {
        booleanModifiers.add(modifier);
    }

    public void setModifier(EnumBeeModifier modifier, float mult, float max) {
        modifiers.put(modifier, new Float[] { mult, max });
    }
}
