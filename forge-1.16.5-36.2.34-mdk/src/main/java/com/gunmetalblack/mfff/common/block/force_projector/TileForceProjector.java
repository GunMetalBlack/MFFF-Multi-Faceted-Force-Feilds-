package com.gunmetalblack.mfff.common.block.force_projector;


import com.gunmetalblack.mfff.common.block.machine.TileMachine;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileForceProjector extends TileMachine{

    public static final ITextComponent DEFAULT_NAME = new TranslationTextComponent("gui.mfff.force_projector");


    public TileForceProjector(TileEntityType<?> tileEntityType) {
        super(tileEntityType, null, 1, 200_000_000, 2_147_483_647, 0, DEFAULT_NAME);
    }
}
