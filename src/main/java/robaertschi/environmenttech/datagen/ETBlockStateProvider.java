/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen;

import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.minecraft.data.PackOutput;

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
