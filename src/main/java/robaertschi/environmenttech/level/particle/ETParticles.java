package robaertschi.environmenttech.level.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class ETParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ENV_PARTICLE = PARTICLES.register("env_particle",
            () -> new SimpleParticleType(false)
    );

    public static void init(IEventBus modEventBus) {
        PARTICLES.register(modEventBus);
    }
}
