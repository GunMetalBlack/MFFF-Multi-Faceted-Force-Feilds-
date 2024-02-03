package com.gunmetalblack.mfff.common.capability.forceprojector;

import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ForceProjectorControllerCapabilityProvider implements ICapabilitySerializable<INBT> {
    
    private final IForceProjectorControllerCapability capabilityInstance = new ForceProjectorControllerCapability();
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
        return MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY.orEmpty(capability,LazyOptional.of(()->this.capabilityInstance));
    }

    @Override
    public INBT serializeNBT() {
        return MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY.getStorage().writeNBT(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY, capabilityInstance, null);
    }

    @Override
    public void deserializeNBT(INBT inbt) {
        MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY.getStorage().readNBT(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY, capabilityInstance, null, inbt);
    }
}
