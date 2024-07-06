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
package robaertschi.environmenttech.level.block.entity;

import lombok.Getter;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.EnvStorage;
import robaertschi.environmenttech.data.capabilities.EnvType;

import static robaertschi.environmenttech.ET.MODID;

@Getter
@ParametersAreNonnullByDefault
public class EnvDistributorBlockEntity extends BlockEntity implements ITickableBlockEntity {

    public static final int TICKS_PER_PROCESS_TICK = 20; // 1 Minute
    public static final int REDUCE_PER_PROCESS_TICK = 5;

    private int ticksBetweenProcessTick = 0;

    public static final String ENV_TAG = "Env";
    private final EnvStorage envStorage = new EnvStorage(EnvType.Chunk, 64) {
        @Override
        public void onContentsChanged() {
            EnvDistributorBlockEntity.this.setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public void setEnvStored(long env) {
            super.setEnvStored(env);
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
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

    @Override
    public void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState) {

        if (ticksBetweenProcessTick < TICKS_PER_PROCESS_TICK) {
            ticksBetweenProcessTick++;
            return;
        } else {
            ticksBetweenProcessTick = 0;
        }

        if (envStorage.getEnvStored() > 0) {
            // ES = 1
            long amount = REDUCE_PER_PROCESS_TICK;
            // 1 - 5 = -4
            long result = envStorage.getEnvStored() - amount;

            if ( result < 0 ) {
                // -4 + 5 = 1
                amount = result + amount;
            }

            ChunkAccess chunk = level.getChunk(blockPos);
            chunk.setData(ETAttachments.ENV, chunk.getData(ETAttachments.ENV) + amount);
            envStorage.setEnvStored(envStorage.getEnvStored() - amount);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
