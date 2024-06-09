package robaertschi.environmenttech.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import robaertschi.environmenttech.level.block.ETBlocks;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETBlockStateProvider extends BlockStateProvider {
    public ETBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        registerEnvCollector();
        horizontalBlock(ETBlocks.ENV_COLLECTOR_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/env_collector")));
    }
}
