package com.gunmetalblack.mfff.common.block.force_projector;


import com.gunmetalblack.mfff.common.block.machine.TileMachine;
import com.gunmetalblack.mfff.common.reg.ContainerRegister;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.sql.SQLOutput;

public class TileForceProjector extends TileMachine implements ITickableTileEntity {

    public static final ITextComponent DEFAULT_NAME = new TranslationTextComponent("gui.mfff.force_projector");

    public TileForceProjector(TileEntityType<?> tileEntityType) {
        super(tileEntityType, ContainerRegister.FORCE_PROJECTOR::get, 1, 1_000_000, 5_000, 0, DEFAULT_NAME);
    }

    @Override
    public void tick() {
        if(level == null) return;
        if(level.isClientSide()) {
        } else {
            if(redstoneSignalPresent()) {
               //Do stuff
            }
        }
    }

//    @Override
//    public void load(BlockState state, CompoundNBT tag) {
//        this.setEMPRadius(tag.getDouble("emp_radius"));
//        super.load(state, tag);
//    }
//
//    @Override
//    public CompoundNBT save(CompoundNBT tag) {
//        tag.putDouble("emp_radius", empRadius);
//        return super.save(tag);
//    }

}