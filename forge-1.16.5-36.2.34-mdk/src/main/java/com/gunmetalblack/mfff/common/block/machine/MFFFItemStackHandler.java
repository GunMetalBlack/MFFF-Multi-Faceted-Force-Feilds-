package com.gunmetalblack.mfff.common.block.machine;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class MFFFItemStackHandler extends ItemStackHandler {

    private boolean doCheckItemValidity = true;
    Consumer<Integer> onInventorySlotChanged = (slot) -> {};
    BiFunction<Integer, ItemStack, Boolean> isInventoryItemValid = (slot, stack) -> true;

    public MFFFItemStackHandler(int inventorySize) {
        super(inventorySize);
    }

    public MFFFItemStackHandler(int inventorySize, Consumer<Integer> onInventorySlotChanged, BiFunction<Integer, ItemStack, Boolean> isInventoryItemValid) {
        this(inventorySize);
        this.onInventorySlotChanged = onInventorySlotChanged;
        this.isInventoryItemValid = isInventoryItemValid;
    }

    @Override
    protected void onContentsChanged(int slot) {
        onInventorySlotChanged.accept(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return !doCheckItemValidity || isInventoryItemValid.apply(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    public ItemStack insertItemUnchecked(int slot, @Nonnull ItemStack stack, boolean simulate) {
        this.doCheckItemValidity = false;
        ItemStack result = this.insertItem(slot, stack, simulate);
        this.doCheckItemValidity = true;
        return result;
    }

}