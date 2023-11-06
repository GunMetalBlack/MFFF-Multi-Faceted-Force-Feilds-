package com.gunmetalblack.mfff.common.listener;

import com.gunmetalblack.mfff.common.block.machine.IScreenMachine;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {
    @Override
    public void updateScreenMachine() {
        if (Minecraft.getInstance().screen instanceof IScreenMachine) {
            ((IScreenMachine)Minecraft.getInstance().screen).updateGui();
        }

    }
    @Override
    public double getTileEntityUpdateDistance() {
        double l1Distance = (Minecraft.getInstance().options.renderDistance + 1) * 16;
        return Math.sqrt(l1Distance * l1Distance + l1Distance * l1Distance);
    }
}
