package robaertschi.environmenttech.data.components;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import robaertschi.environmenttech.EnvironmentTech;

public class ETComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(EnvironmentTech.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilledComponent>> FILLED_COMPONENT = DATA_COMPONENTS.registerComponentType("filled_component",
            filledComponentBuilder -> filledComponentBuilder.persistent(FilledComponent.CODEC).networkSynchronized(FilledComponent.STREAM_CODEC)
            );
}
