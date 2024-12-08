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
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.client.renderer.EnvStorageRenderer;


public class ETFluidTypes {

    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.withDefaultNamespace("block/water_flow");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ET.MODID);

    public static final DeferredHolder<FluidType, FluidType> ENV = FLUID_TYPES.register("env",
            resourceLocation -> new FluidType(
                    FluidType.Properties.create().density(15).viscosity(5)
            )
    );

    public static void registerClientExtensionsEvent(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return EnvStorageRenderer.to;
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return WATER_FLOWING_RL;
            }

            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return WATER_STILL_RL;
            }
        }, ENV);
    }

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ETFluidTypes::registerClientExtensionsEvent);
        FLUID_TYPES.register(modEventBus);
    }
}
