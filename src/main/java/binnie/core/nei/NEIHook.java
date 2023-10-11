package binnie.core.nei;

import org.lwjgl.opengl.GL11;

import binnie.core.craftgui.minecraft.Window;
import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class NEIHook {

    public static boolean enabled = false;

    static {
        if (Loader.isModLoaded("NotEnoughItems")) {
            enabled = true;
            addHandler();
        }
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void addHandler() {
        GuiContainerManager.addObjectHandler(new ContainerObjectHandler());
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static GuiContainerManager getManager() {
        return GuiContainerManager.getManager();
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void preDrawImpl() {
        GuiContainerManager manager = getManager();
        if (manager != null) manager.preDraw();
    }

    public static void preDraw() {
        if (enabled) preDrawImpl();
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void renderObjectsImpl(Window window, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(window.x(), window.y(), 0.0f);
        GuiContainerManager manager = getManager();
        if (manager != null) manager.renderObjects(mouseX, mouseY);
        GL11.glTranslatef(-window.x(), -window.y(), 0.0f);
        GL11.glPopMatrix();
    }

    public static void renderObjects(Window window, int mouseX, int mouseY) {
        if (enabled) renderObjectsImpl(window, mouseX, mouseY);
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void renderToolTipsImpl(int mouseX, int mouseY) {
        GuiContainerManager manager = getManager();
        if (manager != null) manager.renderToolTips(mouseX, mouseY);
    }

    public static void renderToolTips(int mouseX, int mouseY) {
        if (enabled) renderToolTipsImpl(mouseX, mouseY);
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void mouseClickedImpl(int x, int y, int button) {
        GuiContainerManager manager = getManager();
        if (manager != null) manager.mouseClicked(x, y, button);
    }

    public static void mouseClicked(int x, int y, int button) {
        if (enabled) mouseClickedImpl(x, y, button);
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void lastKeyTypedImpl(int k, char c) {
        GuiContainerManager manager = getManager();
        if (manager != null) manager.lastKeyTyped(k, c);
    }

    public static void lastKeyTyped(int k, char c) {
        if (enabled) lastKeyTypedImpl(k, c);
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void handleMouseWheelImpl() {
        GuiContainerManager manager = getManager();
        if (manager != null) manager.handleMouseWheel();
    }

    public static void handleMouseWheel() {
        if (enabled) handleMouseWheelImpl();
    }

    @Optional.Method(modid = "NotEnoughItems")
    private static void mouseUpImpl(int mouseX, int mouseY, int button) {
        GuiContainerManager manager = getManager();
        if (manager != null) manager.mouseUp(mouseX, mouseY, button);
    }

    public static void mouseUp(int mouseX, int mouseY, int button) {
        if (enabled) mouseUpImpl(mouseX, mouseY, button);
    }
}
