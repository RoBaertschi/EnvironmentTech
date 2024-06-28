/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.block.entity;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import robaertschi.environmenttech.level.block.ETBlocks;

import static robaertschi.environmenttech.ET.MODID;

@SuppressWarnings("DataFlowIssue")
public class ETBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnvCollectorBlockEntity>> ENV_COLLECTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("env_collector",
                    () -> BlockEntityType.Builder.of(EnvCollectorBlockEntity::new,
                            ETBlocks.ENV_COLLECTOR_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnvDistributorBlockEntity>> ENV_DISTRIBUTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("env_distributor",
                    () -> BlockEntityType.Builder.of(EnvDistributorBlockEntity::new, ETBlocks.ENV_DISTRIBUTOR_BLOCK.get()).build(null));

    public static void init(IEventBus iEventBus) {
        BLOCK_ENTITIES.register(iEventBus);
    }
}
