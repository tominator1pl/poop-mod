package tominator1.poop.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileToilet extends TileEntity{
	public FluidTank tankWater = new FluidTank(FluidRegistry.WATER, 0, FluidContainerRegistry.BUCKET_VOLUME);
	public FluidTank tankPoop = new FluidTank(mod_poop.liquidPoop, 0, FluidContainerRegistry.BUCKET_VOLUME);

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

	public int fill(FluidStack resource){
		if (resource == null) {
			return 0;
		}
		if(tankWater.getFluidAmount() == FluidContainerRegistry.BUCKET_VOLUME){
			return 0;
		}
		if(resource.getFluid() == FluidRegistry.WATER && resource.amount == FluidContainerRegistry.BUCKET_VOLUME){
			tankWater.fill(resource, true);
			return FluidContainerRegistry.BUCKET_VOLUME;
		}
		return 0;
	}
}
