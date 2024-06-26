package robaertschi.environmenttech.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface ITickableBlockEntity {
    void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState);
}
