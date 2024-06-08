package robaertschi.environmenttech.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import robaertschi.environmenttech.level.block.ETBlocks;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETItemModelProvider extends ItemModelProvider {
    public ETItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ETBlocks.ENV_COLLECTOR_BLOCK.getId().getPath(), modLoc("block/" + "env_collector"));
    }

}
