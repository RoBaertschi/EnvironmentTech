package robaertschi.environmenttech.compat.top;

import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.client.renderer.EnvStorageRenderer;
import robaertschi.environmenttech.data.capabilities.ETCapabilities;
import robaertschi.environmenttech.data.capabilities.EnvType;
import robaertschi.environmenttech.data.capabilities.IEnvStorage;

import java.util.function.Function;

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
                    return EnvironmentTech.id("default");
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
