package robaertschi.environmenttech.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.level.block.ETBlocks;
import robaertschi.environmenttech.level.item.ETItems;
import robaertschi.environmenttech.level.item.EnvDetectorItem;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETItemModelProvider extends ItemModelProvider {
    public ETItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ETBlocks.ENV_COLLECTOR_BLOCK.getId().getPath(), modLoc("block/" + "env_collector"));
//        withExistingParent(ETBlocks.ENV_DISTRIBUTOR_BLOCK.getId().getPath(), modLoc("block/" + "env_distributor"));
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
            builder.override().predicate(EnvironmentTech.id("filled"), i).model(sub_item_builder);
        }
    }

}
