/*
 *  EnvironmentTech MC Mod
    Copyright (C) 2024 Robin Bärtschi and Contributors

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, by version 3 of the License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package xyz.robaertschi.environmenttech.datagen;

import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.minecraft.data.PackOutput;

import xyz.robaertschi.environmenttech.level.block.ETBlocks;

import static xyz.robaertschi.environmenttech.ET.MODID;

public class ETBlockStateProvider extends BlockStateProvider {
    public ETBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        registerEnvCollector();
        horizontalBlock(ETBlocks.ENV_COLLECTOR_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/env_collector")));
        horizontalBlock(ETBlocks.ENV_DISTRIBUTOR_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/env_distributor")));
    }
}
