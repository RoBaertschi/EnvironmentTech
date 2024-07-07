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
package xyz.robaertschi.environmenttech.compat.top;

import java.util.function.Function;

import mcjty.theoneprobe.api.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.EnvironmentTech;
import xyz.robaertschi.environmenttech.client.renderer.EnvStorageRenderer;
import xyz.robaertschi.environmenttech.data.capabilities.ETCapabilities;
import xyz.robaertschi.environmenttech.data.capabilities.EnvType;
import xyz.robaertschi.environmenttech.data.capabilities.IEnvStorage;

public class TopCompat {
    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(TopCompat::sendIMC);
    }

    public static void sendIMC(InterModEnqueueEvent event) {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe probe;

        @Override
        public Void apply(ITheOneProbe theOneProbe) {
            probe = theOneProbe;
            EnvironmentTech.LOGGER.info("Enabled TheOneProbe support");
            theOneProbe.registerProvider(new IProbeInfoProvider() {
                private BlockCapabilityCache<IEnvStorage, EnvType> capCache = null;

                @Override
                public ResourceLocation getID() {
                    return ET.id("default");
                }

                @Override
                public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
                    var blockEntity = level.getBlockEntity(iProbeHitData.getPos());
                    if (blockEntity instanceof TOPInfoProvider topInfoProvider) {
                        topInfoProvider.addProbeInfo(probeMode, iProbeInfo, player, level, blockState, iProbeHitData);
                    }

                    if (!(level instanceof ServerLevel)) return;

                    if (capCache == null || !capCache.pos().equals(iProbeHitData.getPos())) {
                        capCache = BlockCapabilityCache.create(ETCapabilities.ENV_STORAGE_BLOCK, (ServerLevel) level, iProbeHitData.getPos(), EnvType.Normal);
                        // Try other env types
                        if (capCache.getCapability() == null) {
                            capCache = BlockCapabilityCache.create(ETCapabilities.ENV_STORAGE_BLOCK, (ServerLevel) level, iProbeHitData.getPos(), EnvType.Bundled);
                        }
                        if (capCache.getCapability() == null) {
                            capCache = BlockCapabilityCache.create(ETCapabilities.ENV_STORAGE_BLOCK, (ServerLevel) level, iProbeHitData.getPos(), EnvType.Chunk);
                        }
                    }

                    IEnvStorage cap = capCache.getCapability();
                    if (cap != null) {
                        var style = iProbeInfo.defaultProgressStyle()
                                .filledColor(EnvStorageRenderer.from)
                                .alternateFilledColor(EnvStorageRenderer.to)
                                .suffix(" ENV");
                        iProbeInfo.horizontal().progress(cap.getEnvStored(),cap.getMaxEnv(), style);
                    }
                }
            });

            return null;
        }
    }
}
