package robaertschi.environmenttech.level.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import robaertschi.environmenttech.data.attachments.ETAttachments;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class EnvDetectorItem extends Item {
    public static final int STEPS = 9;

    public EnvDetectorItem(Properties properties) {
        super(properties);
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("item.environmenttech.env_detector.tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

        var chunk = pLevel.getChunk(pPlayer.blockPosition());

        if (chunk.hasData(ETAttachments.ENV)) {
            var env = chunk.getData(ETAttachments.ENV);
            long steps = env / 2;
            if (steps > STEPS) {
                steps = STEPS;
            }

            pPlayer.getItemInHand(pUsedHand).set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData((int)steps));

//            pPlayer.displayClientMessage(Component.literal("Chunk has " + chunk.getData(ETAttachments.ENV) + " ENV").withStyle(ChatFormatting.GREEN), true);
        } else {
            pPlayer.displayClientMessage(Component.literal("Warning: This chunk does not have the ENV attachment, please report this to the Author of the Mod.").withStyle(ChatFormatting.YELLOW), true);
        }

        if (!pPlayer.isCreative()) {
            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pLevel.getRandom(), pPlayer, () -> pPlayer.getItemInHand(pUsedHand).setCount(0));
        }

        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
