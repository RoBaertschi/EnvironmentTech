package robaertschi.environmenttech.level.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import robaertschi.environmenttech.data.attachments.ETAttachments;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EnvDetectorItem extends Item {
    public EnvDetectorItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

        var chunk = pLevel.getChunk(pPlayer.blockPosition());

        if (chunk.hasData(ETAttachments.ENV)) {
            pPlayer.displayClientMessage(Component.literal("Chunk has " + chunk.getData(ETAttachments.ENV) + " ENV").withStyle(ChatFormatting.GREEN), true);
        } else {
            pPlayer.displayClientMessage(Component.literal("Warning: This chunk does not have the ENV attachment, please report this to the Author of the Mod.").withStyle(ChatFormatting.YELLOW), true);
        }

        if (!pPlayer.isCreative()) {
            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pLevel.getRandom(), pPlayer, () -> {
                pPlayer.getItemInHand(pUsedHand).setCount(0);
            });
        }

        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
