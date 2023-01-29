package binnie.extratrees.carpentry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import binnie.extratrees.api.IDesignSystem;

public class DesignerManager {

    public static DesignerManager instance = new DesignerManager();

    List<IDesignSystem> systems;

    public DesignerManager() {
        systems = new ArrayList<>();
    }

    public void registerDesignSystem(IDesignSystem system) {
        systems.add(system);
    }

    public Collection<IDesignSystem> getDesignSystems() {
        return systems;
    }
}
