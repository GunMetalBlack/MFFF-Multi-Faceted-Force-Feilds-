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
    public static final RegistryObject<Item> FORCE_PROJECTOR = ITEMS.register("force_projector", () ->
            new BlockItem(BlockRegister.FORCE_PROJECTOR.get(), new Item.Properties().tab(MFFF.MFFF_ITEM_GROUP)));
    public static final RegistryObject<Item> PROJECTOR_MODULE_RADIUS = ITEMS.register("projector_module_radius", () ->
            new BlockItem(BlockRegister.PROJECTOR_MODULE_RADIUS.get(), new Item.Properties().tab(MFFF.MFFF_ITEM_GROUP)));
    public static final RegistryObject<Item> FORCE_CORE = ITEMS.register("force_core",
            () -> new Item(new Item.Properties().tab(MFFF.MFFF_ITEM_GROUP)));
    public static final RegistryObject<Item> HARDENED_REDSTONE = ITEMS.register("hardened_redstone",
            () -> new Item(new Item.Properties().tab(MFFF.MFFF_ITEM_GROUP)));
}
