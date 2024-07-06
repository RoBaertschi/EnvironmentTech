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
package robaertschi.environmenttech.level;

import lombok.extern.slf4j.Slf4j;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

import robaertschi.environmenttech.Config;
import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.data.attachments.ETAttachments;

@EventBusSubscriber(modid = ET.MODID)
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
