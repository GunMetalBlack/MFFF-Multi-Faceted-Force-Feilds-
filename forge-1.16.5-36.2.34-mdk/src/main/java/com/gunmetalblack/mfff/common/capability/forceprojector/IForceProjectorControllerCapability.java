package com.gunmetalblack.mfff.common.capability.forceprojector;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;

import java.util.List;
import java.util.Optional;

public interface IForceProjectorControllerCapability {
    void registerLogicalForceProjector(World level, BlockPos pos, BlockState state);
    List<LogicalForceProjector> getLogicalProjectorList();
    void projectorRemove(BlockPos pos);
    Optional<LogicalForceProjector> getFromPosition(BlockPos pos);
    void directAddProjector(LogicalForceProjector logicalForceProjector);
    boolean isBlockWithinForceField(BlockPos targetBlock);
    void onTickEvent(final TickEvent.WorldTickEvent event);
    Optional<LogicalForceProjector> getProjecterFromForceBlock(BlockPos targetBlock);
}
