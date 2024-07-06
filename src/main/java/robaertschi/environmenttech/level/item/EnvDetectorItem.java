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
package robaertschi.environmenttech.level.item;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.components.ETComponents;
import robaertschi.environmenttech.data.components.FilledComponent;

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

            pPlayer.getItemInHand(pUsedHand).set(ETComponents.FILLED_COMPONENT.get(), new FilledComponent((int)steps));

//            pPlayer.displayClientMessage(Component.literal("Chunk has " + chunk.getData(ETAttachments.ENV) + " ENV").withStyle(ChatFormatting.GREEN), true);
        } else {
            pPlayer.displayClientMessage(Component.literal("Warning: This chunk does not have the ENV attachment, please report this to the Author of the Mod.").withStyle(ChatFormatting.YELLOW), true);
        }

        if (!pPlayer.isCreative()) {
            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, (ServerLevel) pLevel, (ServerPlayer) pPlayer, (item) -> pPlayer.getItemInHand(pUsedHand).setCount(0));
        }

        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
