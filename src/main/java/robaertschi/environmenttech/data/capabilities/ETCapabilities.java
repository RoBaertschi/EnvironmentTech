/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.data.capabilities;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import net.minecraft.core.Direction;

import robaertschi.environmenttech.ET;
import robaertschi.environmenttech.level.block.entity.ETBlockEntities;

public class ETCapabilities {
    public static final BlockCapability<IEnvStorage, EnvType> ENV_STORAGE_BLOCK =
            BlockCapability.create(ET.id("env_storage"),
                    IEnvStorage.class,
                    EnvType.class
                    );

    public static void init(IEventBus iEventBus) {
        iEventBus.addListener(ETCapabilities::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(),
                (object, context) -> {
                    if (context == null) {
                        return object.getInventory().get();
                    }
                    if (context == Direction.DOWN) {
                        return object.getOutputItemHandler().get();
                    }
                    return object.getInputItemHandler().get();
                }
        );

        event.registerBlockEntity(
                ENV_STORAGE_BLOCK,
                ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(),
                (object, context) -> {
                    if (context == EnvType.Chunk) return object.getEnvStorage();
                    return null;
                }
        );

        event.registerBlockEntity(
                ENV_STORAGE_BLOCK,
                ETBlockEntities.ENV_DISTRIBUTOR_BLOCK_ENTITY.get(),
                (object, context) -> {
                    if (context == EnvType.Chunk) return object.getEnvStorage();
                    return null;
                }
        );
    }

}
