package binnie.extratrees.machines;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.util.ForgeDirection;

import forestry.core.render.TankRenderInfo;

public class MachineRendererForestry {

    static Map<String, Object> instances = new HashMap<>();
    static Method renderMethod;

    private static void loadMethod(String file) {
        try {
            Class cls = Class.forName("forestry.core.render.RenderMachine");
            Object instance = cls.getConstructor(String.class).newInstance(file);
            MachineRendererForestry.renderMethod = cls.getDeclaredMethod(
                    "render",
                    TankRenderInfo.class,
                    TankRenderInfo.class,
                    ForgeDirection.class,
                    Double.TYPE,
                    Double.TYPE,
                    Double.TYPE);
            MachineRendererForestry.renderMethod.setAccessible(true);
            MachineRendererForestry.instances.put(file, instance);
        } catch (Exception ex) {
            // ex.printStackTrace();
        }
    }

    public static void renderMachine(String name, double x, double y, double z) {
        if (!MachineRendererForestry.instances.containsKey(name)) {
            loadMethod(name);
        }

        try {
            MachineRendererForestry.renderMethod.invoke(
                    MachineRendererForestry.instances.get(name),
                    TankRenderInfo.EMPTY,
                    TankRenderInfo.EMPTY,
                    ForgeDirection.UP,
                    x,
                    y,
                    z);
        } catch (Exception ex) {
            // ex.printStackTrace();
        }
    }
}
