package com.gunmetalblack.mfff.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.world.World;

public class DisplayTextEntity extends ArmorStandEntity {
    int ticksAlive = 0;
    public DisplayTextEntity(EntityType<? extends ArmorStandEntity> p_i50225_1_, World p_i50225_2_) {
        super(p_i50225_1_, p_i50225_2_);
    }
    @Override public void tick()
    {
        if(!(level.isClientSide))
        {
            if(ticksAlive > 30)
            {
                kill();
            }
            ticksAlive++;
        }
        super.tick();
    }
}
