package robaertschi.environmenttech.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import robaertschi.environmenttech.client.screen.EnvCollectorScreen;
import robaertschi.environmenttech.menu.ETMenus;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ETClient {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerMenu(RegisterMenuScreensEvent event) {
        event.register(ETMenus.ENV_COLLECTOR_MENU.get(), EnvCollectorScreen::new);
    }
}
