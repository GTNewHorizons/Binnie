package binnie.extrabees.worldgen;

import static binnie.core.Mods.buildcraftCore;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import binnie.core.IInitializable;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.config.EBConfig;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import buildcraft.api.core.BuildCraftAPI;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.apiculture.genetics.BeeDefinition;

public class ModuleGeneration implements IWorldGenerator, IInitializable {

    @Override
    public void preInit() {
        ExtraBees.materialBeehive = new MaterialBeehive();
        ExtraBees.hive = new BlockExtraBeeHive();
        GameRegistry.registerBlock(ExtraBees.hive, ItemBeehive.class, "hive");
    }

    @Override
    public void init() {
        GameRegistry.registerWorldGenerator(new ModuleGeneration(), 0);
        if (!EBConfig.canQuarryMineHives && buildcraftCore.active()) {
            BuildCraftAPI.softBlocks.add(ExtraBees.hive);
        }
    }

    @Override
    public void postInit() {
        EnumHiveType.WATER.drops.add(new HiveDrop(ExtraBeeDefinition.WATER.getGenome().getPrimary(), 80));
        EnumHiveType.WATER.drops.add(new HiveDrop(BeeDefinition.VALIANT.getGenome().getPrimary(), 3));

        EnumHiveType.ROCK.drops.add(new HiveDrop(ExtraBeeDefinition.ROCK.getGenome().getPrimary(), 80));
        EnumHiveType.ROCK.drops.add(new HiveDrop(BeeDefinition.VALIANT.getGenome().getPrimary(), 3));

        EnumHiveType.NETHER.drops.add(new HiveDrop(ExtraBeeDefinition.BASALT.getGenome().getPrimary(), 80));
        EnumHiveType.NETHER.drops.add(new HiveDrop(BeeDefinition.VALIANT.getGenome().getPrimary(), 3));

        ExtraBees.hive.setHarvestLevel("scoop", 0, 0);
        ExtraBees.hive.setHarvestLevel("scoop", 0, 1);
        ExtraBees.hive.setHarvestLevel("scoop", 0, 2);
        ExtraBees.hive.setHarvestLevel("scoop", 0, 3);
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider) {
        chunkX <<= 4;
        chunkZ <<= 4;

        for (int i = 0; i < EBConfig.waterHiveRate; ++i) {
            int randPosX = chunkX + rand.nextInt(16);
            int randPosY = rand.nextInt(50) + 20;
            int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveWater().generate(world, rand, randPosX, randPosY, randPosZ);
        }

        for (int i = 0; i < EBConfig.rockHiveRate; ++i) {
            int randPosX = chunkX + rand.nextInt(16);
            int randPosY = rand.nextInt(50) + 20;
            int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveRock().generate(world, rand, randPosX, randPosY, randPosZ);
        }

        for (int i = 0; i < EBConfig.netherHiveRate; ++i) {
            int randPosX = chunkX + rand.nextInt(16);
            int randPosY = rand.nextInt(50) + 20;
            int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveNether().generate(world, rand, randPosX, randPosY, randPosZ);
        }
    }
}
