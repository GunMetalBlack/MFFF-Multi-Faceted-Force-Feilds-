package com.gunmetalblack.mfff.common.capability;

import com.gunmetalblack.mfff.common.capability.forceprojector.ForceProjectorControllerCapability;
import com.gunmetalblack.mfff.common.capability.forceprojector.IForceProjectorControllerCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class MFFFCapabilites {
    private MFFFCapabilites(){}

    @CapabilityInject(IForceProjectorControllerCapability.class)
    public static Capability<IForceProjectorControllerCapability> FORCE_PROJECTOR_CAPABILITY;
    //Call from common proxy
    public static void register()
    {
        ForceProjectorControllerCapability.register();
    }


}
