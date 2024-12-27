package com.gunmetalblack.mfff.common.block.projector_modules;

import com.gunmetalblack.mfff.common.capability.forceprojector.LogicalForceProjector;

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
