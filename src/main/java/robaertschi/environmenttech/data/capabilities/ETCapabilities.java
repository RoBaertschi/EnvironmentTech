package robaertschi.environmenttech.data.capabilities;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import robaertschi.environmenttech.EnvironmentTech;

public class ETCapabilities {
    public static final BlockCapability<IEnvStorage, EnvCapabilityContext> ENV_STORAGE_BLOCK =
            BlockCapability.create(EnvironmentTech.id("env_storage"),
                    IEnvStorage.class,
                    EnvCapabilityContext.class
                    );

    public static void init(IEventBus iEventBus) {
        iEventBus.addListener(ETCapabilities::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }
}
