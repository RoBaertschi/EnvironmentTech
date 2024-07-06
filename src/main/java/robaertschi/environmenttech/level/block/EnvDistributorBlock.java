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
package robaertschi.environmenttech.level.block;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.Nullable;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.mojang.serialization.MapCodec;

import robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import robaertschi.environmenttech.level.block.entity.EnvDistributorBlockEntity;

@Slf4j
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnvDistributorBlock extends SimpleBlockWithEntity<EnvDistributorBlockEntity> {
    public static final MapCodec<EnvDistributorBlock> CODEC = simpleCodec(EnvDistributorBlock::new);
    public static final VoxelShape SHAPE = makeShape();


    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.0625, 0.0625, 0.375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.0625, 0.0625, 0.9375, 0.375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.9375, 0.9375, 0.375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.0625, 0.9375, 0.375, 0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.1875, 0.25, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.625, 0.1875, 0.8125, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.625, 0.25, 0.75, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.375, 0, 1, 0.4375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.625, 0.75, 0.75, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.625, 0.1875, 0.75, 1, 0.25), BooleanOp.OR);

        return shape;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public EnvDistributorBlock(Properties pProperties) {
        super(pProperties, ETBlockEntities.ENV_DISTRIBUTOR_BLOCK_ENTITY);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnvDistributorBlockEntity(pPos, pState);
    }
}
