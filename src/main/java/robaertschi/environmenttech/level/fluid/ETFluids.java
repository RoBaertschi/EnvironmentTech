package robaertschi.environmenttech.level.fluid;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static robaertschi.environmenttech.ET.MODID;

public class ETFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, MODID);


    public static final DeferredHolder<Fluid, Fluid> ENV_FLOWING = FLUIDS.register("env_flowing", resourceLocation -> new BaseFlowingFluid.Flowing(ETFluids.ENV_PROPERTIES));
    public static final DeferredHolder<Fluid, Fluid> ENV_STILL = FLUIDS.register("env", resourceLocation -> new BaseFlowingFluid.Source(ETFluids.ENV_PROPERTIES));
    public static final BaseFlowingFluid.Properties ENV_PROPERTIES = new BaseFlowingFluid.Properties(ETFluidTypes.ENV, ENV_STILL, ENV_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2);


    public static void init(IEventBus modEventBus) {
        FLUIDS.register(modEventBus);
    }
}
