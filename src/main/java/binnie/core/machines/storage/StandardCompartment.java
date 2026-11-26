package binnie.core.machines.storage;

import binnie.core.texture.BinnieCoreTexture;

public class StandardCompartment {

    public static class PackageCompartment extends Compartment.PackageCompartment {

        public PackageCompartment() {
            super("compartment", BinnieCoreTexture.Compartment, 4, 25);
        }
    }

    public static class PackageCompartmentCopper extends Compartment.PackageCompartment {

        public PackageCompartmentCopper() {
            super("compartmentCopper", BinnieCoreTexture.CompartmentCopper, 6, 25);
        }
    }

    public static class PackageCompartmentBronze extends Compartment.PackageCompartment {

        public PackageCompartmentBronze() {
            super("compartmentBronze", BinnieCoreTexture.CompartmentBronze, 8, 25);
        }
    }

    public static class PackageCompartmentIron extends Compartment.PackageCompartment {

        public PackageCompartmentIron() {
            super("compartmentIron", BinnieCoreTexture.CompartmentIron, 4, 50);
        }
    }

    public static class PackageCompartmentGold extends Compartment.PackageCompartment {

        public PackageCompartmentGold() {
            super("compartmentGold", BinnieCoreTexture.CompartmentGold, 6, 50);
        }
    }

    public static class PackageCompartmentDiamond extends Compartment.PackageCompartment {

        public PackageCompartmentDiamond() {
            super("compartmentDiamond", BinnieCoreTexture.CompartmentDiamond, 8, 50);
        }
    }
}
