package com.gunmetalblack.mfff.common.reg;

import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.block.BlockForce;
import com.gunmetalblack.mfff.common.block.force_projector.BlockForceProjector;
import com.gunmetalblack.mfff.common.block.projector_modules.BlockProjectorModuleRadius;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MFFF.MODID);
    public static final RegistryObject<Block> FORCE_PROJECTOR = BLOCKS.register("force_projector", () -> new BlockForceProjector(AbstractBlock.Properties.of(Material.STONE).noOcclusion(), TileRegister.FORCE_PROJECTOR));
    public static final RegistryObject<BlockForce> FORCE_BLOCK = BLOCKS.register("force_block", BlockForce::new);
    public static final RegistryObject<Block> PROJECTOR_MODULE_RADIUS = BLOCKS.register("projector_module_radius", () -> new BlockProjectorModuleRadius(AbstractBlock.Properties.of(Material.STONE).noOcclusion()));
}
