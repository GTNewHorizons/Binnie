package binnie.botany.items;

import binnie.botany.flower.ItemFlower;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.flower.ItemPollen;
import binnie.botany.flower.ItemSeed;
import binnie.botany.gardening.ItemSoilMeter;
import binnie.botany.gardening.ItemTrowel;
import binnie.botany.genetics.ItemDictionary;
import binnie.core.item.DamageItems;
import net.minecraft.item.Item;

public class BotanyItems {

    public static final ItemInsulatedTube insulatedTube = new ItemInsulatedTube();
    public static final ItemSoilMeter soilMeter = new ItemSoilMeter();
    public static final DamageItems misc = new BotanyMisc();
    public static final ItemFlower flowerItem = new ItemFlower();
    public static final ItemPigment pigment = new ItemPigment();
    public static final ItemClay clay = new ItemClay();
    public static final Item seed = new ItemSeed();
    public static final Item pollen = new ItemPollen();
    public static final ItemDictionary database = new ItemDictionary();
    public static final ItemTrowel trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "Wood");
    public static final ItemTrowel trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "Stone");
    public static final ItemTrowel trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "Iron");
    public static final ItemTrowel trowelDiamond = new ItemTrowel(Item.ToolMaterial.EMERALD, "Diamond");
    public static final ItemTrowel trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "Gold");
}
