package binnie.extrabees.core;


import static binnie.extrabees.item.EBItems.itemMisc;

import binnie.core.IInitializable;
import binnie.extrabees.item.EBMisc;

public class ModuleCore implements IInitializable {

    @Override
    public void preInit() {
        // Loads EBItems.class, guaranteeing that items are init by this point
        // TODO: is this needed?
        itemMisc.getUnlocalizedName();
    }

    @Override
    public void init() {
        EBMisc.init();
    }

    @Override
    public void postInit() {
        EBMisc.postInit();
    }
}
