package robaertschi.environmenttech.level.block.entity;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.EnvStorage;
import robaertschi.environmenttech.data.capabilities.EnvType;
import robaertschi.environmenttech.data.recipes.ETRecipes;
import robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class EnvCollectorBlockEntity extends BlockEntity {
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_INPUT_COUNT = 1;

    public static final int SLOT_OUTPUT = 0;
    public static final int SLOT_OUTPUT_COUNT = 1;

    public static final int SLOT_COUNT = SLOT_INPUT_COUNT + SLOT_OUTPUT_COUNT;

    @Getter
    private final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            EnvCollectorBlockEntity.this.setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot < SLOT_INPUT_COUNT;
        }
    };

    @Getter
    private final RecipeWrapper recipeWrapper = new RecipeWrapper(inventory);

    @Getter
    private final EnvStorage envStorage = new EnvStorage(EnvType.Chunk, 64, 0, 1) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            EnvCollectorBlockEntity.this.setChanged();
        }
    };

    private int progress = 0;
    // Every 20 ticks, we take ENV from the current Chunk
    private int takeEnv = 0;
    @Nullable
    private EnvCollectorRecipe currentRecipe = null;


    public EnvCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(pTag, provider);
        CompoundTag modData = pTag.getCompound(MODID);
        this.inventory.deserializeNBT(provider, modData.getCompound("Inventory"));
        this.envStorage.setEnvStored(modData.getLong("ENV"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(pTag, provider);
        CompoundTag modData = new CompoundTag();
        modData.put("Inventory", inventory.serializeNBT(provider));
        modData.putLong("ENV", envStorage.getEnvStored());
        pTag.put(MODID, modData);
    }

    public ItemStack getInputItem() {
        return this.inventory.getStackInSlot(0);
    }

    public ItemStack getOutputItem() {
        return this.inventory.getStackInSlot(1);
    }

    public void setInputItem(ItemStack itemStack) {
        this.inventory.setStackInSlot(0, itemStack);
    }


    public void setOutputItem(ItemStack itemStack) {
        this.inventory.setStackInSlot(1, itemStack);
    }


    public void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState) {

        if (takeEnv <= 0) {
            ChunkAccess currentChunk = level.getChunkAt(blockPos);
            if (currentChunk.hasData(ETAttachments.ENV) && currentChunk.getData(ETAttachments.ENV) > 0) {
                long received = envStorage.receiveEnv(1, false);
                currentChunk.setData(ETAttachments.ENV, currentChunk.getData(ETAttachments.ENV) - received);
                takeEnv = 20;
            }
        } else {
            takeEnv--;
        }

        if (hasRecipe(level)) {
            if (progress > 0 && progress < 60) {
                progress++;
            } else if (progress > 0) {
                produce(level);
                progress = 0;
            } else {
                assert currentRecipe != null;
                if (envStorage.getEnvStored() >= currentRecipe.envUsed()) {
                    envStorage.setEnvStored(envStorage.getEnvStored() - currentRecipe.envUsed());
                    progress = 1;
                }
            }

        }
    }

    private void produce(ServerLevel level) {
        getInputItem().setCount(getInputItem().getCount() - 1);
        // Is safe, as hasRecipe already checked
        assert currentRecipe != null;
        setOutputItem(currentRecipe.assemble(recipeWrapper, level.registryAccess()));
    }

    private boolean hasRecipe(Level level) {
        if (currentRecipe != null) {
            return currentRecipe.matches(recipeWrapper, level);
        }
        var recipe = level.getRecipeManager().getRecipeFor(ETRecipes.ENV_COLLECTOR_RECIPE_TYPE.get(), recipeWrapper, level);
        if (recipe.isEmpty()) {
            progress = 0;
            return false;
        } else {
            currentRecipe = recipe.get().value();
            progress = 0;
            return true;
        }

    }
}
