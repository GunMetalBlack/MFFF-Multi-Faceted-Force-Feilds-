package com.gunmetalblack.mfff.common.reg;

import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.block.force_projector.BlockForceProjector;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MFFF.MODID);
    public static final RegistryObject<Block> FORCE_PROJECTOR = BLOCKS.register("force_projector", () -> new BlockForceProjector(AbstractBlock.Properties.of(Material.STONE).noOcclusion(),TileRegister.FORCE_PROJECTOR));
}
