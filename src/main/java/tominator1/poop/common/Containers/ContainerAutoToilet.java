package tominator1.poop.common.Containers;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Tiles.TileAutoToilet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ContainerAutoToilet extends Container{
	private TileAutoToilet tank;
	private int tankWaterAmount;
	private int tankPoopAmount;
	private int tankMobsLength;
	

	public ContainerAutoToilet(InventoryPlayer inventoryPlayer, TileAutoToilet tile){
		tank = tile;
		
		for (int i = 0; i < 3; i++)
		 {
			 for (int k = 0; k < 9; k++)
			 {
				 addSlotToContainer(new Slot(inventoryPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			 }
		 }

		 for (int j = 0; j < 9; j++)
		 {
			 addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 142));
		 }
	}
	
	 public void addCraftingToCrafters(ICrafting iCrafting)
	    {
	        super.addCraftingToCrafters(iCrafting);
	        iCrafting.sendProgressBarUpdate(this, 0, this.tank.tankWater.getFluidAmount());
	        iCrafting.sendProgressBarUpdate(this, 1, this.tank.tankPoop.getFluidAmount());
	        iCrafting.sendProgressBarUpdate(this, 2, this.tank.mobsLength);
	    }
	
	 
	 public void detectAndSendChanges()
	    {
	        super.detectAndSendChanges();

	        for (int i = 0; i < this.crafters.size(); ++i)
	        {
	            ICrafting icrafting = (ICrafting)this.crafters.get(i);

	            if (this.tankWaterAmount != this.tank.tankWater.getFluidAmount())
	            {
	                icrafting.sendProgressBarUpdate(this, 0, this.tank.tankWater.getFluidAmount());
	            }

	            if (this.tankPoopAmount != this.tank.tankPoop.getFluidAmount())
	            {
	                icrafting.sendProgressBarUpdate(this, 1, this.tank.tankPoop.getFluidAmount());
	            }
	            if (this.tankMobsLength != this.tank.mobsLength)
	            {
	                icrafting.sendProgressBarUpdate(this, 2, this.tank.mobsLength);
	            }
	        }

	        this.tankWaterAmount = this.tank.tankWater.getFluidAmount();
	        this.tankPoopAmount = this.tank.tankPoop.getFluidAmount();
	        this.tankMobsLength = this.tank.mobsLength;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int par1, int par2)
	    {
	        if (par1 == 0)
	        {
	            this.tank.tankWater.setFluid(new FluidStack(FluidRegistry.WATER, par2));
	        }

	        if (par1 == 1)
	        {
	            this.tank.tankPoop.setFluid(new FluidStack(mod_poop.liquidPoop, par2));
	        }
	        if (par1 == 2)
	        {
	            this.tank.mobsLength = par2;
	        }
	    }
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return tank.isUseableByPlayer(entityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotnum){
		
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotnum);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotnum == 0)
            {
                return null;
            }
            else if (slotnum != 0)
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(entityPlayer, itemstack1);
        }

        return itemstack;
	}
}
