package robaertschi.environmenttech.level.block.entity;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.NotNull;
import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.EnvStorage;
import robaertschi.environmenttech.data.capabilities.EnvType;
import robaertschi.environmenttech.data.components.ETComponents;

import javax.annotation.ParametersAreNonnullByDefault;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

@Getter
@ParametersAreNonnullByDefault
public class EnvDistributorBlockEntity extends BlockEntity {

    public static final String ENV_TAG = "Env";
    private final EnvStorage envStorage = new EnvStorage(EnvType.Chunk, 64) {
        @Override
        public void onContentsChanged() {
            EnvDistributorBlockEntity.this.setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public EnvDistributorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ETBlockEntities.ENV_DISTRIBUTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        CompoundTag modData = pTag.getCompound(MODID);
        this.envStorage.setEnvStored(modData.getLong(ENV_TAG));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        CompoundTag modData = new CompoundTag();
        modData.putLong(ENV_TAG, envStorage.getEnvStored());
        pTag.put(MODID, modData);
    }

    public void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState) {
        if (envStorage.getEnvStored() > 0) {
            ChunkAccess chunk = level.getChunk(blockPos);
            long value = Math.min(20, envStorage.getEnvStored());
            chunk.setData(ETAttachments.ENV, chunk.getData(ETAttachments.ENV) + value);
            envStorage.setEnvStored(envStorage.getEnvStored() - value);
        }
    }
}
