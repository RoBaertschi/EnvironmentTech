/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.client.particle;

import lombok.AllArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

import robaertschi.environmenttech.level.particle.EnvParticle;

@AllArgsConstructor()
public class EnvParticleProvider implements ParticleProvider<SimpleParticleType> {
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
