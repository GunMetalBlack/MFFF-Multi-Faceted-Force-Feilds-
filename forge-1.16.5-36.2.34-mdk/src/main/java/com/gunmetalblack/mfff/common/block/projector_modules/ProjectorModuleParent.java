package com.gunmetalblack.mfff.common.block.projector_modules;


import com.gunmetalblack.mfff.common.block.force_projector.BlockForceProjector;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import com.gunmetalblack.mfff.common.capability.forceprojector.LogicalForceProjector;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;



public class ProjectorModuleParent extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public boolean waterloggable;


    public ProjectorModuleParent(boolean waterloggable) {
        super(getblockBlockProperties());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
        this.waterloggable = waterloggable;
    }


    public ProjectorModuleParent(AbstractBlock.Properties properties, boolean waterloggable) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
        this.waterloggable = waterloggable;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(WATERLOGGED);
    }

    /**
     *
     * Copied from {@link net.minecraft.block.AnvilBlock}.
     */
    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    /*
     * Make parent module:
     *  if a projector is found save the direction its facing
     *  Only if the direction its placed is in the correct direction and is in contact with the module also check if the block infront and behind have the parent instance if not pass
     *   The parent saves a instance of itself which for every child placed will be passed on
     *   Each Module will add a +1 to the multiplier
     * */

    public BlockState getBlockStateRelative(World worldIn, BlockPos pos, BlockState state, int var)
    {
        return worldIn.getBlockState(pos.relative(state.getValue(ProjectorModuleParent.FACING), var));
    }

    public BlockPos getBlockPosRelative(BlockPos pos, BlockState state, int var)
    {
        return pos.relative(state.getValue(ProjectorModuleParent.FACING), var);
    }

    public void setProjectorValue(int radiusMultiplyer)
    {
        System.out.println("RADIUS:" + radiusMultiplyer);
    }

    public void onModulePropagate(World worldIn, BlockPos pos, BlockState state)
    {
        for (int index : new int[]{1, -1}) {
            for(int i = 0; i < 8; ++i) {
                Block iterBlock = getBlockStateRelative(worldIn, pos, state, index * i).getBlock();
                if(iterBlock instanceof BlockForceProjector) {
                    BlockPos posOfProjector = getBlockPosRelative(pos, state, index * i); // todo helper function for this
                    worldIn.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).resolve().flatMap(cap ->
                            cap.getFromPosition(posOfProjector)).ifPresent(proj ->
                            proj.searchForModules(worldIn));
                }
            }
        }
    }

    public void applyModuleEffects(LogicalForceProjector projector)
    {

    }



    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemInHandOfPlacer) {
        if(!worldIn.isClientSide) {
            onModulePropagate(worldIn, pos, state);
        }
        super.setPlacedBy(worldIn, pos, state, placer, itemInHandOfPlacer);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public void onRemove(BlockState originalState, World level, BlockPos blockPos, BlockState newState, boolean flag) {
        if(!level.isClientSide) {
            onModulePropagate(level, blockPos, originalState);
        }
        super.onRemove(originalState,level,blockPos,newState,flag);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockState = super.getStateForPlacement(context);
        return (blockState == null) ? null : blockState
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, waterloggable && context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    public static AbstractBlock.Properties getblockBlockProperties() {
        return AbstractBlock.Properties.of(Material.STONE, MaterialColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F)
                .noOcclusion()
                .isValidSpawn((BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity)->false)
                .isRedstoneConductor((BlockState state, IBlockReader reader, BlockPos pos)->false)
                .isSuffocating((BlockState state, IBlockReader reader, BlockPos pos)->false)
                .isViewBlocking((BlockState state, IBlockReader reader, BlockPos pos)->false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    /**
     * Schedule a tick if the block is  and a neighbor is updated.
     */
    @Override
    public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingState, IWorld level, BlockPos currentPos, BlockPos facingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(blockState, facing, facingState, level, currentPos, facingPos);
    }

}
