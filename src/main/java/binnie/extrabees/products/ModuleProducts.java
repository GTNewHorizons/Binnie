package binnie.extrabees.products;

import binnie.core.IInitializable;

public class ModuleProducts implements IInitializable {

    @Override
    public void postInit() {
        for (CombItems.Items comb : CombItems.Items.VALUES) {
            comb.addRecipe();
        }
        for (EnumHoneyDrop info2 : EnumHoneyDrop.values()) {
            info2.addRecipe();
        }
        for (Propolis.Items propolis : Propolis.Items.VALUES) {
            propolis.addRecipe();
        }
    }
}
