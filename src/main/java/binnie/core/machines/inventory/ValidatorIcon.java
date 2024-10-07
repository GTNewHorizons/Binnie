package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.List;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.resource.BinnieIcon;

public class ValidatorIcon {

    private final List<BinnieIcon> iconsInput;
    private final List<BinnieIcon> iconsOutput;

    public ValidatorIcon(AbstractMod mod, String pathInput, String pathOutput) {
        iconsInput = new ArrayList<>();
        iconsOutput = new ArrayList<>();
        iconsInput.add(Binnie.Resource.getItemIcon(mod, pathInput));
        iconsOutput.add(Binnie.Resource.getItemIcon(mod, pathOutput));
    }

    public BinnieIcon getIcon(boolean input) {
        return input ? iconsInput.get(0) : iconsOutput.get(0);
    }
}
