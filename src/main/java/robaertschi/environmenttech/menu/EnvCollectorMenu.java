package robaertschi.environmenttech.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import robaertschi.environmenttech.level.block.ETBlocks;
import robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity;

import static robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity.SLOT_COUNT;
import static robaertschi.environmenttech.level.block.entity.EnvCollectorBlockEntity.SLOT_INPUT;

public class EnvCollectorMenu extends AbstractContainerMenu {

    private final BlockPos pos;

    public EnvCollectorMenu(int pContainerId, Player player, BlockPos pos) {
        super(ETMenus.ENV_COLLECTOR_MENU.get(), pContainerId);
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof EnvCollectorBlockEntity envCollector) {
            addSlot(new SlotItemHandler(envCollector.getInventory(), 0, 55, 35));
            addSlot(new SlotItemHandler(envCollector.getInventory(), 1, 114, 33));
        }
        layoutPlayerInventorySlots(player.getInventory(), 10, 70);
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(Container playerInventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();
            if (pIndex < SLOT_COUNT) {
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (!this.moveItemStackTo(stack, SLOT_INPUT, SLOT_INPUT+1, false)) {
                if (pIndex < 27 + SLOT_COUNT) {
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, 2, 27 + SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, stack);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.level(), pos), pPlayer, ETBlocks.ENV_COLLECTOR_BLOCK.get());
    }
}
