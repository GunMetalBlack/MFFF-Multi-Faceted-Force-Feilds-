package com.gunmetalblack.mfff.common.listener;

import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.capability.forceprojector.ForceProjectorControllerCapability;
import com.gunmetalblack.mfff.common.capability.forceprojector.ForceProjectorControllerCapabilityProvider;
import com.gunmetalblack.mfff.common.capability.MFFFCapabilites;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CommonProxy {
    public void updateScreenMachine() { }
    public void onCommonSetupEvent(final FMLCommonSetupEvent event) {
        MFFFCapabilites.register();
    }
    public void onClientSetupEvent(final FMLClientSetupEvent event) {}
    public double getTileEntityUpdateDistance() {
        MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
        if(!(minecraftServer instanceof DedicatedServer)) return 64D; // Can't think of any circumstance where this would apply, but worth a check.
        double l1Distance = (((DedicatedServer)minecraftServer).getProperties().viewDistance + 1) * 16;
        return Math.sqrt(l1Distance * l1Distance + l1Distance * l1Distance);
    }
    public void onAttachCapabilitiesEventWorld(final AttachCapabilitiesEvent<World> event) {
        if(!event.getObject().isClientSide()) {
            event.addCapability(new ResourceLocation(MFFF.MODID, "force_projector"), new ForceProjectorControllerCapabilityProvider());
        }
    }
    public void onTickEvent(final TickEvent.WorldTickEvent event)
    {
        if(!event.world.isClientSide() && event.phase == TickEvent.Phase.START)
        {
            event.world.getCapability(MFFFCapabilites.FORCE_PROJECTOR_CAPABILITY).ifPresent(cap -> cap.onTickEvent(event));
        }
    }
}
