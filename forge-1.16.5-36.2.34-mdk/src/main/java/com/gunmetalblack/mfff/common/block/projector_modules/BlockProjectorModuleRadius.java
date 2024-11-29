package com.gunmetalblack.mfff.common.block.projector_modules;

import com.gunmetalblack.mfff.common.reg.BlockRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;

public class BlockProjectorModuleRadius extends ProjectorModuleParent{

    public BlockProjectorModuleRadius(boolean waterloggable) {
        super(waterloggable);
    }
    /*
    * Make parent module:
    *  if a projector is found save the direction its facing
    *  Only if the direction its placed is in the correct direction and is in contact with the module also check if the block infront and behind have the parent instance if not pass
    *   The parent saves a instance of itself which for every child placed will be passed on
    *   Each Module will add a +1 to the multiplier
    * */

    public BlockState getBlockStateRelative(World worldIn, BlockPos pos, BlockState state, int var)
    {
        return worldIn.getBlockState(pos.relative(state.getValue(ProjectorModuleParent.FACING), var));
    }

    public void setProjectorValue(int radiusMultiplyer)
    {
        System.out.println("RADIUS:" + radiusMultiplyer);
    }

    public void onModulePropagate(World worldIn, BlockPos pos, BlockState state)
    {
        int amountOfModules = 0;
        boolean hasNotFinishedComputation = true;
        while(hasNotFinishedComputation) {
            //Checks the block infront if its a projector
            if (getBlockStateRelative(worldIn, pos, state, 1).getBlock().equals(BlockRegister.FORCE_PROJECTOR) && !(getBlockStateRelative(worldIn, pos, state, -1).getBlock().equals(BlockRegister.PROJECTOR_MODULE_RADIUS)))
            {
                hasNotFinishedComputation = false;
                amountOfModules++;
            }
            //Checks above and behind
            else if (getBlockStateRelative(worldIn, pos, state, -1).getBlock().equals(BlockRegister.FORCE_PROJECTOR) && !(getBlockStateRelative(worldIn, pos, state, 1).getBlock().equals(BlockRegister.PROJECTOR_MODULE_RADIUS)))
            {
                hasNotFinishedComputation = false;
                amountOfModules++;
            }else
            {
                //Checks if the block is a force projector is not then keep searching the path of modules till projector is found
                if(getBlockStateRelative(worldIn, pos, state, amountOfModules).getBlock().equals(BlockRegister.FORCE_PROJECTOR)) {
                    hasNotFinishedComputation = false;
                }
                else
                {
                    //if Its the last Module Loop through and count the modules
                    if (getBlockStateRelative(worldIn, pos, state, amountOfModules).getBlock().equals(BlockRegister.PROJECTOR_MODULE_RADIUS) && !(getBlockStateRelative(worldIn, pos, state, -1).getBlock().equals(BlockRegister.PROJECTOR_MODULE_RADIUS))) {
                        amountOfModules++;
                    }
//                    else if (getBlockStateRelative(worldIn, pos, state, amountOfModules).getBlock().equals(BlockRegister.PROJECTOR_MODULE_RADIUS) && !(getBlockStateRelative(worldIn, pos, state, -1).getBlock().equals(BlockRegister.PROJECTOR_MODULE_RADIUS)))
//                    {
//
//                    }
                }
            }

        }
        setProjectorValue(Math.abs(amountOfModules));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemInHandOfPlacer) {
        super.setPlacedBy(worldIn, pos, state, placer, itemInHandOfPlacer);
        if(!worldIn.isClientSide) onModulePropagate(worldIn, pos, state);

    }

}
