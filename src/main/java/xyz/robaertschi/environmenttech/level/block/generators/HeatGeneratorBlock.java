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
package xyz.robaertschi.environmenttech.level.block.generators;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.Nullable;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;

import xyz.robaertschi.environmenttech.level.block.SimpleBlockWithEntity;
import xyz.robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import xyz.robaertschi.environmenttech.level.block.entity.HeatGeneratorBlockEntity;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HeatGeneratorBlock extends SimpleBlockWithEntity<HeatGeneratorBlockEntity> {
    public static final MapCodec<HeatGeneratorBlock> CODEC = simpleCodec(HeatGeneratorBlock::new);

    public HeatGeneratorBlock(Properties pProperties) {
        super(pProperties, ETBlockEntities.HEAT_GENERATOR_BLOCK_ENTITY);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HeatGeneratorBlockEntity(pos, state);
    }
}
