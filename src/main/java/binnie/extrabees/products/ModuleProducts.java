package binnie.extrabees.products;

import binnie.core.IInitializable;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleProducts implements IInitializable {

    @Override
    public void preInit() {
        OreDictionary.registerOre("ingotIron", Items.iron_ingot);
        OreDictionary.registerOre("ingotGold", Items.gold_ingot);
        OreDictionary.registerOre("gemDiamond", Items.diamond);
    }

    @Override
    public void init() {
        ItemHoneyComb.addSubtypes();
    }

    @Override
    public void postInit() {
        for (EnumHoneyComb info : EnumHoneyComb.values()) {
            info.addRecipe();
        }
        for (EnumHoneyDrop info2 : EnumHoneyDrop.values()) {
            info2.addRecipe();
        }
        for (Propolis.Items propolis : Propolis.Items.VALUES) {
            propolis.addRecipe();
        }
    }
}
