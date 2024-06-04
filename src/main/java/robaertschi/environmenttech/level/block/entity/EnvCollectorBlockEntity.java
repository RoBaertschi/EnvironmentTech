package robaertschi.environmenttech.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnvCollectorBlockEntity extends BlockEntity {
    private int progress = 0;


    public EnvCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EnvCollectorBlockEntity envCollectorBlockEntity) {

    }
}
