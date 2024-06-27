/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.menu;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<EnvCollectorMenu>> ENV_COLLECTOR_MENU = MENUS.register("env_collector", () ->
            IMenuTypeExtension.create((windowId, inv, data) -> new EnvCollectorMenu(windowId, inv.player, data)));

    public static void init(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
