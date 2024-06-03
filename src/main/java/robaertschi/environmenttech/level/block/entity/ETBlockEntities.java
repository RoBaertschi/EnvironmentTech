package robaertschi.environmenttech.level.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import robaertschi.environmenttech.level.block.ETBlocks;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnvCollectorBlockEntity>> ENV_COLLECTOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("env_collector",
                    () -> BlockEntityType.Builder.of(EnvCollectorBlockEntity::new,
                            ETBlocks.ENV_COLLECTOR_BLOCK.get()).build(null));

    public static void init(IEventBus iEventBus) {
        BLOCK_ENTITIES.register(iEventBus);
    }
}
