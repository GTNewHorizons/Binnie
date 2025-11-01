package binnie.core.mod.parser;

import binnie.core.item.ItemMisc;
import java.lang.reflect.Field;

import net.minecraft.item.Item;

import binnie.core.AbstractMod;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemParser extends FieldParser {

    @Override
    public boolean isHandled(Field field, AbstractMod mod) {
        return Item.class.isAssignableFrom(field.getType());
    }

    @Override
    public void preInit(Field field, AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        Item item = (Item) field.get(null);
        if (item != null && !(item instanceof ItemMisc)) {
            GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        }
    }
}
