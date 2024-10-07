package binnie.core.genetics;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class ForestryAllele {

    public enum Lifespan {

        Shortest,
        Shorter,
        Short,
        Shortened,
        Normal,
        Elongated,
        Long,
        Longer,
        Longest;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.lifespan" + this);
        }
    }

    public enum Speed {

        Slowest,
        Slower,
        Slow,
        Norm,
        Fast,
        Faster,
        Fastest;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.speed" + this);
        }
    }

    public enum Fertility {

        Low,
        Normal,
        High,
        Maximum;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.fertility" + this);
        }
    }

    public enum Territory {

        Default,
        Large,
        Larger,
        Largest;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.territory" + this);
        }
    }

    public enum Size {

        Smallest,
        Smaller,
        Small,
        Average,
        Large,
        Larger,
        Largest;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.size" + this);
        }
    }

    public enum Bool {

        True,
        False;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.bool" + this);
        }

        public static IAllele get(boolean bool) {
            return (bool ? Bool.True : Bool.False).getAllele();
        }
    }

    public enum Int {

        Int1,
        Int2,
        Int3,
        Int4,
        Int5,
        Int6,
        Int7,
        Int8,
        Int9,
        Int10;

        public IAllele getAllele() {
            return AlleleManager.alleleRegistry.getAllele("forestry.i" + (ordinal() + 1) + "d");
        }
    }
}
