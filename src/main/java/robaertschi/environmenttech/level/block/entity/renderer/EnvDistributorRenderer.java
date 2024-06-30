/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.block.entity.renderer;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

import com.mojang.blaze3d.vertex.PoseStack;

import robaertschi.environmenttech.client.RenderUtils;
import robaertschi.environmenttech.level.block.entity.EnvDistributorBlockEntity;
import robaertschi.environmenttech.level.fluid.ETFluids;

@Slf4j
@ParametersAreNonnullByDefault
public class EnvDistributorRenderer implements BlockEntityRenderer<EnvDistributorBlockEntity> {
    private static final FluidStack ENV_STACK = new FluidStack(ETFluids.ENV_STILL, 1);

    @SuppressWarnings("unused")
    public EnvDistributorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EnvDistributorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        final float pixel = 1f / 16f;

        poseStack.translate(pixel + 0.01f, pixel, pixel + 0.01f);

        if (blockEntity.getEnvStorage().getEnvStored() <= 0) {
            return;
        }

        RenderUtils.renderFluidBox(
                ENV_STACK, poseStack, bufferSource, packedLight, blockEntity.getEnvStorage().getEnvStored(), blockEntity.getEnvStorage().getMaxEnv(),
                new Vector3f(0f, 0f, 0f), new Vector3f(pixel * 14 - 0.02f, pixel * 5, pixel * 14 - 0.02f)
        );
    }

    @Override
    public boolean shouldRender(EnvDistributorBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(EnvDistributorBlockEntity blockEntity) {
        return true;
    }
}
