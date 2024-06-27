/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.data.components;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.component.DataComponentType;

import robaertschi.environmenttech.EnvironmentTech;

public class ETComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(EnvironmentTech.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilledComponent>> FILLED_COMPONENT = DATA_COMPONENTS.registerComponentType("filled_component",
            filledComponentBuilder -> filledComponentBuilder.persistent(FilledComponent.CODEC).networkSynchronized(FilledComponent.STREAM_CODEC)
            );

    public static void init(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
