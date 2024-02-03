package com.gunmetalblack.mfff.common.capability.forceprojector;

import com.gunmetalblack.mfff.common.reg.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LogicalForceProjector {
    public BlockPos pos;
    public int radius = 9;
    public int xOffset = 0;
    public int yOffset = 0;
    public int zOffset = 0;


    public int offset;
    public LogicalForceProjector(CompoundNBT nbt)
    {
        int x = nbt.getInt("x");
        int y = nbt.getInt("y");
        int z = nbt.getInt("z");
        this.pos = new BlockPos(x,y,z);
    }
    public LogicalForceProjector(BlockPos pos)
    {
        this.pos = pos;
    }
    public CompoundNBT serializeNbt()
    {
        CompoundNBT data = new CompoundNBT();
        data.putInt("x",pos.getX());
        data.putInt("y",pos.getY());
        data.putInt("z",pos.getZ());
        return data;
    }

    public boolean testFunc(BlockPos targetPos) {
        // Apply Offset
        BlockPos centerPos = pos.offset(xOffset, yOffset, zOffset);
        // Calculate Distances from Offset Projector to the Target Block
        int xDist = Math.abs(targetPos.getX() - centerPos.getX());
        int yDist = Math.abs(targetPos.getY() - centerPos.getY());
        int zDist = Math.abs(targetPos.getZ() - centerPos.getZ());
        // Check if x/y/z matches
        boolean xMatches = xDist == radius;
        boolean yMatches = yDist == radius;
        boolean zMatches = zDist == radius;
        // Check if x/y/z in range
        boolean xInRange = xDist <= radius;
        boolean yInRange = yDist <= radius;
        boolean zInRange = zDist <= radius;
        // Target block is part of this forcefield if all coordinates are in
        // range and at least 1 of them is an exact match
        return (xInRange && yInRange && zMatches) ||
                (xInRange && yMatches && zInRange) ||
                (xMatches && yInRange && zInRange);
    }
    public void forceFieldBuild(World level) {
        for (Direction direction : Direction.values()) {
            for (int i = -radius; i <= radius; ++i) {
                for (int j = -radius; j <= radius; ++j) {
                    level.setBlock(pos.offset(xOffset,yOffset,zOffset).offset(rotateOffsetAboutDirection(i, j, direction, radius)), BlockRegister.FORCE_BLOCK.get().defaultBlockState(), 2);
                }
            }
        }
    }
    public BlockPos rotateOffsetAboutDirection(int i, int j, Direction direction, int magnitude) {
        if(direction.getAxis() == Direction.Axis.X) return new BlockPos(magnitude * direction.getAxisDirection().getStep(), i, j);
        if(direction.getAxis() == Direction.Axis.Y) return new BlockPos(i, magnitude * direction.getAxisDirection().getStep(), j);
        if(direction.getAxis() == Direction.Axis.Z) return new BlockPos(i, j, magnitude * direction.getAxisDirection().getStep());
        return null;
    }

}
