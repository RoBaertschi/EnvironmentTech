package robaertschi.environmenttech.level.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import robaertschi.environmenttech.level.block.entity.EnvDistributorBlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EnvDistributorRenderer implements BlockEntityRenderer<EnvDistributorBlockEntity> {
    @SuppressWarnings("unused")
    public EnvDistributorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(EnvDistributorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0, 5, 0);




        poseStack.popPose();
    }
}
