package tominator1.poop.common.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Tiles.TileIngotCaster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerIngotCaster extends Container{
	private TileIngotCaster caster;
	private int tankPoopAmount;
	private int tankPoopID;
	

	public ContainerIngotCaster(InventoryPlayer inventoryPlayer, TileIngotCaster tile){
		caster = tile;
		this.addSlotToContainer(new SlotFurnace(inventoryPlayer.player,tile, 0, 80, 35));
		
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
	        iCrafting.sendProgressBarUpdate(this, 0, this.caster.tankPoop.getFluidAmount());
	        iCrafting.sendProgressBarUpdate(this, 1, this.caster.tankPoop.getFluid().getFluidID());
	    }
	
	 
	 public void detectAndSendChanges()
	    {
	        super.detectAndSendChanges();

	        for (int i = 0; i < this.crafters.size(); ++i)
	        {
	            ICrafting icrafting = (ICrafting)this.crafters.get(i);

	            if (this.tankPoopAmount != this.caster.tankPoop.getFluidAmount())
	            {
	                icrafting.sendProgressBarUpdate(this, 0, this.caster.tankPoop.getFluidAmount());
	            }
	            if (this.tankPoopID != this.caster.tankPoop.getFluid().getFluidID())
	            {
	                icrafting.sendProgressBarUpdate(this, 1, this.caster.tankPoop.getFluid().getFluidID());
	            }
	        }
	        this.tankPoopAmount = this.caster.tankPoop.getFluidAmount();
	        this.tankPoopID = this.caster.tankPoop.getFluid().getFluidID();
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int par1, int par2)
	    {
	        if (par1 == 0)
	        {
	        	this.caster.tankPoop.setFluid(new FluidStack(this.caster.tankPoop.getFluid().getFluid(), par2));
	        }
	        if (par1 == 1)
	        {
	        	this.caster.tankPoop.setFluid(new FluidStack(FluidRegistry.getFluid(par2), this.caster.tankPoop.getFluidAmount()));
	        }

	    }
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return caster.isUseableByPlayer(entityPlayer);
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
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
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
