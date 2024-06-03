package robaertschi.environmenttech.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EnvCollectorBlockEntity extends BlockEntity {
    public EnvCollectorBlockEntity(BlockEntityType<EnvCollectorBlockEntity> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public EnvCollectorBlockEntity(BlockPos pos, BlockState state) {
        this(ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(), pos, state);
    }
}
