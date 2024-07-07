/*
 *  EnvironmentTech MC Mod
    Copyright (C) 2024 Robin Bärtschi and Contributors

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, by version 3 of the License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package xyz.robaertschi.environmenttech.client.particle;

import lombok.AllArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

import xyz.robaertschi.environmenttech.level.particle.EnvParticle;

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
