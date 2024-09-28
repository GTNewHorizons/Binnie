package binnie.extrabees.genetics;

import binnie.core.Mods;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.genetics.requirements.RequirementPerson;
import binnie.extrabees.products.CombItems;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleBeeSpeciesCustom;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.genetics.BeeBranchDefinition;
import forestry.apiculture.genetics.BeeDefinition;
import forestry.apiculture.genetics.IBeeDefinition;
import forestry.apiculture.genetics.alleles.AlleleEffect;
import forestry.core.genetics.IBranchDefinition;
import forestry.core.genetics.alleles.EnumAllele;
import java.awt.Color;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.BiomeDictionary;

public enum ExtraBeeDefinition implements IBeeDefinition {

    /* BARREN BRANCH */
    ARID(ExtraBeeBranchDefinition.BARREN, "aridus", true, new Color(0xbee854), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f).setTemperature(EnumTemperature.HOT)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MEADOWS, BeeDefinition.FRUGAL, 10);
            registerMutation(BeeDefinition.FOREST, BeeDefinition.FRUGAL, 10);
        }
    },
    BARREN(ExtraBeeBranchDefinition.BARREN, "infelix", true, new Color(0xe0d263), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f).setTemperature(EnumTemperature.HOT)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.COMMON, ARID, 10);
        }
    },
    DESOLATE(ExtraBeeBranchDefinition.BARREN, "desolo", false, new Color(0xd1b890), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f).setTemperature(EnumTemperature.HOT)
                    .setHumidity(EnumHumidity.ARID).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.HUNGER.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(ARID, BARREN, 10);
        }
    },
    DECOMPOSING(ExtraBeeBranchDefinition.BARREN, "aegrus", true, new Color(0x523711), new Color(0xffffff)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f).addSpecialty(CombItems.Items.COMPOST.get(1), 0.08f)
                    .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MARSHY, BARREN, 15);
        }
    },
    GNAWING(ExtraBeeBranchDefinition.BARREN, "apica", true, new Color(0xe874b0), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.25f).addSpecialty(CombItems.Items.SAWDUST.get(1), 0.25f)
                    .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.FLOWER_PROVIDER,
                    AlleleHelper.getAllele(ExtraBeesFlowers.WOOD.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FOREST, BARREN, 15);
        }
    },

    /* HOSTILE BRANCH */
    ROTTEN(ExtraBeeBranchDefinition.HOSTILE, "caries", true, new Color(0xbfe0b6), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f).addSpecialty(CombItems.Items.ROTTEN.get(1), 0.10f)
                    .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.SPAWN_ZOMBIE.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MEADOWS, DESOLATE, 15);
        }
    },
    BONE(ExtraBeeBranchDefinition.HOSTILE, "os", true, new Color(0xe9ede8), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f).addSpecialty(CombItems.Items.BONE.get(1), 0.10f)
                    .setTemperature(EnumTemperature.HOT).setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.SPAWN_SKELETON.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FOREST, DESOLATE, 15);
        }
    },
    CREEPER(ExtraBeeBranchDefinition.HOSTILE, "erepo", true, new Color(0x2ce615), new Color(0xcbe374)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.30f)
                    .addSpecialty(CombItems.ForestryCombs.POWDERY.get(), 0.08f).setTemperature(EnumTemperature.HOT)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.SPAWN_CREEPER.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MODEST, DESOLATE, 15);
        }
    },

    /* ROCKY BRANCH */
    ROCK(ExtraBeeBranchDefinition.ROCKY, "saxum", true, new Color(0xa8a8a8), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.30f);
        }
    },
    STONE(ExtraBeeBranchDefinition.ROCKY, "lapis", false, new Color(0x757575), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.30f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.DILIGENT, ROCK, 12);
        }
    },
    GRANITE(ExtraBeeBranchDefinition.ROCKY, "granum", true, new Color(0x695555), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.30f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
            AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.UNWEARY, STONE, 10);
        }
    },
    MINERAL(ExtraBeeBranchDefinition.ROCKY, "minerale", true, new Color(0x6e757d), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.30f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
            AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.INDUSTRIOUS, GRANITE, 6);
        }
    },

    /* METALLIC BRANCH */
    COPPER(ExtraBeeBranchDefinition.METALLIC, "cuprous", true, new Color(0xd16308), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.COPPER.get(1), 0.06f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.WINTRY, MINERAL, 5);
            registerMutation(BeeDefinition.MODEST, MINERAL, 5);
        }
    },
    TIN(ExtraBeeBranchDefinition.METALLIC, "stannus", true, new Color(0xbdb1bd), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.TIN.get(1), 0.06f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MARSHY, MINERAL, 5);
            registerMutation(BeeDefinition.TROPICAL, MINERAL, 5);
        }
    },
    IRON(ExtraBeeBranchDefinition.METALLIC, "ferrous", false, new Color(0xa87058), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.IRON.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MEADOWS, MINERAL, 5);
            registerMutation(BeeDefinition.FOREST, MINERAL, 5);
        }
    },
    LEAD(ExtraBeeBranchDefinition.METALLIC, "plumbous", true, new Color(0xad8bab), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.LEAD.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MEADOWS, MINERAL, 5);
            registerMutation(BeeDefinition.MODEST, MINERAL, 5);
        }
    },

    /* METALLIC2 BRANCH */
    ZINC(ExtraBeeBranchDefinition.METALLIC2, "spelta", true, new Color(0xedebff), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.ZINC.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.WINTRY, MINERAL, 5);
            registerMutation(BeeDefinition.TROPICAL, MINERAL, 5);
        }
    },
    TITANIUM(ExtraBeeBranchDefinition.METALLIC2, "titania", true, new Color(0xb0aae3), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.TITANIUM.get(1), 0.02f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.CULTIVATED, MINERAL, 3);
        }
    },
    TUNGSTATE(ExtraBeeBranchDefinition.METALLIC2, "wolfram", true, new Color(0x131214), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.TUNGSTEN.get(1), 0.01f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.COMMON, MINERAL, 3);
        }
    },
    NICKEL(ExtraBeeBranchDefinition.METALLIC2, "claro", true, new Color(0xffdefc), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.NICKEL.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FOREST, MINERAL, 5);
            registerMutation(BeeDefinition.MARSHY, MINERAL, 5);
        }
    },

    /* PRECIOUS BRANCH */
    GOLD(ExtraBeeBranchDefinition.PRECIOUS, "aureus", true, new Color(0xe6cc0b), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.GOLD.get(1), 0.02f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MAJESTIC, IRON, 2);
            registerMutation(BeeDefinition.MAJESTIC, COPPER, 2);
            registerMutation(BeeDefinition.MAJESTIC, NICKEL, 2);
            registerMutation(BeeDefinition.MAJESTIC, TUNGSTATE, 2);
        }
    },
    SILVER(ExtraBeeBranchDefinition.PRECIOUS, "argentus", false, new Color(0x43455b), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.SILVER.get(1), 0.02f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MAJESTIC, ZINC, 2);
            registerMutation(BeeDefinition.MAJESTIC, TIN, 2);
            registerMutation(BeeDefinition.MAJESTIC, LEAD, 2);
            registerMutation(BeeDefinition.MAJESTIC, TITANIUM, 2);
        }
    },
    // ELECTRUM,
    PLATINUM(ExtraBeeBranchDefinition.PRECIOUS, "platina", false, new Color(0xdbdbdb), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.PLATINUM.get(1), 0.01f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(GOLD, SILVER, 2);
        }
    },

    /* MINERAL BRANCH */
    LAPIS(ExtraBeeBranchDefinition.MINERAL, "lazuli", true, new Color(0x3d2cdb), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.LAPIS.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.IMPERIAL, MINERAL, 5);
        }
    },
    // SODALITE,
    // PYRITE,
    // BAUXITE,
    // CINNABAR,
    // SPHALERITE,

    /* GEMSTONE BRANCH */
    EMERALD(ExtraBeeBranchDefinition.GEMSTONE, "emerala", true, new Color(0x1cff03), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.EMERALD.get(1), 0.04f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FOREST, LAPIS, 5);
        }
    },
    RUBY(ExtraBeeBranchDefinition.GEMSTONE, "ruba", true, new Color(0xd60000), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.RUBY.get(1), 0.03f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MODEST, LAPIS, 5);
        }
    },
    SAPPHIRE(ExtraBeeBranchDefinition.GEMSTONE, "saphhira", true, new Color(0x0a47ff), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.SAPPHIRE.get(1), 0.03f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(WATER, LAPIS, 5);
        }
    },
    // OLIVINE,
    DIAMOND(ExtraBeeBranchDefinition.GEMSTONE, "diama", true, new Color(0x7fbdfa), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.STONE.get(1), 0.20f).addSpecialty(CombItems.Items.DIAMOND.get(1), 0.01f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.CULTIVATED, LAPIS, 5);
        }
    },

    /* NUCLEAR BRANCH */
    UNSTABLE(ExtraBeeBranchDefinition.NUCLEAR, "levis", false, new Color(0x3e8c34), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.20f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(PREHISTORIC, MINERAL, 5);
        }
    },
    NUCLEAR(ExtraBeeBranchDefinition.NUCLEAR, "nucleus", false, new Color(0x41cc2f), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.20f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(UNSTABLE, IRON, 5);
            registerMutation(UNSTABLE, COPPER, 5);
            registerMutation(UNSTABLE, TIN, 5);
            registerMutation(UNSTABLE, ZINC, 5);
            registerMutation(UNSTABLE, NICKEL, 5);
            registerMutation(UNSTABLE, LEAD, 5);
        }
    },
    RADIOACTIVE(ExtraBeeBranchDefinition.NUCLEAR, "fervens", false, new Color(0x1eff00), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.20f).addSpecialty(CombItems.Items.URANIUM.get(1), 0.02f)
                    .setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(NUCLEAR, GOLD, 5);
            registerMutation(NUCLEAR, SILVER, 5);
        }
    },
    YELLORIUM(ExtraBeeBranchDefinition.NUCLEAR, "yellori", true, new Color(0xd5ed00), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.20f)
                    .addSpecialty(CombItems.Items.YELLORIUM.get(1), 0.02f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTEST);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.RADIOACTIVE.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FRUGAL, NUCLEAR, 5);
        }
    },
    CYANITE(ExtraBeeBranchDefinition.NUCLEAR, "cyanita", true, new Color(0x0086ed), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.20f).addSpecialty(CombItems.Items.CYANITE.get(1), 0.01f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTEST);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.RADIOACTIVE.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(NUCLEAR, YELLORIUM, 5);
        }
    },
    BLUTONIUM(ExtraBeeBranchDefinition.NUCLEAR, "caruthus", true, new Color(0x1b00e6), new Color(0x999999)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.BARREN.get(1), 0.20f)
                    .addSpecialty(CombItems.Items.BLUTONIUM.get(1), 0.01f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTEST);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.RADIOACTIVE.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(CYANITE, YELLORIUM, 5);
        }
    },

    /* HISTORIC BRANCH */
    ANCIENT(ExtraBeeBranchDefinition.HISTORIC, "antiquus", true, new Color(0xf2db8f), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.30f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.NOBLE, BeeDefinition.DILIGENT, 10);
        }
    },
    PRIMEVAL(ExtraBeeBranchDefinition.HISTORIC, "priscus", true, new Color(0xb3a67b), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.30f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.SECLUDED, ANCIENT, 8);
        }
    },
    PREHISTORIC(ExtraBeeBranchDefinition.HISTORIC, "pristinus", false, new Color(0x6e5a40), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.30f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.LOW);
        }

        @Override
        protected void registerMutations() {
            registerMutation(PRIMEVAL, ANCIENT, 8);
        }
    },
    RELIC(ExtraBeeBranchDefinition.HISTORIC, "sapiens", true, new Color(0x4d3e16), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.30f).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.LONGEST);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.IMPERIAL, PREHISTORIC, 8);
        }
    },

    /* FOSSILIZED BRANCH */
    COAL(ExtraBeeBranchDefinition.FOSSILIZED, "carbo", true, new Color(0x7a7648), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.20f).addSpecialty(CombItems.Items.COAL.get(1), 0.08f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(PRIMEVAL, GROWING, 8);
            registerMutation(BeeDefinition.RURAL, PRIMEVAL, 8);
        }
    },
    RESIN(ExtraBeeBranchDefinition.FOSSILIZED, "lacrima", false, new Color(0xa6731b), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.20f).addSpecialty(CombItems.Items.RESIN.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MIRY, PRIMEVAL, 8);
        }
    },
    OIL(ExtraBeeBranchDefinition.FOSSILIZED, "lubricus", true, new Color(0x574770), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.20f).addSpecialty(CombItems.Items.OIL.get(1), 0.05f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(OCEAN, PRIMEVAL, 8);
            registerMutation(BeeDefinition.FRUGAL, PRIMEVAL, 8);
        }
    },
    // PEAT,

    /* REFINED BRANCH */
    DISTILLED(ExtraBeeBranchDefinition.REFINED, "distilli", false, new Color(0x356356), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OLD.get(1), 0.10f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.INDUSTRIOUS, OIL, 8);
        }
    },
    FUEL(ExtraBeeBranchDefinition.REFINED, "refina", true, new Color(0xffc003), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.OIL.get(1), 0.10f).addSpecialty(CombItems.Items.FUEL.get(1), 0.04f)
                    .setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(DISTILLED, OIL, 8);
        }
    },
    CREOSOTE(ExtraBeeBranchDefinition.REFINED, "creosota", true, new Color(0x979e13), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.COAL.get(1), 0.10f).addSpecialty(CombItems.Items.CREOSOTE.get(1), 0.07f)
                    .setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(DISTILLED, COAL, 8);
        }
    },
    LATEX(ExtraBeeBranchDefinition.REFINED, "latex", true, new Color(0x494a3e), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.RESIN.get(1), 0.10f).addSpecialty(CombItems.Items.LATEX.get(1), 0.05f)
                    .setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(DISTILLED, RESIN, 8);
        }
    },

    /* AQUATIC BRANCH */
    WATER(ExtraBeeBranchDefinition.AQUATIC, "aqua", true, new Color(0x94a2ff), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.WATER.get(1), 0.30f).setHumidity(EnumHumidity.DAMP);
        }
    },
    RIVER(ExtraBeeBranchDefinition.AQUATIC, "flumen", true, new Color(0x83b3d4), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.WATER.get(1), 0.30f).addSpecialty(CombItems.Items.CLAY.get(1), 0.20f)
                    .setHumidity(EnumHumidity.DAMP);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.DILIGENT, WATER, 10).restrictBiomeType(BiomeDictionary.Type.RIVER);
        }
    },
    OCEAN(ExtraBeeBranchDefinition.AQUATIC, "mare", false, new Color(0x1d2ead), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.WATER.get(1), 0.30f).setHumidity(EnumHumidity.DAMP);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.DILIGENT, WATER, 10).restrictBiomeType(BiomeDictionary.Type.OCEAN);
        }
    },
    INK(ExtraBeeBranchDefinition.AQUATIC, "atramentum", true, new Color(0xe1447), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.WATER.get(1), 0.30f).addSpecialty(new ItemStack(Items.dye, 1, 0), 0.10f)
                    .setHumidity(EnumHumidity.DAMP);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BLACK, OCEAN, 8);
        }
    },

    /* AGRARIAN BRANCH */
    GROWING(BeeBranchDefinition.AGRARIAN, "tyrelli", true, new Color(0x5bebd8), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.35f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.FLOWER_PROVIDER,
                    AlleleHelper.getAllele(ExtraBeesFlowers.LEAVES.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FOREST, BeeDefinition.DILIGENT, 10);
        }
    },
    FARM(BeeBranchDefinition.AGRARIAN, "ager", true, new Color(0x75db60), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.WHEATEN.get(), 0.30f)
                    .addSpecialty(CombItems.Items.SEED.get(1), 0.10f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FARMERLY, BeeDefinition.MEADOWS, 10);
        }
    },
    THRIVING(BeeBranchDefinition.AGRARIAN, "thriva", true, new Color(0x34e37d), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.35f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.FAST);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.FLOWER_PROVIDER,
                    AlleleHelper.getAllele(ExtraBeesFlowers.LEAVES.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.UNWEARY, GROWING, 10);
        }
    },
    BLOOMING(BeeBranchDefinition.AGRARIAN, "blooma", true, new Color(0x0abf34), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.35f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.FASTEST);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.FLOWER_PROVIDER,
                    AlleleHelper.getAllele(ExtraBeesFlowers.SAPLING.getUID()));
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.BONEMEAL_SAPLING.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.INDUSTRIOUS, THRIVING, 8);
        }
    },

    /* SACCHARINE BRANCH */
    SWEET(ExtraBeeBranchDefinition.SACCHARINE, "mellitus", true, new Color(0xfc51f1), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.40f)
                    .addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.VALIANT, BeeDefinition.DILIGENT, 15);
        }
    },
    SUGAR(ExtraBeeBranchDefinition.SACCHARINE, "dulcis", true, new Color(0xe6d3e0), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.40f)
                    .addProduct(new ItemStack(Items.sugar, 1, 0), 0.20f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.RURAL, SWEET, 15);
        }
    },
    RIPENING(ExtraBeeBranchDefinition.SACCHARINE, "ripa", true, new Color(0xb2c75d), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.30f)
                    .addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f)
                    .addSpecialty(CombItems.Items.FRUIT.get(1), 0.10f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.FLOWER_PROVIDER,
                    AlleleHelper.getAllele(ExtraBeesFlowers.FRUIT.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(SWEET, GROWING, 5);
        }
    },
    FRUIT(ExtraBeeBranchDefinition.SACCHARINE, "pomum", true, new Color(0xdb5876), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.30f)
                    .addProduct(new ItemStack(Items.sugar, 1, 0), 0.15f).addSpecialty(CombItems.Items.FRUIT.get(1), 0.20f)
                    .setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.BONEMEAL_FRUIT.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(SWEET, THRIVING, 5);
        }
    },

    /* FARMING BRANCH */
    ALCOHOL(ExtraBeeBranchDefinition.FARMING, "vinum", false, new Color(0xe88a61), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.WHEATEN.get(), 0.30f)
                    .addSpecialty(CombItems.Items.ALCOHOL.get(1), 0.10f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele("forestry.effectDrunkard"));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FARMERLY, BeeDefinition.MEADOWS, 10);
        }
    },
    MILK(ExtraBeeBranchDefinition.FARMING, "lacteus", true, new Color(0xe3e8e8), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.WHEATEN.get(), 0.30f)
                    .addSpecialty(CombItems.Items.MILK.get(1), 0.10f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FARMERLY, ExtraBeeDefinition.WATER, 10);
        }
    },
    COFFEE(ExtraBeeBranchDefinition.FARMING, "arabica", true, new Color(0x8c5e30), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.WHEATEN.get(), 0.30f)
                    .addSpecialty(CombItems.Items.COFFEE.get(1), 0.08f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FARMERLY, BeeDefinition.TROPICAL, 10);
        }
    },
    // CITRUS,
    // MINT,

    /* BOGGY BRANCH */
    SWAMP(BeeBranchDefinition.BOGGY, "paludis", true, new Color(0x356933), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.MOSSY.get(), 0.30f).setHumidity(EnumHumidity.DAMP);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.SLOW.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MIRY, WATER, 10);
        }
    },
    BOGGY(BeeBranchDefinition.BOGGY, "lama", false, new Color(0x785c29), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.MOSSY.get(), 0.30f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.SLOW.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.BOGGY, SWAMP, 8);
        }
    },
    FUNGAL(BeeBranchDefinition.BOGGY, "boletus", true, new Color(0xd16200), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.MOSSY.get(), 0.30f)
                    .addSpecialty(CombItems.Items.FUNGAL.get(1), 0.15f).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.BONEMEAL_MUSHROOM.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.BOGGY, BeeDefinition.MIRY, 8);
            registerMutation(BeeDefinition.BOGGY, FUNGAL, 8);
        }
    },

    /* CLASSICAL BRANCH */
    // MARBLE,
    // ROMAN,
    // GREEK,
    // CLASSICAL,

    /* VOLCANIC BRANCH */
    BASALT(ExtraBeeBranchDefinition.VOLCANIC, "aceri", true, new Color(0x8c6969), new Color(0x9a2323)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SIMMERING.get(), 0.25f)
                    .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.EFFECT, AlleleEffect.effectAggressive);
        }
    },
    TEMPERED(ExtraBeeBranchDefinition.VOLCANIC, "iratus", false, new Color(0x8a4848), new Color(0x9a2323)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SIMMERING.get(), 0.25f)
                    .setTemperature(EnumTemperature.HELLISH).setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FIENDISH, ExtraBeeDefinition.BASALT, 30)
                    .restrictBiomeType(BiomeDictionary.Type.NETHER);
        }
    },
    // ANGRY,
    VOLCANIC(ExtraBeeBranchDefinition.VOLCANIC, "volcano", true, new Color(0x4d0c0c), new Color(0x9a2323)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SIMMERING.get(), 0.25f)
                    .addSpecialty(CombItems.Items.BLAZE.get(1), 0.10f).setTemperature(EnumTemperature.HELLISH)
                    .setHumidity(EnumHumidity.ARID).setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.DEMONIC, ExtraBeeDefinition.TEMPERED, 20)
                    .restrictBiomeType(BiomeDictionary.Type.NETHER);
        }
    },
    GLOWSTONE(ExtraBeeBranchDefinition.VOLCANIC, "glowia", true, new Color(0xe0c61b), new Color(0x9a2323)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.GLOWSTONE.get(1), 0.15f).setTemperature(EnumTemperature.HELLISH)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.EFFECT, AlleleEffect.effectAggressive);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.TEMPERED, ExtraBeeDefinition.EXCITED, 5);
        }
    },

    /* VIRULENT BRANCH */
    MALICIOUS(ExtraBeeBranchDefinition.VIRULENT, "acerbus", true, new Color(0x782a77), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.SINISTER, BeeDefinition.TROPICAL, 10);
        }
    },
    INFECTIOUS(ExtraBeeBranchDefinition.VIRULENT, "contagio", true, new Color(0xb82eb5), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.TROPICAL, ExtraBeeDefinition.MALICIOUS, 8);
        }
    },
    VIRULENT(ExtraBeeBranchDefinition.VIRULENT, "morbus", false, new Color(0xf013ec), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f)
                    .addSpecialty(CombItems.Items.VENOMOUS.get(1), 0.12f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.MALICIOUS, ExtraBeeDefinition.INFECTIOUS, 8);
        }
    },

    /* VISCOUS BRANCH */
    VISCOUS(ExtraBeeBranchDefinition.VISCOUS, "liquidus", true, new Color(0x9470e), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.EXOTIC, ExtraBeeDefinition.WATER, 10);
        }
    },
    GLUTINOUS(ExtraBeeBranchDefinition.VISCOUS, "glutina", true, new Color(0x1d8c27), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.NORMAL);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.EXOTIC, ExtraBeeDefinition.VISCOUS, 8);
        }
    },
    STICKY(ExtraBeeBranchDefinition.VISCOUS, "lentesco", true, new Color(0x17e328), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f)
                    .addSpecialty(CombItems.Items.SLIME.get(1), 0.12f).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.FAST);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.VISCOUS, ExtraBeeDefinition.GLUTINOUS, 8);
        }
    },

    /* CAUSTIC BRANCH */
    CORROSIVE(ExtraBeeBranchDefinition.CAUSTIC, "corrumpo", false, new Color(0x4a5c0b), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.20f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.MALICIOUS, ExtraBeeDefinition.VISCOUS, 10);
        }
    },
    CAUSTIC(ExtraBeeBranchDefinition.CAUSTIC, "torrens", true, new Color(0x84a11d), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.25f)
                    .addSpecialty(CombItems.Items.ACIDIC.get(1), 0.03f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FIENDISH, ExtraBeeDefinition.CORROSIVE, 8);
        }
    },
    ACIDIC(ExtraBeeBranchDefinition.CAUSTIC, "acidus", true, new Color(0xc0f016), new Color(0x069764)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.SILKY.get(), 0.20f)
                    .addSpecialty(CombItems.Items.ACIDIC.get(1), 0.16f).setHumidity(EnumHumidity.DAMP)
                    .setTemperature(EnumTemperature.WARM).setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.CORROSIVE, ExtraBeeDefinition.CAUSTIC, 4);
        }
    },

    /* ENERGETIC BRANCH */
    EXCITED(ExtraBeeBranchDefinition.ENERGETIC, "excita", true, new Color(0xff4545), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.REDSTONE.get(1), 0.10f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.VALIANT, BeeDefinition.CULTIVATED, 10);
        }
    },
    ENERGETIC(ExtraBeeBranchDefinition.ENERGETIC, "energia", false, new Color(0xe835c7), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.REDSTONE.get(1), 0.12f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.DILIGENT, ExtraBeeDefinition.EXCITED, 8);
        }
    },
    ECSTATIC(ExtraBeeBranchDefinition.ENERGETIC, "ecstatica", true, new Color(0xaf35e8), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.REDSTONE.get(1), 0.20f)
                    .addSpecialty(CombItems.Items.IC2ENERGY.get(1), 0.08f).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.POWER.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.EXCITED, ExtraBeeDefinition.ENERGETIC, 8);
        }
    },

    /* FROZEN BRANCH */
    ARTIC(BeeBranchDefinition.FROZEN, "artica", true, new Color(0xade0e0), new Color(0xdaf5f3)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.FROZEN.get(), 0.25f).setTemperature(EnumTemperature.ICY);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.MAXIMUM);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.WINTRY, BeeDefinition.DILIGENT, 10);
        }
    },
    FREEZING(BeeBranchDefinition.FROZEN, "glacia", true, new Color(0x7be3e3), new Color(0xdaf5f3)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.FROZEN.get(), 0.20f)
                    .addSpecialty(CombItems.Items.GLACIAL.get(1), 0.10f).setTemperature(EnumTemperature.ICY);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWER);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.MAXIMUM);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.OCEAN, ExtraBeeDefinition.ARTIC, 10);
        }
    },

    /* SHADOW BRANCH */
    SHADOW(ExtraBeeBranchDefinition.SHADOW, "shadowa", false, new Color(0x595959), new Color(0x333333)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.SHADOW.get(1), 0.05f).setTemperature(EnumTemperature.HELLISH)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.SINISTER, ExtraBeeDefinition.ROCK, 10);
        }
    },
    DARKENED(ExtraBeeBranchDefinition.SHADOW, "darka", true, new Color(0x332e33), new Color(0x333333)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.SHADOW.get(1), 0.10f).setTemperature(EnumTemperature.HELLISH)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.SHADOW, ExtraBeeDefinition.ROCK, 8);
        }
    },
    ABYSS(ExtraBeeBranchDefinition.SHADOW, "abyssba", true, new Color(0x210821), new Color(0x333333)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.SHADOW.get(1), 0.25f).setTemperature(EnumTemperature.HELLISH)
                    .setHumidity(EnumHumidity.ARID).setHasEffect();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.WITHER.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(ExtraBeeDefinition.SHADOW, ExtraBeeDefinition.DARKENED, 8);
        }
    },

    /* PRIMARY BRNCH */
    RED(ExtraBeeBranchDefinition.PRIMARY, "rubra", true, new Color(0xff0000), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.RED.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.FOREST, BeeDefinition.VALIANT, 5);
        }
    },
    YELLOW(ExtraBeeBranchDefinition.PRIMARY, "fulvus", true, new Color(0xffdd00), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.YELLOW.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MEADOWS, BeeDefinition.VALIANT, 5);
        }
    },
    BLUE(ExtraBeeBranchDefinition.PRIMARY, "caeruleus", true, new Color(0x0022ff), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.BLUE.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.VALIANT, ExtraBeeDefinition.WATER, 5);
        }
    },
    GREEN(ExtraBeeBranchDefinition.PRIMARY, "prasinus", true, new Color(0x009900), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.GREEN.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.TROPICAL, BeeDefinition.VALIANT, 5);
        }
    },
    BLACK(ExtraBeeBranchDefinition.PRIMARY, "niger", true, new Color(0x575757), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.BLACK.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.VALIANT, ExtraBeeDefinition.ROCK, 5);
        }
    },
    WHITE(ExtraBeeBranchDefinition.PRIMARY, "albus", true, new Color(0xffffff), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.WHITE.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.WINTRY, BeeDefinition.VALIANT, 5);
        }
    },
    BROWN(ExtraBeeBranchDefinition.PRIMARY, "fuscus", true, new Color(0x5c350f), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.BROWN.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.MARSHY, BeeDefinition.VALIANT, 5);
        }
    },

    /* SECONDARY BRANCH */
    ORANGE(ExtraBeeBranchDefinition.SECONDARY, "flammeus", true, new Color(0xff9d00), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.ORANGE.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(RED, YELLOW, 5);
        }
    },
    CYAN(ExtraBeeBranchDefinition.SECONDARY, "cyana", true, new Color(0x00ffe5), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.CYAN.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(GREEN, BLUE, 5);
        }
    },
    PURPLE(ExtraBeeBranchDefinition.SECONDARY, "purpureus", true, new Color(0xae00ff), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.PURPLE.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(RED, BLUE, 5);
        }
    },
    GRAY(ExtraBeeBranchDefinition.SECONDARY, "ravus", true, new Color(0xbababa), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.GRAY.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BLACK, WHITE, 5);
        }
    },
    LIGHTBLUE(ExtraBeeBranchDefinition.SECONDARY, "aqua", true, new Color(0x009dff), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.LIGHTBLUE.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BLUE, WHITE, 5);
        }
    },
    PINK(ExtraBeeBranchDefinition.SECONDARY, "rosaceus", true, new Color(0xff80df), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.PINK.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(RED, WHITE, 5);
        }
    },
    LIMEGREEN(ExtraBeeBranchDefinition.SECONDARY, "lima", true, new Color(0x00ff08), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.LIMEGREEN.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(GREEN, WHITE, 5);
        }
    },

    /* TERTIARY BRANCH */
    MAGENTA(ExtraBeeBranchDefinition.TERTIARY, "fuchsia", true, new Color(0xff00cc), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.MAGENTA.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(PURPLE, PINK, 5);
        }
    },
    LIGHTGRAY(ExtraBeeBranchDefinition.TERTIARY, "canus", true, new Color(0xc9c9c9), new Color(0x8cff00)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.75f)
                    .addSpecialty(CombItems.Items.LIGHTGRAY.get(1), 0.25f);
        }

        @Override
        protected void registerMutations() {
            registerMutation(GRAY, WHITE, 5);
        }
    },

    /* FESTIVE BRANCH */
    CELEBRATORY(BeeBranchDefinition.FESTIVE, "celeba", true, new Color(0xfa0a6a), new Color(0xd40000)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.setTemperature(EnumTemperature.ICY);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
            AlleleHelper.instance.set(
                    template,
                    EnumBeeChromosome.EFFECT,
                    AlleleHelper.getAllele(ExtraBeesEffect.FIREWORKS.getUID()));
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.AUSTERE, EXCITED, 5);
        }
    },

    /* FTB BRANCH */
    JADED(ExtraBeeBranchDefinition.FTB, "jadeca", true, new Color(0xfa0a6a), new Color(0xdc8aeb)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.HONEY.get(), 0.30f)
                    .addSpecialty(Mods.forestry.stack("pollen"), 0.20f).addSpecialty(CombItems.Items.PURPLE.get(1), 0.15f)
                    .setIsNotCounted().setHasEffect();
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.ENDED, RELIC, 2).restrictPerson("jadedcat");
        }
    },
    CHAD(ExtraBeeBranchDefinition.FTB, "merciboocoup", true, new Color(0x2157DB), new Color(0xD2ABD1)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(new ItemStack(Items.cake, 1), 1f)
                    .addSpecialty(Mods.forestry.stack("pollenFertile"), 0.50f)
                    .addSpecialty(CombItems.Items.MILK.get(1), 0.55f).setIsNotCounted().setHasEffect();
        }

        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, true);
            AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_5);
            AlleleHelper.instance.set(template, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_5);
            AlleleHelper.instance
                    .set(template, EnumBeeChromosome.EFFECT, AlleleHelper.getAllele(ExtraBeesEffect.BIRTHDAY.getUID()));
        }

        @Override
        protected void registerMutations() {
            final ExtraBeeMutation mutation = registerMutation(BeeDefinition.CULTIVATED, CELEBRATORY, 5);
            mutation.restrictDateRange(2, 29, 2, 29);
            mutation.requireDay();
            mutation.restrictPerson(
                    new RequirementPerson().add(
                            name -> "" + EnumChatFormatting.DARK_RED
                                    + EnumChatFormatting.BOLD
                                    + "R"
                                    + EnumChatFormatting.RED
                                    + EnumChatFormatting.BOLD
                                    + "u"
                                    + EnumChatFormatting.GOLD
                                    + EnumChatFormatting.BOLD
                                    + "n"
                                    + EnumChatFormatting.YELLOW
                                    + EnumChatFormatting.BOLD
                                    + "a"
                                    + EnumChatFormatting.DARK_GREEN
                                    + EnumChatFormatting.BOLD
                                    + "k"
                                    + EnumChatFormatting.GREEN
                                    + EnumChatFormatting.BOLD
                                    + "a"
                                    + EnumChatFormatting.AQUA
                                    + EnumChatFormatting.BOLD
                                    + "i",
                            "Runakai").add(
                                    "boubou_19",
                                    "Alastors_Game",
                                    "BlueWeabo",
                                    "Lewis_Saber",
                                    "True_Aurastorm",
                                    "mitchej123",
                                    "minecraft7771",
                                    "_Timbo",
                                    "kuba6000",
                                    "Alrightsc",
                                    "YeetYeetDatBoi",
                                    "DreamMasterXXL",
                                    "Colen",
                                    "OrderedSet"));
        }
    },

    /* AUSTERE BRANCH */
    HAZARDOUS(BeeBranchDefinition.AUSTERE, "infensus", true, new Color(0xb06c28), new Color(0xffdc16)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.Items.SALTPETER.get(1), 0.12f).setTemperature(EnumTemperature.HOT)
                    .setHumidity(EnumHumidity.ARID);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
            AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_2);
            AlleleHelper.instance.set(template, EnumBeeChromosome.EFFECT, AlleleEffect.effectCreeper);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.AUSTERE, DESOLATE, 5);
        }
    },

    /* ALLOY BRANCH */
    // INVAR,
    // BRONZE,
    // BRASS,
    // STEEL,

    /* QUANTUM BRANCH */
    QUANTUM(ExtraBeeBranchDefinition.QUANTUM, "quanta", true, new Color(0x37c5db), new Color(0xd50fdb)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.QUARTZ.get(), 0.25f)
                    .addSpecialty(CombItems.Items.CERTUS.get(1), 0.15f)
                    .addSpecialty(CombItems.Items.ENDERPEARL.get(1), 0.15f);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            AlleleHelper.instance.set(template, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
            AlleleHelper.instance.set(template, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
            AlleleHelper.instance.set(template, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
            AlleleHelper.instance.set(template, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
            AlleleHelper.instance.set(template, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
            AlleleHelper.instance.set(template, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(template, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
            AlleleHelper.instance.set(template, EnumBeeChromosome.EFFECT, AlleleEffect.effectNone);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.SPECTRAL, SPATIAL, 5);
        }
    },
    UNUSUAL(ExtraBeeBranchDefinition.QUANTUM, "daniella", true, new Color(0x59a4ba), new Color(0xbaa2eb)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.QUARTZ.get(), 0.25f).setTemperature(EnumTemperature.COLD);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.SECLUDED, BeeDefinition.ENDED, 5);
        }
    },
    SPATIAL(ExtraBeeBranchDefinition.QUANTUM, "spatia", true, new Color(0x4c1be0), new Color(0xa44ecc)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(CombItems.ForestryCombs.QUARTZ.get(), 0.25f)
                    .addSpecialty(CombItems.Items.CERTUS.get(1), 0.05f).setTemperature(EnumTemperature.COLD);
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.HERMITIC, UNUSUAL, 5);
        }
    },

    /* BOTANIA BRANCH */
    MYSTICAL(ExtraBeeBranchDefinition.BOTANIA, "mystica", true, new Color(0x46a722), new Color(0xffffff)) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            Map<ItemStack, Float> products = BeeDefinition.NOBLE.getGenome().getPrimary().getProductChances();
            for (Map.Entry<ItemStack, Float> entry : products.entrySet()) {
                beeSpecies.addProduct(entry.getKey(), entry.getValue());
            }
        }

        @Override
        protected void registerMutations() {
            registerMutation(BeeDefinition.NOBLE, BeeDefinition.MONASTIC, 5);
        }
    };

    private static final EnumSet<ExtraBeeDefinition> overworldHiveBees = EnumSet.of(ROCK, WATER, BASALT);

    private final IBranchDefinition branch;
    private final IAlleleBeeSpeciesCustom species;

    private IAllele[] template;
    private IBeeGenome genome;

    ExtraBeeDefinition(IBranchDefinition branch, String binomial, boolean dominant, Color primary, Color secondary) {
        String species = toString().toLowerCase(Locale.ENGLISH);
        String uid = "extrabees.species." + species;
        String description = "extrabees.species." + species + ".desc";
        String name = "extrabees.species." + species + ".name";

        this.branch = branch;
        if (branch != null) {
            this.species = BeeManager.beeFactory.createSpecies(
                    uid,
                    dominant,
                    "Binnie",
                    name,
                    description,
                    branch.getBranch(),
                    binomial,
                    primary.getRGB(),
                    secondary.getRGB());
        } else {
            this.species = null;
        }
    }

    protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
        // ignored
    }

    protected void setAlleles(IAllele[] template) {
        // ignored
    }

    protected void registerMutations() {
        // can be found in hive
    }

    protected boolean isNeedRegister() {
        return branch != null;
    }

    public static void initBees() {
        for (ExtraBeeDefinition bee : values()) {
            if (bee.isNeedRegister()) {
                bee.init();
                bee.registerMutations();
            }
        }

        registerMutation(BeeDefinition.FOREST, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MEADOWS, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MODEST, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.TROPICAL, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MARSHY, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.WINTRY, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(ExtraBeeDefinition.ROCK, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);
        registerMutation(ExtraBeeDefinition.BASALT, ExtraBeeDefinition.WATER, BeeDefinition.COMMON, 15);

        registerMutation(BeeDefinition.FOREST, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MEADOWS, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MODEST, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.TROPICAL, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MARSHY, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.WINTRY, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);
        registerMutation(ExtraBeeDefinition.BASALT, ExtraBeeDefinition.ROCK, BeeDefinition.COMMON, 15);

        registerMutation(BeeDefinition.FOREST, ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MEADOWS, ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MODEST, ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.TROPICAL, ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.MARSHY, ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, 15);
        registerMutation(BeeDefinition.WINTRY, ExtraBeeDefinition.BASALT, BeeDefinition.COMMON, 15);

        registerMutation(BeeDefinition.COMMON, ExtraBeeDefinition.WATER, BeeDefinition.CULTIVATED, 12);
        registerMutation(BeeDefinition.COMMON, ExtraBeeDefinition.ROCK, BeeDefinition.CULTIVATED, 12);
        registerMutation(BeeDefinition.COMMON, ExtraBeeDefinition.BASALT, BeeDefinition.CULTIVATED, 12);

        registerMutation(BeeDefinition.CULTIVATED, BASALT, BeeDefinition.SINISTER, 60)
                .restrictBiomeType(BiomeDictionary.Type.NETHER);

        registerMutation(BeeDefinition.SINISTER, BASALT, BeeDefinition.FIENDISH, 40)
                .restrictBiomeType(BiomeDictionary.Type.NETHER);
    }

    private void init() {
        if (!overworldHiveBees.contains(this)) {
            species.setIsSecret();
        }
        setSpeciesProperties(species);

        template = branch.getTemplate();
        AlleleHelper.instance.set(template, EnumBeeChromosome.SPECIES, species);
        setAlleles(template);

        genome = BeeManager.beeRoot.templateAsGenome(template);
        BeeManager.beeRoot.registerTemplate(template);
    }

    protected final ExtraBeeMutation registerMutation(ExtraBeeDefinition parent1, ExtraBeeDefinition parent2,
            int chance) {
        return registerMutation(parent1.species, parent2.species, chance);
    }

    protected final ExtraBeeMutation registerMutation(BeeDefinition parent1, ExtraBeeDefinition parent2, int chance) {
        return registerMutation(parent1.getGenome().getPrimary(), parent2.species, chance);
    }

    protected final ExtraBeeMutation registerMutation(BeeDefinition parent1, BeeDefinition parent2, int chance) {
        return registerMutation(parent1.getGenome().getPrimary(), parent2.getGenome().getPrimary(), chance);
    }

    protected final ExtraBeeMutation registerMutation(IAlleleBeeSpecies parent1, IAlleleBeeSpecies parent2,
            int chance) {
        ExtraBeeMutation mutation = new ExtraBeeMutation(parent1, parent2, getTemplate(), chance);
        BeeManager.beeRoot.registerMutation(mutation);
        return mutation;
    }

    private static ExtraBeeMutation registerMutation(ExtraBeeDefinition parent1, ExtraBeeDefinition parent2,
            BeeDefinition result, int chance) {
        ExtraBeeMutation mutation = new ExtraBeeMutation(
                parent1.species,
                parent2.species,
                result.getTemplate(),
                chance);
        BeeManager.beeRoot.registerMutation(mutation);
        return mutation;
    }

    private static ExtraBeeMutation registerMutation(BeeDefinition parent1, ExtraBeeDefinition parent2,
            BeeDefinition result, int chance) {
        ExtraBeeMutation mutation = new ExtraBeeMutation(
                parent1.getGenome().getPrimary(),
                parent2.species,
                result.getTemplate(),
                chance);
        BeeManager.beeRoot.registerMutation(mutation);
        return mutation;
    }

    @Override
    public final IAllele[] getTemplate() {
        return Arrays.copyOf(template, template.length);
    }

    @Override
    public final IBeeGenome getGenome() {
        return genome;
    }

    @Override
    public final IBee getIndividual() {
        return new Bee(genome);
    }

    @Override
    public final ItemStack getMemberStack(EnumBeeType beeType) {
        IBee bee = getIndividual();
        return BeeManager.beeRoot.getMemberStack(bee, beeType.ordinal());
    }
}
