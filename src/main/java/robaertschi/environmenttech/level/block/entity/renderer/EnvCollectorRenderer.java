/*
 *  EnvironmentTech  Copyright (C) 2024 Robin B??rtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.block.entity.renderer;

import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import robaertschi.environmenttech.level.block.EnvCollectorBlock;
import robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity;
import robaertschi.environmenttech.level.particle.ETParticles;

@ParametersAreNonnullByDefault
public class EnvCollectorRenderer implements BlockEntityRenderer<EnvCollectorBlockEntity> {
    @SuppressWarnings("unused")
    public EnvCollectorRenderer(BlockEntityRendererProvider.Context context) {}
    public int spawnParticle = 0;

    @Override
    public void render(EnvCollectorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack inputItem = pBlockEntity.getInputItem();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.125, 0.5);
        pPoseStack.scale(0.35f, 0.35f, 0.35f);
        finishItemRender(pBlockEntity, pPoseStack, pBuffer, inputItem, itemRenderer);

        ItemStack outputItem = pBlockEntity.getOutputItem();
        pPoseStack.pushPose();
        Vec3i vec = pBlockEntity.getBlockState().getValue(EnvCollectorBlock.FACING).getNormal();
        Vec3 vecDouble = new Vec3(vec.getX(), vec.getY(), vec.getZ());
        vecDouble = vecDouble.multiply(new Vec3(0.3, 0.3, 0.3));
        vecDouble = vecDouble.add(0.5, 0.125, 0.5);
        pPoseStack.translate(vecDouble.x, vecDouble.y, vecDouble.z);
        pPoseStack.scale(0.3f, 0.3f, 0.3f);
        finishItemRender(pBlockEntity, pPoseStack, pBuffer, outputItem, itemRenderer);

        var pos = pBlockEntity.getBlockPos();

        //level.sendParticles(ParticleTypes.GLOW, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 1, 0, 0, 0, -1);


        if (pBlockEntity.getProgress() > 0) {
            if (spawnParticle <= 0) {
                Objects.requireNonNull(pBlockEntity.getLevel()).addParticle(ETParticles.ENV_PARTICLE.get(), pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, 0, -10, 0);
                spawnParticle = 30;
            } else {
                spawnParticle--;
            }
        }
    }

    private void finishItemRender(EnvCollectorBlockEntity pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, ItemStack itemStack1, ItemRenderer itemRenderer) {
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(EnvCollectorBlock.FACING).toYRot()));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(itemStack1, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
