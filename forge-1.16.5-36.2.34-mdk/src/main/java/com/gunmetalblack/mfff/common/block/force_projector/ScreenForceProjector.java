package com.gunmetalblack.mfff.common.block.force_projector;

import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.block.machine.ScreenMachine;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenForceProjector extends ScreenMachine<ContainerForceProjector> {

    public static final ResourceLocation FORCE_PROJECTOR_BACKGROUND_TEXTURE = new ResourceLocation(MFFF.MODID, "textures/gui/gui_force_projector.png");

    public ScreenForceProjector(ContainerForceProjector container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name, FORCE_PROJECTOR_BACKGROUND_TEXTURE, 176, 166);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        drawHorizontallyCenteredStringNoShadow(matrixStack, this.font, new TranslationTextComponent("gui.mfff.force_projector.place"),
                1, 32, MFFF.MFFFTextColors.DARK_GRAY.code);
    }

    @Override
    public void renderSlotBackgrounds(MatrixStack matrixStack, int relX, int relY) {

    }

}