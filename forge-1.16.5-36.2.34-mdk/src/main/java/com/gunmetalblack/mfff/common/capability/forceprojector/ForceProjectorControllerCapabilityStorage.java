package com.gunmetalblack.mfff.common.capability.forceprojector;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ForceProjectorControllerCapabilityStorage implements Capability.IStorage<IForceProjectorControllerCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IForceProjectorControllerCapability> capability, IForceProjectorControllerCapability instance, Direction direction) {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT projectorListNBT = new ListNBT();
        instance.getLogicalProjectorList().stream().forEach( projector -> projectorListNBT.add(0,projector.serializeNbt()));
        nbt.put("projectors", projectorListNBT);
        return nbt;
    }


    @Override
    public void readNBT(Capability<IForceProjectorControllerCapability> capability, IForceProjectorControllerCapability instance, Direction direction, INBT inbt) {
        if(inbt instanceof CompoundNBT)
        {
            CompoundNBT nbt = (CompoundNBT) inbt;
            ListNBT projectorListNBT = nbt.getList("projectors",10);
            projectorListNBT.stream().map(projectorNBT -> (CompoundNBT)projectorNBT).map(LogicalForceProjector::new).forEach(instance::directAddProjector);

        }
    }
}
