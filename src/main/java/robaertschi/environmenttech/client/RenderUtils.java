package robaertschi.environmenttech.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This file is from <a href="https://github.com/DaRealTurtyWurty/DynamicFluidTanks/blob/forge/src/main/java/dev/turtywurty/dynamicfluidtanks/client/renderer/FluidTankBERenderer.java">here</a>.
 * So all credits go to TurtyWurty. The code is under the LGPL-3.0, the conformation you can see <a href="https://github.com/DaRealTurtyWurty/DynamicFluidTanks/blob/forge/gradle.properties">here</a> on the mod_license line.
 * But the repo does not contain a LICENSE file, so it is not clear.
 * @author TurtyWurty
 */
public class RenderUtils {

    public static void vertex(VertexConsumer consumer,
                              PoseStack stack,
                              float x, float y, float z,
                              float u, float v,
                              int packedLight,
                              int color) {
        consumer.addVertex(stack.last().pose(), x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(1, 0, 0);
    }

    public static void quad(VertexConsumer consumer,
                                PoseStack stack,
                                int packedLight, int color,
                                float x0, float y0, float z0,
                                float x1, float y1, float z1,
                                float u0, float v0, float u1, float v1
    ) {
        vertex(consumer, stack, x0, y0, z0, u0, v0, packedLight, color);
        vertex(consumer, stack, x0, y1, z1, u0, v1, packedLight, color);
        vertex(consumer, stack, x1, y1, z1, u1, v1, packedLight, color);
        vertex(consumer, stack, x1, y0, z0, u1, v0, packedLight, color);
    }


    public static void quadReversed(VertexConsumer consumer,
                            PoseStack stack,
                            int packedLight, int color,
                            float x0, float y0, float z0,
                            float x1, float y1, float z1,
                            float u0, float v0, float u1, float v1
    ) {
        vertex(consumer, stack, x0, y0, z0, u0, v0, packedLight, color);
        vertex(consumer, stack, x1, y0, z1, u1, v0, packedLight, color);
        vertex(consumer, stack, x1, y1, z1, u1, v1, packedLight, color);
        vertex(consumer, stack, x0, y1, z0, u0, v1, packedLight, color);
    }

    public static void cuboid(
            VertexConsumer consumer,
            PoseStack stack,
            Vector3f start, Vector3f end,
            TextureAtlasSprite sprite,
            int packedLight, int color

    ) {

        float width = end.x() - start.x();
        float height = end.y() - start.y();
        float depth = end.z() - start.z();

        float u0 = sprite.getU0();
        float v0 = sprite.getV0();
        float u1 = sprite.getU1();
        float v1 = sprite.getV1();

        stack.pushPose();
//        log.info("x: {}, y: {}, z: {}", -relativeStart.x(), -relativeStart.y(), -relativeStart.z());
//        stack.translate(-relativeStart.x(), -relativeStart.y(), -relativeStart.z());
//        stack.translate(relativeStart.x(), relativeStart.y(), relativeStart.z());

//        System.out.println("Width " + width + " height " + height + " depth " + depth);
//        System.out.println("start " + start + " end " + end + " relativeStart " + relativeStart);

        // Front
        quad(consumer, stack, packedLight, color, 0, 0, 0, width, height, 0, u0, v0, u1, v1);

        // Back
        quad(consumer, stack, packedLight, color, width, 0, depth, 0, height, depth, u0, v0, u1, v1);

        // Top
        quad(consumer, stack, packedLight, color, 0, height, 0, width, height, depth, u0, v0, u1, v1);

        // Bottom
        quad(consumer, stack, packedLight, color, 0, 0, depth, width, 0, 0, u0, v0, u1, v1);

        // Right
        quadReversed(consumer, stack, packedLight, color, 0, 0, 0, 0, height, depth, u0, v0, u1, v1);

        // Left
        quadReversed(consumer, stack, packedLight, color, width, 0, depth, width, height, 0, u0, v0, u1, v1);

        stack.popPose();
    }

    @ParametersAreNonnullByDefault
    public static void renderFluidBox(PoseStack poseStack,
                                      MultiBufferSource bufferSource,
                                      int packedLight,
                                      RenderType renderType,
                                      ResourceLocation stillTexture,
                                      int tintColor,
                                      long amount,
                                      long capacity,
                                      Vector3f start,
                                      Vector3f end
    ) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        VertexConsumer consumer = bufferSource.getBuffer(renderType);
        float percent = ((float) amount / (float) capacity);
        float newY = start.y() + (percent * (end.y() - start.y()));
        Vector3f newEnd = new Vector3f(end.x(), newY, end.z());

        cuboid(consumer, poseStack, start, newEnd, sprite, packedLight, tintColor);
    }

    public static void renderFluidBox(FluidStack fluidStack,
                                      PoseStack stack,
                                      MultiBufferSource bufferSource,
                                      int packedLight,
                                      long amount,
                                      long capacity,
                                      Vector3f start,
                                      Vector3f end
                                      ) {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = extensions.getStillTexture();
        int tintColor = extensions.getTintColor(fluidStack);
        RenderType renderType = ItemBlockRenderTypes.getRenderLayer(fluidStack.getFluid().defaultFluidState());
        renderFluidBox(
                stack, bufferSource, packedLight, renderType, stillTexture, tintColor, amount, capacity, start, end
        );
    }
}
