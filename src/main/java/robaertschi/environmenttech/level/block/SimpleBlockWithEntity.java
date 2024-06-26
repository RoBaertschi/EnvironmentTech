package robaertschi.environmenttech.level.block;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robaertschi.environmenttech.level.block.entity.ITickableBlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SimpleBlockWithEntity<T extends BlockEntity & ITickableBlockEntity> extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final Logger log = LoggerFactory.getLogger(SimpleBlockWithEntity.class);

    protected final DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> blockEntityType;

    protected SimpleBlockWithEntity(Properties pProperties, DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> beType) {
        super(pProperties);
        blockEntityType = beType;

    }

    // Always model
    @SuppressWarnings("deprecation")
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        pLevel.updateNeighborsAt(pPos, this);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return super.getStateDefinition();
    }

    @Nullable
    @Override
    public <BT extends BlockEntity> BlockEntityTicker<BT> getTicker(Level pLevel, BlockState pState, BlockEntityType<BT> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, blockEntityType.get(), this::tick);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!level.isClientSide()) {
            if (level instanceof ServerLevel serverLevel) {
                blockEntity.serverTick(serverLevel, blockPos, blockState);
            } else {
                log.error("level.isClientSide() returned false, but is not a ServerLevel. Can not tick block entity.");
            }
        }
    }
}
