package tominator1.poop.common;

import java.util.Random;

import scala.collection.generic.BitOperations.Int;
import scala.tools.nsc.ast.Trees.InjectDerivedValue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileAutoToilet extends TileToilet{

	private int convertSpeed = 1;
	private BlockAutoToilet bloczek;
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	public void updateEntity(){
		if (this.worldObj.getTotalWorldTime() % 80L == 0L)
        {
			if(tankPoop.getFluidAmount() < 1000 && tankWater.getFluidAmount() > 0){
				FluidStack i = tankWater.drain(convertSpeed, true);
				int j = i.amount;
				tankPoop.fill(new FluidStack(mod_poop.liquidPoop, j), true);
				((BlockToilet) worldObj.getBlock(xCoord, yCoord, zCoord)).metaUpdated(this, worldObj, xCoord, yCoord, zCoord);
			}
		/*FluidStack i = drain(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, convertSpeed), true);
		if(i != null){
			int j = i.amount;
			tankPoop.fill(new FluidStack(mod_poop.liquidPoop, j), true);
		}else{
			tankWater.fill(new FluidStack(FluidRegistry.WATER, 1), true);
		}*/
        }
	}
	
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain){
		if(from == ForgeDirection.UNKNOWN){
			FluidStack r = tankWater.drain(maxEmpty, doDrain);	
			return r;
		}else if(from == ForgeDirection.DOWN){
			FluidStack r = tankPoop.drain(maxEmpty, doDrain);	
			return r;
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,boolean doDrain) {
		if (resource == null) {
			return null;
		}
		return drain(from, resource.amount, doDrain);
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}
	
}
