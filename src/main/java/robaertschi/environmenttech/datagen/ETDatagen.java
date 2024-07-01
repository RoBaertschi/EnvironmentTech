/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.datagen;

import java.util.concurrent.CompletableFuture;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider;
import com.klikli_dev.modonomicon.datagen.EnUsProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import robaertschi.environmenttech.datagen.book.DocsBook;

import static robaertschi.environmenttech.ET.MODID;

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

        var enUsCache = new LanguageProviderCache("en_us");
        generator.addProvider(event.includeServer(), NeoBookProvider.of(event,
                new DocsBook(MODID, enUsCache)));

        generator.addProvider(event.includeClient(), new EnUsProvider(generator.getPackOutput(), enUsCache));
    }
}
