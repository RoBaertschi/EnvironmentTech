package robaertschi.environmenttech.level;

import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import robaertschi.environmenttech.Config;
import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.data.attachments.ETAttachments;

@EventBusSubscriber(modid = EnvironmentTech.MODID)
public class ETChunkEvents {
    @SubscribeEvent()
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getLevel().isClientSide()) return;
        if (event.isNewChunk() && event.getChunk() instanceof LevelChunk levelChunk && !event.getChunk().hasData(ETAttachments.ENV)) {
            if (levelChunk.getStatus() != ChunkStatus.FULL) {
                return;
            }
            int random = event.getLevel().getRandom().nextIntBetweenInclusive(Config.minEnvForNewChunk, Config.maxEnvForNewChunk);
            EnvironmentTech.LOGGER.debug("New Chunk, set random to {}", random);
            event.getChunk().setData(ETAttachments.ENV, random);
        } else if (!event.getChunk().hasData(ETAttachments.ENV)) {
            int random = event.getLevel().getRandom().nextIntBetweenInclusive(Config.minEnvForNewChunk, Config.maxEnvForNewChunk);
            EnvironmentTech.LOGGER.debug("Chunk without data, set random to {}", random);
            event.getChunk().setData(ETAttachments.ENV, random);
        }
    }
}
