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
package xyz.robaertschi.environmenttech.level.block;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.mojang.serialization.MapCodec;

import xyz.robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import xyz.robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity;

@ParametersAreNonnullByDefault()
@Slf4j
public class EnvCollectorBlock extends SimpleBlockWithEntity<EnvCollectorBlockEntity> {
    public static final MapCodec<EnvCollectorBlock> CODEC = simpleCodec(EnvCollectorBlock::new);

    public EnvCollectorBlock(Properties properties) {
        super(properties, ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY);
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 0.1875, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.8125, 0, 1, 1, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0.8125, 0.8125, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.8125, 0.1875, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.1875, 0.8125, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.4375, 0.375, 0.625, 0.625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.0625, 0.1875, 0.8125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.8125, 0.9375, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.8125, 0.1875, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.0625, 0.9375, 0.8125, 0.1875), BooleanOp.OR);

        return shape;
    }

    public static final VoxelShape SHAPE = makeShape();

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnvCollectorBlockEntity(pPos, pState);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        return openMenu(pLevel, pPos, pPlayer);
    }

    @SuppressWarnings("SameReturnValue")
    private InteractionResult openMenu(Level pLevel, BlockPos pPos, Player pPlayer) {
        if (!pLevel.isClientSide) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof EnvCollectorBlockEntity) {
                pPlayer.openMenu((MenuProvider) be, pPos);
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        openMenu(pLevel, pPos, pPlayer);
        return ItemInteractionResult.SUCCESS;
    }

}
