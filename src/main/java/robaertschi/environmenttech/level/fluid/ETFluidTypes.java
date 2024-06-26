/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.fluid;

import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;

import robaertschi.environmenttech.client.renderer.EnvStorageRenderer;

import static robaertschi.environmenttech.ET.MODID;

public class ETFluidTypes {

    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.withDefaultNamespace("block/water_flow");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MODID);

    public static final DeferredHolder<FluidType, FluidType> ENV = FLUID_TYPES.register("env",
            resourceLocation -> new FluidType(
                    FluidType.Properties.create().density(15).viscosity(5)
            ) {
                @Override
                @MethodsReturnNonnullByDefault
                @ParametersAreNonnullByDefault
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public int getTintColor() {
                            return EnvStorageRenderer.to;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return WATER_FLOWING_RL;
                        }

                        @Override
                        public ResourceLocation getStillTexture() {
                            return WATER_STILL_RL;
                        }
                    });
                }
            }
    );

    public static void init(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
    }
}
