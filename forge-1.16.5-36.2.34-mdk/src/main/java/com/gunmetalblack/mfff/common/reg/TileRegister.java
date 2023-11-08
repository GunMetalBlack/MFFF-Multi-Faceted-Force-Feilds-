package com.gunmetalblack.mfff.common.reg;

import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.block.force_projector.TileForceProjector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Suppression required as IntelliJ is convinced that TileEntityType::build cannot accept null as a DataFixer, even though the vanilla game does this without issue.
@SuppressWarnings("ConstantConditions")
public class TileRegister {
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MFFF.MODID);
    // Launcher Control Panels
    public static final RegistryObject<TileEntityType<? extends TileEntity>> FORCE_PROJECTOR = TILES.register(
            BlockRegister.FORCE_PROJECTOR.getId().getPath(),
            () -> TileEntityType.Builder.of(
                    () -> new TileForceProjector(TileRegister.FORCE_PROJECTOR.get()),
                    BlockRegister.FORCE_PROJECTOR.get()
            ).build(null)
    );
}
