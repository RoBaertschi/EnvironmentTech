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
package xyz.robaertschi.environmenttech.level.block.entity.generators;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import xyz.robaertschi.environmenttech.ET;
import xyz.robaertschi.environmenttech.data.capabilities.EnvStorage;
import xyz.robaertschi.environmenttech.level.block.entity.ETBlockEntities;
import xyz.robaertschi.environmenttech.level.block.entity.ITickableBlockEntity;

public class HeatGeneratorBlockEntity extends BlockEntity implements ITickableBlockEntity {
    private static final HashMap<Block, Long> BLOCK_TO_ENV = new HashMap<>(Map.of(Blocks.TORCH, 1L, Blocks.FIRE, 3L, Blocks.CAMPFIRE, 5L, Blocks.SOUL_TORCH, 6L, Blocks.SOUL_FIRE, 8L, Blocks.SOUL_CAMPFIRE, 10L, Blocks.LAVA, 14L));

    public static final String ENV_TAG = "Env";
    private final EnvStorage envStorage = new EnvStorage(64) {
        @Override
        public void onContentsChanged() {
            HeatGeneratorBlockEntity.this.setChanged();
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

    public HeatGeneratorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ETBlockEntities.HEAT_GENERATOR_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState) {
        var toAdd = BLOCK_TO_ENV.getOrDefault(level.getBlockState(blockPos.below()).getBlock(), 0L);
        envStorage.receiveEnv(toAdd, false);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag modData = tag.getCompound(ET.MODID);
        this.envStorage.setEnvStored(modData.getLong(ENV_TAG));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag modData = new CompoundTag();
        modData.putLong(ENV_TAG, envStorage.getEnvStored());
        tag.put(ET.MODID, modData);
    }
}
