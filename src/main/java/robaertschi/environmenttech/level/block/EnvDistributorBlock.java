/*
 *  EnvironmentTech  Copyright (C) 2024 Robin Bärtschi
 *  This program comes with ABSOLUTELY NO WARRANTY; for details open the file LICENSE at the root of the source code.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; read the LICENSE file at the root of the source code for details.
 */
package robaertschi.environmenttech.level.block;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.Nullable;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;

import robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import robaertschi.environmenttech.level.block.entity.EnvDistributorBlockEntity;

@Slf4j
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnvDistributorBlock extends SimpleBlockWithEntity<EnvDistributorBlockEntity> {
    public static final MapCodec<EnvDistributorBlock> CODEC = simpleCodec(EnvDistributorBlock::new);

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
