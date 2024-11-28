package com.gunmetalblack.mfff.common.block;

import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import com.gunmetalblack.mfff.common.capability.forceprojector.IForceProjectorControllerCapability;
import com.gunmetalblack.mfff.common.capability.forceprojector.LogicalForceProjector;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockForce extends Block {

    public BlockForce() {
        super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY)
                .strength(10)
                .noOcclusion()
                .isViewBlocking((BlockState state, IBlockReader level, BlockPos pos)->false)
                .lightLevel(blockState -> 15)
                .sound(SoundType.GLASS));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState state, IBlockReader level, BlockPos blockPos) {
        return 1.0F;
    }
    public VoxelShape getVisualShape(BlockState pState, IBlockReader pReader, BlockPos pPos, ISelectionContext pContext) {
        return VoxelShapes.empty();
    }

    /**
     * Prevents the rendering of internal faces when multiple blocks of the same type are placed next to each other
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return state.getBlock() == adjacentBlockState.getBlock() || super.skipRendering(state, adjacentBlockState, side);
    }
    @Override
    public void onRemove(BlockState originalState, World level, BlockPos blockPos, BlockState newState, boolean flag) {
        if (!originalState.is(newState.getBlock()) && level instanceof ServerWorld) {
            super.onRemove(originalState, level, blockPos, newState, flag);
            boolean shouldReplace = level.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).resolve().flatMap(cap -> cap.getProjecterFromForceBlock(blockPos)).map(logicalForceProjector -> logicalForceProjector.tryConsumeBrokenUpKeepEnergy((ServerWorld) level)).orElse(false);
            if(shouldReplace) {
                level.setBlock(blockPos, originalState, 3);
            }
        }
    }
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
        boolean hadEnoughEnergy = pLevel.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).resolve()
                .flatMap(cap -> cap.getProjecterFromForceBlock(pPos))
                .map(logicalForceProjector -> logicalForceProjector.tryConsumeUpKeepEnergy(pLevel))
                .orElse(false);
        if(!hadEnoughEnergy) pLevel.removeBlock(pPos, false);
    }
/**
     * Copied from {@link net.minecraft.block.AbstractGlassBlock}
     */
    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

}
