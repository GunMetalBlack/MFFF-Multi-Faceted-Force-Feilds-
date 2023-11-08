package com.gunmetalblack.mfff.common.block.force_projector;

import com.gunmetalblack.mfff.common.block.machine.AbstractContainerMachine;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ContainerForceProjector extends AbstractContainerMachine {

    public ContainerForceProjector(@Nullable ContainerType<?> containerType, int windowId, World level, BlockPos blockPos, PlayerInventory playerInventory) {
        super(containerType, windowId, level, blockPos, playerInventory);
        addSlot(84,47);
        addPlayerInventorySlots(8,84);
    }
}
