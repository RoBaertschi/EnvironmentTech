package robaertschi.environmenttech.client.particle;

import lombok.AllArgsConstructor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import robaertschi.environmenttech.level.particle.EnvParticle;

@AllArgsConstructor()
public class EnvParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final double SPEED_FACTOR = 0.25;
    private final SpriteSet spriteSet;


    @Nullable
    @Override
    public Particle createParticle(
            @NotNull SimpleParticleType pType,
            @NotNull ClientLevel pLevel,
            double pX,
            double pY,
            double pZ,
            double pXSpeed,
            double pYSpeed,
            double pZSpeed) {
        return new EnvParticle(pLevel, pX, pY, pZ, spriteSet);
    }
}
