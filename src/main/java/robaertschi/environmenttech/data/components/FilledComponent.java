package robaertschi.environmenttech.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FilledComponent(int filled) {
    public static final Codec<FilledComponent> CODEC = RecordCodecBuilder.create(filledComponentInstance ->
            filledComponentInstance.group(
                    Codec.INT.fieldOf("filled").forGetter(FilledComponent::filled)
            ).apply(filledComponentInstance, FilledComponent::new)
    );
    public static final StreamCodec<ByteBuf, FilledComponent> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, FilledComponent::filled, FilledComponent::new);
}
