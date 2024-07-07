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
package xyz.robaertschi.environmenttech.level.fluid;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;

import xyz.robaertschi.environmenttech.ET;


public class ETFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ET.MODID);


    public static final DeferredHolder<Fluid, Fluid> ENV_FLOWING = FLUIDS.register("env_flowing", resourceLocation -> new BaseFlowingFluid.Flowing(ETFluids.ENV_PROPERTIES));
    public static final DeferredHolder<Fluid, Fluid> ENV_STILL = FLUIDS.register("env", resourceLocation -> new BaseFlowingFluid.Source(ETFluids.ENV_PROPERTIES));
    public static final BaseFlowingFluid.Properties ENV_PROPERTIES = new BaseFlowingFluid.Properties(ETFluidTypes.ENV, ENV_STILL, ENV_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2);


    public static void init(IEventBus modEventBus) {
        FLUIDS.register(modEventBus);
    }
}
