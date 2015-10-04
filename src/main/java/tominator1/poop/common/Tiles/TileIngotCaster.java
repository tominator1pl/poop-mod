package tominator1.poop.common.Tiles;

import java.util.List;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Blocks.BlockIngotCaster;
import tominator1.poop.common.Packets.IngotCasterPacket;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileIngotCaster extends TileEntity implements IFluidHandler, ISidedInventory{
	public FluidTank tankPoop = new FluidTank(mod_poop.liquidPoop, 0, FluidContainerRegistry.BUCKET_VOLUME*4);
	private ItemStack[] ingotCasterItemStacks = new ItemStack[1];
	private static final int[] SLOTS = new int[]{0};
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	public void updateEntity(){
		if(!worldObj.isRemote){
		if (this.worldObj.getTotalWorldTime() % 20L == 0L)
        {
			TileEntity tile3 = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
			if(tile3 != null){
				if(tile3 instanceof IFluidHandler){
					int emptyness = tankPoop.getCapacity() - tankPoop.getFluidAmount();
					FluidStack drained = ((IFluidHandler) tile3).drain(ForgeDirection.DOWN, new FluidStack(mod_poop.liquidPoop, emptyness), true);
					tankPoop.fill(drained, true);
				}
			}
			if(tankPoop.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME){
				if (this.ingotCasterItemStacks[0] != null && this.ingotCasterItemStacks[0].isItemEqual(new ItemStack(mod_poop.poop))){
					if(this.ingotCasterItemStacks[0].stackSize < getInventoryStackLimit()){
						this.ingotCasterItemStacks[0].stackSize += 1;
						tankPoop.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
					}
				}else{
					this.ingotCasterItemStacks[0] = new ItemStack(mod_poop.poop,1,0);
					tankPoop.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
				}
			}
			mod_poop.network.sendToAllAround(new IngotCasterPacket(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 5));
        }
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data)
	  {
		data.setInteger("tankPoopAmount", tankPoop.getFluidAmount());
		NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.ingotCasterItemStacks.length; ++i)
        {
            if (this.ingotCasterItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.ingotCasterItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        data.setTag("Items", nbttaglist);
		super.writeToNBT(data);
	  }
	
	@Override
	public void readFromNBT(NBTTagCompound data)
	  {
		tankPoop.fill(new FluidStack(mod_poop.liquidPoop, data.getInteger("tankPooopAmount")), true);
		NBTTagList nbttaglist = data.getTagList("Items", 10);
        this.ingotCasterItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.ingotCasterItemStacks.length)
            {
                this.ingotCasterItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
		super.readFromNBT(data);
	  }

	@Override
	public int fill(ForgeDirection from, FluidStack resource2, boolean doFill){
		if (resource2 == null) {
			return 0;
		}
		FluidStack resourceCopy = resource2.copy();
		int totalUsed = 0;
		FluidTank tankToFill = tankPoop;
		if(tankPoop.getFluidAmount() == FluidContainerRegistry.BUCKET_VOLUME*4){
			return 0;
		}
		FluidStack liquid = tankToFill.getFluid();
		if (!resourceCopy.isFluidEqual(new FluidStack(mod_poop.liquidPoop,1))) {
			return 0;
		}
		while (tankToFill != null && resourceCopy.amount > 0 && tankToFill.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME*4) {
			int used = tankToFill.fill(resourceCopy, doFill);
			resourceCopy.amount -= used;
			
			if (used > 0) {
			}

			totalUsed += used;
		}
		return totalUsed;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain){
		if(from == ForgeDirection.UNKNOWN){
			FluidStack r = tankPoop.drain(maxEmpty, doDrain);
			return r;
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(tankPoop.getFluid())) {
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

	@Override
	public int getSizeInventory() {
		return this.ingotCasterItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.ingotCasterItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.ingotCasterItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.ingotCasterItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.ingotCasterItemStacks[par1];
                this.ingotCasterItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.ingotCasterItemStacks[par1].splitStack(par2);

                if (this.ingotCasterItemStacks[par1].stackSize == 0)
                {
                    this.ingotCasterItemStacks[par1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.ingotCasterItemStacks[par1] != null)
        {
            ItemStack itemstack = this.ingotCasterItemStacks[par1];
            this.ingotCasterItemStacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2) {
		
		this.ingotCasterItemStacks[par1] = par2;

        if (par2 != null && par2.stackSize > this.getInventoryStackLimit())
        {
        	par2.stackSize = this.getInventoryStackLimit();
        }
	}

	@Override
	public String getInventoryName() {
		return "container.ingotCaster";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return SLOTS;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return true;
	}

}