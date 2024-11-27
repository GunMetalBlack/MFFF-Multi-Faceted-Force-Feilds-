package com.gunmetalblack.mfff.common.capability.forceprojector;

import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import com.gunmetalblack.mfff.common.capability.energystorage.MFFFEnergyStorage;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import java.util.Optional;

public class LogicalForceProjector {
    public BlockPos pos;
    public int radius = 3;
    public int xOffset = 0;
    public int yOffset = 0;
    public int zOffset = 0;
    public boolean isProjecting = false;
    public int perBlockUpkeep = 1000;
    public int initialEnergyCost = (int) Math.pow((2 * radius)+1,2)* 6 * 1000 * 3;
    public int upkeepPerTick = 6 * (int)Math.pow((2 * radius + 1),2);

    public int offset;
    public LogicalForceProjector(CompoundNBT nbt)
    {
        int x = nbt.getInt("x");
        int y = nbt.getInt("y");
        int z = nbt.getInt("z");
        this.isProjecting = nbt.getBoolean("isProjecting");
        this.pos = new BlockPos(x,y,z);
    }
    public LogicalForceProjector(BlockPos pos)
    {
        this.pos = pos;
    }
    public CompoundNBT serializeNbt()
    {
        CompoundNBT data = new CompoundNBT();
        data.putBoolean("isProjecting", isProjecting);
        data.putInt("x",pos.getX());
        data.putInt("y",pos.getY());
        data.putInt("z",pos.getZ());
        return data;
    }

    public Optional<MFFFEnergyStorage> getEnergyStorage(ServerWorld world)
    {
        return world.getBlockEntity(pos).getCapability(CapabilityEnergy.ENERGY).resolve().filter(MFFFEnergyStorage.class::isInstance).map(MFFFEnergyStorage.class::cast);
    }


    public void tick(ServerWorld world)
    {
        int currentEnergy = getEnergyStorage(world).map(EnergyStorage::getEnergyStored).orElse(0);
        if (world.hasNeighborSignal(pos) && isProjecting && initialEnergyCost >= currentEnergy) {
            forceFieldBuild(world);
            isProjecting = false;
        }
    }

    public boolean tryConsumeUpKeepEnergy(ServerWorld world)
    {
        return getEnergyStorage(world).map(mfffEnergyStorage -> {
            int amountAvailable = mfffEnergyStorage.extractEnergy(perBlockUpkeep,true);
            if(amountAvailable != perBlockUpkeep) {return false;}
            mfffEnergyStorage.extractEnergy(perBlockUpkeep,false);
            return true;
        }).orElse(false);
    }

    public boolean containsBlockPos(BlockPos targetPos) {
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
    public void forceFieldDestroy(World level) {
        for (Direction direction : Direction.values()) {
            for (int i = -radius; i <= radius; ++i) {
                for (int j = -radius; j <= radius; ++j) {
                    level.setBlock(pos.offset(xOffset,yOffset,zOffset).offset(rotateOffsetAboutDirection(i, j, direction, radius)), Blocks.AIR.defaultBlockState(), 2);
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

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        initialEnergyCost = (int) Math.pow((2 * radius)+1,2)* 6 * 1000 * 3;
        upkeepPerTick = 6 * (int)Math.pow((2 * radius + 1),2);
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setzOffset(int zOffset) {
        this.zOffset = zOffset;
    }

    public void setProjecting(boolean projecting) {
        isProjecting = projecting;
    }

    public void setPerBlockUpkeep(int perBlockUpkeep) {
        this.perBlockUpkeep = perBlockUpkeep;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}