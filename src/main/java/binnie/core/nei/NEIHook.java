package binnie.core.nei;

import org.lwjgl.opengl.GL11;

import binnie.core.craftgui.minecraft.Window;
import codechicken.nei.guihook.GuiContainerManager;

public final class NEIHook {

    private static boolean enabled = false;

    static {
        try {
            GuiContainerManager.addObjectHandler(new ContainerObjectHandler());
            enabled = true;
        } catch (NoClassDefFoundError ignored) {

        }
    }

    private static GuiContainerManager getManager() {
        return GuiContainerManager.getManager();
    }

    public static void preDraw() {
        if (enabled) getManager().preDraw();
    }

    public static void renderObjects(Window window, int mouseX, int mouseY) {
        if (enabled) {
            GL11.glPushMatrix();
            GL11.glTranslatef(window.x(), window.y(), 0.0f);
            GuiContainerManager.getManager().renderObjects(mouseX, mouseY);
            GL11.glTranslatef(-window.x(), -window.y(), 0.0f);
            GL11.glPopMatrix();
        }
    }

    public static void renderToolTips(int mouseX, int mouseY) {
        if (enabled) getManager().renderToolTips(mouseX, mouseY);
    }

    public static void mouseClicked(int x, int y, int button) {
        if (enabled) getManager().mouseClicked(x, y, button);
    }

    public static void lastKeyTyped(int k, char c) {
        if (enabled) getManager().lastKeyTyped(k, c);
    }

    public static void handleMouseWheel() {
        if (enabled) getManager().handleMouseWheel();
    }

    public static void mouseUp(int mouseX, int mouseY, int button) {
        if (enabled) getManager().mouseUp(mouseX, mouseY, button);
    }
}
