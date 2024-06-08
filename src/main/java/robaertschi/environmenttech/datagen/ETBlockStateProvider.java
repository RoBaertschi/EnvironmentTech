package robaertschi.environmenttech.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
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

    private void registerEnvCollector() {
        var envCollector = ETBlocks.ENV_COLLECTOR_BLOCK;
        String path = "env_collector";

        BlockModelBuilder base = models().getBuilder("block/" + path + "_main");
        base.parent(models().getExistingFile(mcLoc("cube")));

        base.element()
                .from(0f, 0f, 0f)
                .to(16f, 16f, 16f)
                .allFaces((direction, faceBuilder) -> faceBuilder.texture("#txt"))
                .end();

        base.texture("txt", modLoc("block/env_collector_main"));
        base.texture("particle", modLoc("block/env_collector_main"));

        base.renderType("solid");

        createEnvCollectorModel(envCollector.get(), path, base);
    }

    private void createEnvCollectorModel(Block block, String path, BlockModelBuilder frame) {
        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        bld.part().modelFile(frame).addModel().end();
    }
}
