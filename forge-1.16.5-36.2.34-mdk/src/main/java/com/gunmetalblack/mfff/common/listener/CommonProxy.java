package com.gunmetalblack.mfff.common.listener;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CommonProxy {
    public void updateScreenMachine() { }
    public double getTileEntityUpdateDistance() {
        MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
        if(!(minecraftServer instanceof DedicatedServer)) return 64D; // Can't think of any circumstance where this would apply, but worth a check.
        double l1Distance = (((DedicatedServer)minecraftServer).getProperties().viewDistance + 1) * 16;
        return Math.sqrt(l1Distance * l1Distance + l1Distance * l1Distance);
    }
}
