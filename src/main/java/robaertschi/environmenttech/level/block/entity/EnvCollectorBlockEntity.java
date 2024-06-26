package robaertschi.environmenttech.level.block.entity;

import lombok.Getter;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import robaertschi.environmenttech.compat.top.TOPInfoProvider;
import robaertschi.environmenttech.data.attachments.ETAttachments;
import robaertschi.environmenttech.data.capabilities.AdaptedItemHandler;
import robaertschi.environmenttech.data.capabilities.EnvStorage;
import robaertschi.environmenttech.data.capabilities.EnvType;
import robaertschi.environmenttech.data.recipes.ETRecipes;
import robaertschi.environmenttech.data.recipes.EnvCollectorRecipe;
import robaertschi.environmenttech.menu.EnvCollectorMenu;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class EnvCollectorBlockEntity extends BlockEntity implements MenuProvider, ITickableBlockEntity, TOPInfoProvider {
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_INPUT_COUNT = 1;

    @SuppressWarnings("unused")
    public static final int SLOT_OUTPUT = 0;
    public static final int SLOT_OUTPUT_COUNT = 1;

    public static final int SLOT_COUNT = SLOT_INPUT_COUNT + SLOT_OUTPUT_COUNT;
    public static final String INPUT_INVENTORY_KEY = "InputInventory";
    public static final String OUTPUT_INVENTORY_KEY = "OutputInventory";
    public static final String ENV_KEY = "ENV";
    public static final String PROGRESS_KEY = "Progress";

    @Getter
    private final ItemStackHandler inputInventory = new ItemStackHandler(SLOT_INPUT_COUNT) {
        protected void onContentsChanged(int slot) {
            EnvCollectorBlockEntity.this.setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    @Getter
    private final ItemStackHandler outputInventory = new ItemStackHandler(SLOT_INPUT_COUNT) {
        protected void onContentsChanged(int slot) {
            EnvCollectorBlockEntity.this.setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    @Getter
    private final Lazy<IItemHandlerModifiable> inventory = Lazy.of(() -> new CombinedInvWrapper(inputInventory, outputInventory) {

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot < SLOT_INPUT_COUNT;
        }
    });
    @Getter
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new AdaptedItemHandler(inputInventory) {
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }
    });
    @Getter
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new AdaptedItemHandler(outputInventory) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }
    });
    @Getter
    private final Lazy<RecipeInput> recipeWrapper = Lazy.of(() -> new RecipeInput() {
        @Override
        public @NotNull ItemStack getItem(int slot) {
            return getInputInventory().getStackInSlot(slot);
        }

        @Override
        public int size() {
            return getInputInventory().getSlots();
        }
    });


    @Getter
    private final EnvStorage envStorage = new EnvStorage(EnvType.Chunk, 64, 0, 1) {
        @Override
        public void onContentsChanged() {
            EnvCollectorBlockEntity.this.setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    @Getter
    private final ContainerData data;

    @Getter
    private int progress = 0;
    @Getter
    private int maxProgress = 100;
    // Every 20 ticks, we take ENV from the current Chunk
    private int takeEnv = 0;
    @Nullable
    private EnvCollectorRecipe currentRecipe = null;


    public EnvCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(ETBlockEntities.ENV_COLLECTOR_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch(pIndex) {
                    case 0 -> EnvCollectorBlockEntity.this.progress;
                    case 1 -> EnvCollectorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> EnvCollectorBlockEntity.this.progress = pValue;
                    case 1 -> EnvCollectorBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }



    @Override
    protected void loadAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(pTag, provider);
        CompoundTag modData = pTag.getCompound(MODID);
        inputInventory.deserializeNBT(provider, modData.getCompound(INPUT_INVENTORY_KEY));
        outputInventory.deserializeNBT(provider, modData.getCompound(OUTPUT_INVENTORY_KEY));
        this.envStorage.setEnvStored(modData.getLong(ENV_KEY));
        this.progress = modData.getInt(PROGRESS_KEY);
//        this.maxProgress = modData.getInt(MAX_PROGRESS_KEY);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(pTag, provider);
        CompoundTag modData = new CompoundTag();
        modData.put(INPUT_INVENTORY_KEY, inputInventory.serializeNBT(provider));
        modData.put(OUTPUT_INVENTORY_KEY, outputInventory.serializeNBT(provider));
        modData.putLong(ENV_KEY, envStorage.getEnvStored());
        modData.putInt(PROGRESS_KEY, progress);
//        modData.putInt(MAX_PROGRESS_KEY, maxProgress);
        pTag.put(MODID, modData);
    }


    public ItemStack getInputItem() {
        return this.inputInventory.getStackInSlot(0);
    }

    public ItemStack getOutputItem() {
        return this.outputInventory.getStackInSlot(0);
    }

    @SuppressWarnings("unused")
    public void setInputItem(ItemStack itemStack) {
        this.inputInventory.setStackInSlot(0, itemStack);
    }


    public void setOutputItem(ItemStack itemStack) {
        this.outputInventory.setStackInSlot(0, itemStack);
    }


    @Override
    public void serverTick(ServerLevel level, BlockPos blockPos, @SuppressWarnings("unused") BlockState blockState) {

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
            if (progress > 0 && progress < getMaxProgress()) {
                progress++;
            } else if (progress > 0) {
                produce(level);
                progress = 0;
            } else if (getOutputItem().getCount() < getOutputItem().getMaxStackSize()){
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

        if (getOutputItem().isEmpty()) {
            setOutputItem(currentRecipe.assemble(recipeWrapper.get(), level.registryAccess()));
        } else {
            getOutputItem().setCount(getOutputItem().getCount() + 1);
        }

    }

    private boolean hasRecipe(Level level) {
        if (currentRecipe != null) {
            return currentRecipe.matches(recipeWrapper.get(), level);
        }
        var recipe = level.getRecipeManager().getRecipeFor(ETRecipes.ENV_COLLECTOR_RECIPE_TYPE.get(), recipeWrapper.get(), level);
        if (recipe.isEmpty()) {
            progress = 0;
            return false;
        } else {
            currentRecipe = recipe.get().value();
            progress = 0;
            return true;
        }

    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void onDataPacket(@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket pkt, HolderLookup.@NotNull Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("screen.environmenttech.env_collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new EnvCollectorMenu(pContainerId, pPlayer, this, data);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (hasRecipe(level)) {
            probeInfo.horizontal().progress(getProgress(), getMaxProgress(),
                    probeInfo.defaultProgressStyle().suffix("%")
            );
        }
    }
}
