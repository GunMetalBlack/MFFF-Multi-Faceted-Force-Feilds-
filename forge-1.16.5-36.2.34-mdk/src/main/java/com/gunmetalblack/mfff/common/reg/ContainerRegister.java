package com.gunmetalblack.mfff.common.reg;
import com.gunmetalblack.mfff.common.MFFF;
import com.gunmetalblack.mfff.common.block.force_projector.ContainerForceProjector;
import com.gunmetalblack.mfff.common.block.machine.AbstractContainerMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ContainerRegister {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MFFF.MODID);
    public static final RegistryObject<ContainerType<ContainerForceProjector>>
            FORCE_PROJECTOR = registerContainer(BlockRegister.FORCE_PROJECTOR, ContainerForceProjector::new, () -> ContainerRegister.FORCE_PROJECTOR);
    @FunctionalInterface
    public interface IModContainerFactory<T extends AbstractContainerMachine> {
        T construct(ContainerType<T> containerType, int windowId, World level, BlockPos blockPos, PlayerInventory playerInventory);
    }
    public static <T extends AbstractContainerMachine> RegistryObject<ContainerType<T>> registerContainer(RegistryObject<Block> block, IModContainerFactory<T> containerConstructor, Supplier<RegistryObject<ContainerType<T>>> self) {
        return CONTAINERS.register(
                block.getId().getPath(),
                () -> {
                    return IForgeContainerType.create(
                            (windowId, inv, data) -> {
                                BlockPos pos = data.readBlockPos();
                                World world = inv.player.getCommandSenderWorld();
                                return containerConstructor.construct(self.get().get(), windowId, world, pos, inv);
                            }
                    );
                }
        );
    }
}
