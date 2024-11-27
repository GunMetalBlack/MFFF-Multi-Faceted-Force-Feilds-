package com.gunmetalblack.mfff.common.capability.forceprojector;


import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForceProjectorControllerCapability implements IForceProjectorControllerCapability{

    private List<LogicalForceProjector> projectors = new ArrayList<>();


    public static void register()
    {
        CapabilityManager.INSTANCE.register(IForceProjectorControllerCapability.class, new ForceProjectorControllerCapabilityStorage(), ForceProjectorControllerCapability::new);
    }

    @Override
    public void onTickEvent(final TickEvent.WorldTickEvent event)
    {
        for(LogicalForceProjector projector : projectors)
        {
            projector.tick((ServerWorld) event.world);
        }
    }


    @Override
    public void registerLogicalForceProjector(World level, BlockPos pos, BlockState state) {
        projectors.add(new LogicalForceProjector(pos));
    }

    @Override
    public List<LogicalForceProjector> getLogicalProjectorList() {
        return projectors;
    }
    public void directAddProjector(LogicalForceProjector logicalForceProjector)
    {
        projectors.add(logicalForceProjector);
    }
    public Optional<LogicalForceProjector> getFromPosition(BlockPos pos)
    {
        return projectors.stream().filter(projector -> projector.pos.equals(pos)).findAny();
    }
    public boolean isBlockWithinForceField(BlockPos targetBlock)
    {
       return getProjecterFromForceBlock(targetBlock).isPresent();
    }
    public Optional<LogicalForceProjector> getProjecterFromForceBlock(BlockPos targetBlock)
    {
        return projectors.stream().filter(projector -> projector.containsBlockPos(targetBlock)).findFirst();
    }
    @Override
    public void projectorRemove(BlockPos pos) {
        new ArrayList<>(projectors).stream().filter(projector -> projector.pos.equals(pos)).forEach(projector -> projectors.remove(projector));
    }

}
