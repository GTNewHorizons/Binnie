package binnie.core.genetics;

import binnie.core.util.I18N;

public enum EnumBeeBooleanModifier {

    SEALED,
    SELF_LIGHTED,
    SUNLIGHT_STIMULATED,
    HELLISH;

    public String getName() {
        return I18N.localise("binniecore.beebooleanmodifier." + name().toLowerCase());
    }
}
