package robaertschi.environmenttech.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MODID)
public class ETDatagen {
    @SubscribeEvent()
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeServer(),
                new ETRecipeProvider(output, lookupProvider)
        );

        generator.addProvider(
                event.includeClient(),
                new ETBlockStateProvider(output, existingFileHelper)
        );

        generator.addProvider(
                event.includeClient(),
                new ETItemModelProvider(output, existingFileHelper)
        );
    }
}
