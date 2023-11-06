package com.gunmetalblack.mfff.common;

import com.gunmetalblack.mfff.common.listener.ClientProxy;
import com.gunmetalblack.mfff.common.listener.CommonProxy;
import com.gunmetalblack.mfff.common.reg.BlockRegister;
import com.gunmetalblack.mfff.common.reg.ItemRegister;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
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

        for(DeferredRegister<?> deferredRegister : getMFFFDeferredRegisters()){
            deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }

    public DeferredRegister<?>[] getMFFFDeferredRegisters() {
        return new DeferredRegister[] {
                BlockRegister.BLOCKS,
                ItemRegister.ITEMS
        };
    }
}
