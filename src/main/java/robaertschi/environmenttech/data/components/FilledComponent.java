/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
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
