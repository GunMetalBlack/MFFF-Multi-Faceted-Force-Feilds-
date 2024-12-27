package com.gunmetalblack.mfff.common;

import com.gunmetalblack.mfff.common.listener.ClientProxy;
import com.gunmetalblack.mfff.common.listener.CommonProxy;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import com.gunmetalblack.mfff.common.reg.EntityReg;
import com.gunmetalblack.mfff.common.reg.ItemRegister;
import com.gunmetalblack.mfff.common.reg.TileRegister;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mfff")
public class MFFF
{
    public static final CommonProxy distProxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    @CapabilityInject(IEnergyStorage.class)
    public static Capability<IEnergyStorage> FORGE_ENERGY_CAPABILITY;

    public static final String MODID = "mfff";
    public static final ItemGroup MFFF_ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack makeIcon() {
            return ItemRegister.FORCE_PROJECTOR.get().getDefaultInstance();
        }
    };
    private static final Logger LOGGER = LogManager.getLogger();

    public MFFF() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.addGenericListener(World.class,this::onAttachCapabilitiesEventWorld);
        MinecraftForge.EVENT_BUS.addListener(this::onTickEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetupEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetupEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEntityAttributeCreationEvent);

        for(DeferredRegister<?> deferredRegister : getMFFFDeferredRegisters()){
            deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
    @SubscribeEvent
    public void onEntityAttributeCreationEvent(EntityAttributeCreationEvent event)
    {
        MFFF.distProxy.onEntityAttributeCreationEvent(event);
    }
    @SubscribeEvent
    public void onClientSetupEvent(final FMLClientSetupEvent event) {
        MFFF.distProxy.onClientSetupEvent(event);
    }
    @SubscribeEvent
    public void onCommonSetupEvent(final FMLCommonSetupEvent event) {
        MFFF.distProxy.onCommonSetupEvent(event);
    }
    @SubscribeEvent
    public void onAttachCapabilitiesEventWorld(final AttachCapabilitiesEvent<World> event) {
        MFFF.distProxy.onAttachCapabilitiesEventWorld(event);
    }

    @SubscribeEvent
    public void onTickEvent(final TickEvent.WorldTickEvent event)
    {
        MFFF.distProxy.onTickEvent(event);
    }

    public static enum MFFFTextColors {
        LIGHT_GRAY(0x8f8f8f),
        DARK_GRAY(0x363636);
        public final int code;
        MFFFTextColors(int code) {
            this.code = code;
        }
    }
    public DeferredRegister<?>[] getMFFFDeferredRegisters() {
        return new DeferredRegister[] {
                BlockRegister.BLOCKS,
                ItemRegister.ITEMS,
                TileRegister.TILES,
                EntityReg.ENTITIES
        };
    }
}
