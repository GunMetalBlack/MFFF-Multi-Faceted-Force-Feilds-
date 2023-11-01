package com.gunmetalblack.mfff.common.reg;

import com.gunmetalblack.mfff.common.MFFF;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MFFF.MODID);
    public static final RegistryObject<Item> FORCE_PROJECTOR = ITEMS.register("force_projector", () -> new BlockItem(BlockRegister.FORCE_PROJECTOR.get(), new Item.Properties()));
}
