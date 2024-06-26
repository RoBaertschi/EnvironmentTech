package robaertschi.environmenttech.data.capabilities;

import net.minecraft.core.Direction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import robaertschi.environmenttech.EnvironmentTech;
import robaertschi.environmenttech.level.block.entity.ETBlockEntities;

public class ETCapabilities {
    public static final BlockCapability<IEnvStorage, EnvType> ENV_STORAGE_BLOCK =
            BlockCapability.create(EnvironmentTech.id("env_storage"),
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
