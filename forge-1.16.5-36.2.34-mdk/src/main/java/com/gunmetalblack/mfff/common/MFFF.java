package com.gunmetalblack.mfff.common;

import com.gunmetalblack.mfff.common.listener.ClientProxy;
import com.gunmetalblack.mfff.common.listener.CommonProxy;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import com.gunmetalblack.mfff.common.reg.ContainerRegister;
import com.gunmetalblack.mfff.common.reg.ItemRegister;
import com.gunmetalblack.mfff.common.reg.TileRegister;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

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
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetupEvent);

        for(DeferredRegister<?> deferredRegister : getMFFFDeferredRegisters()){
            deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }

    @SubscribeEvent
    public void onClientSetupEvent(final FMLClientSetupEvent event) {
        MFFF.distProxy.onClientSetupEvent(event);
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
                ContainerRegister.CONTAINERS,
                ItemRegister.ITEMS,
                TileRegister.TILES
        };
    }
}
