package com.gunmetalblack.mfff.common.block.force_projector;
import com.gunmetalblack.mfff.common.block.machine.AbstractBlockMachineTile;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class BlockForceProjector extends AbstractBlockMachineTile {

    public BlockForceProjector(RegistryObject<TileEntityType<? extends TileEntity>> tileEntityType) {
        this(tileEntityType, false);
    }

    @Override
    public void setPlacedBy(World level, BlockPos blockPos, BlockState blockState, LivingEntity placer, ItemStack itemInHandOfPlacer) {
        super.setPlacedBy(level, blockPos, blockState, placer, itemInHandOfPlacer);
        level.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).ifPresent(cap -> cap.registerLogicalForceProjector(level,blockPos,blockState));
    }

    @Override
    public void onRemove(BlockState originalState, World level, BlockPos blockPos, BlockState newState, boolean flag) {
        if (!originalState.is(newState.getBlock())) {
            super.onRemove(originalState, level, blockPos, newState, flag);
            level.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).ifPresent(cap -> cap.projectorRemove(blockPos));
        }
    }

    public BlockForceProjector(RegistryObject<TileEntityType<? extends TileEntity>> tileEntityType, boolean waterloggable) {
        super(tileEntityType, waterloggable);
    }

    public BlockForceProjector(AbstractBlock.Properties properties, RegistryObject<TileEntityType<? extends TileEntity>> tileEntityType) {
        super(properties, tileEntityType, false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState blockState, World level, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        // TODO
        return super.use(blockState, level, blockPos, playerEntity, hand, hit);
    }

}