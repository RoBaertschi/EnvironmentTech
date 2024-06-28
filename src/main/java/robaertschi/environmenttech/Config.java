/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import static robaertschi.environmenttech.ET.MODID;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue MAX_ENV_FOR_NEW_CHUNK = BUILDER
            .comment("How much is the maximum of allowed ENV in a chunk.")
            .defineInRange("maxEnvForNewChunk", 20, 0, 1024);

    private static final ModConfigSpec.IntValue MIN_ENV_FOR_NEW_CHUNK = BUILDER
            .comment("How much is the minimum of allowed ENV in a chunk.")
            .defineInRange("minEnvForNewChunk", 0, 0, 1024);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static int maxEnvForNewChunk;
    public static int minEnvForNewChunk;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

        maxEnvForNewChunk = MAX_ENV_FOR_NEW_CHUNK.get();
        minEnvForNewChunk = MIN_ENV_FOR_NEW_CHUNK.get();
    }
}
