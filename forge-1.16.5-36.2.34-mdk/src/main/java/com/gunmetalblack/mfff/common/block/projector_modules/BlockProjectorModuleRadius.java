package com.gunmetalblack.mfff.common.block.projector_modules;

import com.gunmetalblack.mfff.common.block.force_projector.TileForceProjector;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import com.gunmetalblack.mfff.common.capability.forceprojector.LogicalForceProjector;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;

public class BlockProjectorModuleRadius extends ProjectorModuleParent{

    public BlockProjectorModuleRadius(boolean waterloggable) {
        super(waterloggable);
    }

    @Override

    public void applyModuleEffects(LogicalForceProjector projector)
    {
        projector.setRadius(projector.radius + 3);
    }
}
