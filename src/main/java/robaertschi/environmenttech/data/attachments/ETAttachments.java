package robaertschi.environmenttech.data.attachments;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import robaertschi.environmenttech.EnvironmentTech;

import java.util.function.Supplier;

public class ETAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EnvironmentTech.MODID);

    public static final Supplier<AttachmentType<Integer>> ENV = ATTACHMENT_TYPES.register(
            "env", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static void init(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
