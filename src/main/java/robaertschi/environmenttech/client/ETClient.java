package robaertschi.environmenttech.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.client.event.*;
import org.lwjgl.glfw.GLFW;
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

    public static final Lazy<KeyMapping> OPEN_DEBUG_MENU = Lazy.of(() -> new KeyMapping(
            "key.environmenttech.open_debug_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_U,
            "key.categories.environmenttech"
    ));

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
        if (EnvironmentTech.DEBUG) {
            event.register(OPEN_DEBUG_MENU.get());
        }
    }
}
