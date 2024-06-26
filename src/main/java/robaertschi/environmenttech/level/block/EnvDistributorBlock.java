package robaertschi.environmenttech.level.block;

import com.mojang.serialization.MapCodec;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import robaertschi.environmenttech.level.block.entity.EnvDistributorBlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;

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
