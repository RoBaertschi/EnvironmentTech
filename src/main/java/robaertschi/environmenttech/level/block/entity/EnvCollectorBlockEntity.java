package robaertschi.environmenttech.level.block.entity;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import robaertschi.environmenttech.data.capabilities.EnvStorage;
import robaertschi.environmenttech.data.capabilities.EnvType;

import static robaertschi.environmenttech.EnvironmentTech.MODID;

public class EnvCollectorBlockEntity extends BlockEntity {

    @Getter
    private final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            EnvCollectorBlockEntity.this.setChanged();
        }
    };

    @Getter
    private final EnvStorage envStorage = new EnvStorage(EnvType.Chunk, 64, 0, 1) {
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            EnvCollectorBlockEntity.this.setChanged();
        }
    };

    private int progress = 0;


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

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EnvCollectorBlockEntity envCollectorBlockEntity) {

    }
}
