package com.gunmetalblack.mfff.common.block.machine;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public interface IScreenMachine {

    // Called on client when server requests a GUI refresh
    default void updateGui() {}

    // Called on client when GUI is closed or save is manually triggered (ex. by a button in the GUI)
    default void sendUpdatePacketToServer() {}

    int getWidth();

    int getHeight();

    int getImageWidth();

    int getImageHeight();

    Minecraft getMinecraft();

    void renderTooltip(MatrixStack pMatrixStack, ITextComponent pText, int pMouseX, int pMouseY);

}