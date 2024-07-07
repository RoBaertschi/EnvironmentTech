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

import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.level.block.ETBlocks;
import xyz.robaertschi.environmenttech.level.item.ETItems;
import xyz.robaertschi.environmenttech.level.item.EnvDetectorItem;

import static xyz.robaertschi.environmenttech.ET.MODID;

public class ETItemModelProvider extends ItemModelProvider {
    public ETItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ETBlocks.ENV_COLLECTOR_BLOCK.getId().getPath(), modLoc("block/" + "env_collector"));
        withExistingParent(ETBlocks.ENV_DISTRIBUTOR_BLOCK.getId().getPath(), modLoc("block/" + "env_distributor"));
        basicItem(ETItems.ENVIRONMENTAL_ESSENCE_ITEM.get());
        basicItem(ETItems.GLASS_TANK.get());
        registerEnvDetector();
    }

    private void registerEnvDetector() {

        ItemModelBuilder builder = getBuilder("env_detector");
        builder.parent(new ModelFile.ExistingModelFile(ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated"), existingFileHelper));

        for (int i = 0; i < EnvDetectorItem.STEPS; i++) {
            ItemModelBuilder sub_item_builder = getBuilder("env_detector_" + i);
            sub_item_builder.parent(new ModelFile.ExistingModelFile(ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated"), existingFileHelper));
            sub_item_builder.texture("layer0", modLoc("item/env_detector_" + i));
            builder.override().predicate(ET.id("filled"), i).model(sub_item_builder);
        }
    }

}
