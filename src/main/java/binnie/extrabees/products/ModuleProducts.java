package binnie.extrabees.products;

import binnie.core.IInitializable;

public class ModuleProducts implements IInitializable {

    @Override
    public void postInit() {
        for (CombItems.Items comb : CombItems.Items.VALUES) {
            comb.addRecipe();
        }
        for (DropItems.Items drop : DropItems.Items.VALUES) {
            drop.addRecipe();
        }
        for (Propolis.Items propolis : Propolis.Items.VALUES) {
            propolis.addRecipe();
        }
    }
}
