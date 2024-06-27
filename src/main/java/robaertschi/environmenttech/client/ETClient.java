/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;

import net.minecraft.client.renderer.item.ItemProperties;

import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.client.particle.EnvParticleProvider;
import robaertschi.environmenttech.client.screen.EnvCollectorScreen;
import robaertschi.environmenttech.data.components.ETComponents;
import robaertschi.environmenttech.data.components.FilledComponent;
import robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import robaertschi.environmenttech.level.block.entity.renderer.EnvCollectorRenderer;
import robaertschi.environmenttech.level.item.ETItems;
import robaertschi.environmenttech.level.particle.ETParticles;
import robaertschi.environmenttech.menu.ETMenus;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ETClient {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
                ETItems.ENV_DETECTOR_ITEM.get(),
                EnvironmentTech.id("filled"),
                (pStack, pLevel, pEntity, pSeed) ->  pStack.getOrDefault(ETComponents.FILLED_COMPONENT, new FilledComponent(0)).filled()
        ));
    }

    @SubscribeEvent
    public static void registerMenu(RegisterMenuScreensEvent event) {
        event.register(ETMenus.ENV_COLLECTOR_MENU.get(), EnvCollectorScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(), EnvCollectorRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ETParticles.ENV_PARTICLE.get(), EnvParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
    }

//    @EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
//    public static class Events {
//
//    }

}
