package binnie.core;

import binnie.Binnie;

public abstract class ManagerBase implements IInitializable {

    public ManagerBase() {
        Binnie.Managers.add(this);
    }
}
