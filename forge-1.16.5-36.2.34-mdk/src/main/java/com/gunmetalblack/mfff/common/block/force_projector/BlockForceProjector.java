package com.gunmetalblack.mfff.common.block.force_projector;

import com.gunmetalblack.mfff.common.block.machine.AbstractBlockMachineTile;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import com.gunmetalblack.mfff.common.capability.energystorage.MFFFEnergyStorage;
import com.gunmetalblack.mfff.common.reg.EntityReg;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.command.impl.data.EntityDataAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {

        if(!(world instanceof ServerWorld)){return ActionResultType.sidedSuccess(true);}
        ITextComponent initEnergyCost = world.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).resolve()
            .flatMap(cap -> cap.getFromPosition(blockPos))
            .map(projector -> projector.initialEnergyCost)
            .map(integer -> MFFFEnergyStorage.EnergyMeasurementUnit.formatEnergyValue(integer,0,true))
            .orElse(new StringTextComponent("piss"));
        int radius = world.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).resolve()
            .flatMap(cap -> cap.getFromPosition(blockPos))
            .map(projector -> projector.radius)
            .orElse(-69);
        ITextComponent currentEnergy = world.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).resolve()
            .flatMap(cap -> cap.getFromPosition(blockPos))
            .flatMap(projector -> projector.getEnergyStorage((ServerWorld) world,blockPos))
            .map(mfffEnergyStorage -> mfffEnergyStorage.getEnergyStored())
            .map(integer -> MFFFEnergyStorage.EnergyMeasurementUnit.formatEnergyValue(integer,0,true))
            .orElse(new StringTextComponent("piss"));
        String projInfo = "Radius:" + radius + "\\n" + "CurrentEnergy:" + currentEnergy.getString() + "\\n" + "InitialEnergyCost:" +initEnergyCost.getString();
        ArmorStandEntity entity = EntityReg.DISPLAY_TEXT.get().create(world);
        EntityDataAccessor entityDataAccessor = new EntityDataAccessor(entity);
        CompoundNBT entityNBT = entityDataAccessor.getData();
        entityNBT.putString("CustomName", "{\"text\":\""+projInfo+"\",\"color\":\"gold\",\"bold\":true}");
        entityNBT.putByte("CustomNameVisible", (byte)1);
        entityNBT.putByte("NoGravity", (byte)1);
        entityNBT.putByte("Invisible", (byte)1);
        try {
            entityDataAccessor.setData(entityNBT);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
        entity.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
        world.addFreshEntity(entity);
        return super.use(blockState, world, blockPos, playerEntity, hand, hit);
    }

}