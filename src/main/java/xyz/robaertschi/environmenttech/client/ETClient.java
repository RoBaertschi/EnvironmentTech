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
package xyz.robaertschi.environmenttech.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;

import net.minecraft.client.renderer.item.ItemProperties;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.client.particle.EnvParticleProvider;
import xyz.robaertschi.environmenttech.client.screen.EnvCollectorScreen;
import xyz.robaertschi.environmenttech.data.components.ETComponents;
import xyz.robaertschi.environmenttech.data.components.FilledComponent;
import xyz.robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import xyz.robaertschi.environmenttech.level.block.entity.renderer.EnvCollectorRenderer;
import xyz.robaertschi.environmenttech.level.block.entity.renderer.EnvDistributorRenderer;
import xyz.robaertschi.environmenttech.level.item.ETItems;
import xyz.robaertschi.environmenttech.level.particle.ETParticles;
import xyz.robaertschi.environmenttech.menu.ETMenus;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = ET.MODID, value = Dist.CLIENT)
public class ETClient {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
                ETItems.ENV_DETECTOR_ITEM.get(),
                ET.id("filled"),
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
        event.registerBlockEntityRenderer(ETBlockEntities.ENV_DISTRIBUTOR_BLOCK_ENTITY.get(), EnvDistributorRenderer::new);
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
