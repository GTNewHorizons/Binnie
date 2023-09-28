package binnie.core.nei;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import binnie.core.craftgui.minecraft.GuiCraftGUI;
import codechicken.nei.guihook.IContainerObjectHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(
        iface = "codechicken.nei.guihook.IContainerObjectHandler",
        modid = "NotEnoughItems",
        striprefs = true)
public final class ContainerObjectHandler implements IContainerObjectHandler {

    @Override
    public void guiTick(GuiContainer gui) {

    }

    @Override
    public void refresh(GuiContainer gui) {

    }

    @Override
    public void load(GuiContainer gui) {

    }

    @Override
    public ItemStack getStackUnderMouse(GuiContainer gui, int mousex, int mousey) {
        if (gui instanceof GuiCraftGUI) {
            return ((GuiCraftGUI) gui).getStackUnderMouse(mousex, mousey);
        } else {
            return null;
        }
    }

    @Override
    public boolean objectUnderMouse(GuiContainer gui, int mousex, int mousey) {
        return false;
    }

    @Override
    public boolean shouldShowTooltip(GuiContainer gui) {
        return true;
    }
}
