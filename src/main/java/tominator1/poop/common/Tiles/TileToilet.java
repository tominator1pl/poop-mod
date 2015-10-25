package tominator1.poop.common.Tiles;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Blocks.BlockToilet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileToilet extends TileEntity implements IFluidHandler{
	public FluidTank tankWater = new FluidTank(FluidRegistry.WATER, 0, FluidContainerRegistry.BUCKET_VOLUME);
	public FluidTank tankPoop = new FluidTank(mod_poop.liquidPoop, 0, FluidContainerRegistry.BUCKET_VOLUME);
	public BlockToilet bloczek;

	@Override
	public void writeToNBT(NBTTagCompound data)
	  {
		data.setInteger("tankWaterAmount", tankWater.getFluidAmount());
		data.setInteger("tankPoopAmount", tankPoop.getFluidAmount());
		super.writeToNBT(data);
	  }
	
	@Override
	public void readFromNBT(NBTTagCompound data)
	  {
		tankWater.fill(new FluidStack(FluidRegistry.WATER, data.getInteger("tankWaterAmount")), true);
		tankPoop.fill(new FluidStack(mod_poop.liquidPoop, data.getInteger("tankPooopAmount")), true);
		super.readFromNBT(data);
	  }
	
	

	@Override
	public int fill(ForgeDirection from, FluidStack resource2, boolean doFill){
		bloczek =  (BlockToilet) worldObj.getBlock(xCoord, yCoord, zCoord);
		if (resource2 == null) {
			return 0;
		}
		FluidStack resourceCopy = resource2.copy();
		int totalUsed = 0;
		FluidTank tankToFill = tankWater;
		if(tankWater.getFluidAmount() == FluidContainerRegistry.BUCKET_VOLUME){
			return 0;
		}
		FluidStack liquid = tankToFill.getFluid();
		if (!resourceCopy.isFluidEqual(new FluidStack(FluidRegistry.WATER,1))) {
			return 0;
		}
		while (tankToFill != null && resourceCopy.amount > 0 && tankToFill.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME) {
			int used = tankToFill.fill(resourceCopy, doFill);
			resourceCopy.amount -= used;
			
			if (used > 0) {
			}

			totalUsed += used;
		}
		bloczek.metaUpdated(this, worldObj, xCoord, yCoord, zCoord);
		return totalUsed;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain){
		if(from == ForgeDirection.UNKNOWN){
			FluidStack r = tankWater.drain(maxEmpty, doDrain);
			return r;
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(tankWater.getFluid())) {
			return null;
		}
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return null;
	}
}
