/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level;

import lombok.extern.slf4j.Slf4j;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

import robaertschi.environmenttech.Config;
import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.data.attachments.ETAttachments;

@EventBusSubscriber(modid = EnvironmentTech.MODID)
@Slf4j(topic = "EnvironmentTech/ChunkData")
public class ETChunkEvents {
    @SubscribeEvent()
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getLevel().isClientSide()) return;
        if (event.isNewChunk() && event.getChunk() instanceof LevelChunk levelChunk && !event.getChunk().hasData(ETAttachments.ENV)) {
            if (levelChunk.getPersistedStatus() != ChunkStatus.FULL) {
                return;
            }
            int random = event.getLevel().getRandom().nextIntBetweenInclusive(Config.minEnvForNewChunk, Config.maxEnvForNewChunk);
            log.debug("New Chunk at pos {}, set random to {}", event.getChunk().getPos(), random);
            event.getChunk().setData(ETAttachments.ENV, (long)random);
        } else if (!event.getChunk().hasData(ETAttachments.ENV)) {
            int random = event.getLevel().getRandom().nextIntBetweenInclusive(Config.minEnvForNewChunk, Config.maxEnvForNewChunk);
            log.debug("Chunk without data at pos {}, set random to {}",event.getChunk().getPos(), random);
            event.getChunk().setData(ETAttachments.ENV, (long)random);
        }
    }
}
