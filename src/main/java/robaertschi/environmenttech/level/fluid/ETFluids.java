/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.fluid;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;

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
