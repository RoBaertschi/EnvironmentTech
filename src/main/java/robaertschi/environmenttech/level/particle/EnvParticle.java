package robaertschi.environmenttech.level.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class EnvParticle extends TextureSheetParticle implements ParticleOptions {
    private final SpriteSet spriteSet;

    public EnvParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ);
        this.friction = 0.8f;
        this.spriteSet = spriteSet;
//        this.gravity = 0;
        this.gravity = 0.025f;
        lifetime = 50;
        this.quadSize *= 0.35f;
        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(spriteSet);
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ETParticles.ENV_PARTICLE.get();
    }
}
