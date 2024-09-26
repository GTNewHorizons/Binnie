package binnie.extratrees.nei;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInterModComms;

public class IMCForNEI {

    public static void IMCSender() {
        sendHandler("binnie.extratrees.nei.NEIHandlerLumbermill", "ExtraTrees:machine:0");

        sendCatalyst("extratrees.lumbermill", "ExtraTrees:machine:0");
    }

    private static void sendHandler(String name, String block) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", name);
        aNBT.setString("modName", "Extra Trees");
        aNBT.setString("modId", "ExtraTrees");
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", block);
        aNBT.setInteger("handlerHeight", 76);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", 4);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String name, String stack, int prio) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", name);
        aNBT.setString("itemName", stack);
        aNBT.setInteger("priority", prio);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    private static void sendCatalyst(String name, String stack) {
        sendCatalyst(name, stack, 0);
    }
}
