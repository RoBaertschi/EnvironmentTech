package robaertschi.environmenttech.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<EnvCollectorMenu>> ENV_COLLECTOR_MENU = MENUS.register("env_collector", () ->
            IMenuTypeExtension.create((windowId, inv, data) -> new EnvCollectorMenu(windowId, inv.player, data)));

    public static void init(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
