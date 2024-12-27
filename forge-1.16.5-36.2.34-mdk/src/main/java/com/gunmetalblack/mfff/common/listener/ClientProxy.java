package com.gunmetalblack.mfff.common.listener;

import com.gunmetalblack.mfff.common.block.machine.IScreenMachine;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import com.gunmetalblack.mfff.common.reg.EntityReg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void updateScreenMachine() {
        if (Minecraft.getInstance().screen instanceof IScreenMachine) {
            ((IScreenMachine)Minecraft.getInstance().screen).updateGui();
        }

    }
    @SuppressWarnings("unchecked")
    public void onClientSetupEvent(FMLClientSetupEvent event) {
        // Register Container Screens
        RenderingRegistry.registerEntityRenderingHandler(EntityReg.DISPLAY_TEXT.get(), manager -> new ArmorStandRenderer(manager));
        RenderTypeLookup.setRenderLayer(BlockRegister.FORCE_BLOCK.get(), RenderType.translucent());
    }
        @Override
    public double getTileEntityUpdateDistance() {
        double l1Distance = (Minecraft.getInstance().options.renderDistance + 1) * 16;
        return Math.sqrt(l1Distance * l1Distance + l1Distance * l1Distance);
    }
}
