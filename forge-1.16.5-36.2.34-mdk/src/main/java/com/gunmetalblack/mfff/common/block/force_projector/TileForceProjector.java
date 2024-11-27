package com.gunmetalblack.mfff.common.block.force_projector;


import com.gunmetalblack.mfff.common.block.machine.TileMachine;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import com.gunmetalblack.mfff.common.reg.ContainerRegister;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.sql.SQLOutput;

public class TileForceProjector extends TileMachine{

    public static final ITextComponent DEFAULT_NAME = new TranslationTextComponent("gui.mfff.force_projector");


    public TileForceProjector(TileEntityType<?> tileEntityType) {
        super(tileEntityType, ContainerRegister.FORCE_PROJECTOR::get, 1, 200_000_000, 2_147_483_647, 0, DEFAULT_NAME);
    }
}
