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
package robaertschi.environmenttech.data.components;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FilledComponent(int filled) {
    public static final Codec<FilledComponent> CODEC = RecordCodecBuilder.create(filledComponentInstance ->
            filledComponentInstance.group(
                    Codec.INT.fieldOf("filled").forGetter(FilledComponent::filled)
            ).apply(filledComponentInstance, FilledComponent::new)
    );
    public static final StreamCodec<ByteBuf, FilledComponent> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, FilledComponent::filled, FilledComponent::new);
}
