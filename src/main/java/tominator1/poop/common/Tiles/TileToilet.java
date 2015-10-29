package tominator1.poop.common.Tiles;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.NetworkRegistry;
import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Blocks.BlockToilet;
import tominator1.poop.common.Handlers.AutoToiletHandler;
import tominator1.poop.common.Handlers.FishingToiletHandler;
import tominator1.poop.common.Packets.AutoToiletPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileToilet extends TileEntity implements IFluidHandler{
	private final Random rand = new Random();
	
	public FluidTank tankWater = new FluidTank(FluidRegistry.WATER, 0, FluidContainerRegistry.BUCKET_VOLUME);
	public FluidTank tankPoop = new FluidTank(mod_poop.liquidPoop, 0, FluidContainerRegistry.BUCKET_VOLUME);
	public BlockToilet bloczek;
	public EntityFishHook fishHook;
	private int fishHookTime;
	private boolean fishWasSucess = false;

	
	public void updateEntity(){
		if(!worldObj.isRemote){
			WorldServer worldserver = (WorldServer)this.worldObj;
		if (this.worldObj.getTotalWorldTime() % 20L == 0L)
        {
			if(tankWater.getFluidAmount() >= 1000){
				List<EntityFishHook> hooks = this.worldObj.getEntitiesWithinAABB(EntityFishHook.class,AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord+1, this.zCoord, this.xCoord+1, this.yCoord+2, this.zCoord+1));
				for(int p = 0;p < hooks.size(); ++p){
					if(fishHook == null){
						fishHook = hooks.get(p);
						fishHook.setPosition(xCoord+0.5, yCoord+1, zCoord+0.5);
						fishHookTime = 0;
						fishWasSucess = false;
					}else if(fishHook == hooks.get(p)){
						fishHook.setPosition(xCoord+0.5, yCoord+1, zCoord+0.5);
					}else{
						EntityFishHook entityitem = hooks.get(p);
						float f = this.rand.nextFloat() * 0.8F + 0.1F;
	                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
	                    float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
	                    float f3 = 0.05F;
	                    entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
	                    entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
	                    entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
					}
				}
				if(fishHook != null){
					if(fishHook.isDead){
						if(fishWasSucess){
							ItemStack itemekItemStack = FishingToiletHandler.INSTANCE.getRecipe();
							if(itemekItemStack != null){
								EntityItem entityitem = new EntityItem(this.worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, itemekItemStack);
								double d1 = fishHook.field_146042_b.posX - xCoord+0.5;
	                            double d3 = fishHook.field_146042_b.posY - yCoord+1;
	                            double d5 = fishHook.field_146042_b.posZ - zCoord+0.5;
	                            double d7 = (double)MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
	                            double d9 = 0.1D;
	                            entityitem.motionX = d1 * d9;
	                            entityitem.motionY = d3 * d9 + (double)MathHelper.sqrt_double(d7) * 0.08D;
	                            entityitem.motionZ = d5 * d9;
	                            this.worldObj.spawnEntityInWorld(entityitem);
							}
						}
						fishHook = null;
					}else{
						fishHookTime++;
						worldserver.func_147487_a("splash", fishHook.posX, fishHook.posY, fishHook.posZ, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
						int randd = (int) (Math.random()*100);
						if(randd < fishHookTime){							
							fishHook.motionY -= 0.20000000298023224D;
                            fishHook.playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                            float f1 = (float)MathHelper.floor_double(fishHook.boundingBox.minY);
                            worldserver.func_147487_a("bubble", fishHook.posX, (double)(f1 + 1.0F), fishHook.posZ, (int)(1.0F + fishHook.width * 20.0F), (double)fishHook.width, 0.0D, (double)fishHook.width, 0.20000000298023224D);
                            worldserver.func_147487_a("wake", fishHook.posX, (double)(f1 + 1.0F), fishHook.posZ, (int)(1.0F + fishHook.width * 20.0F), (double)fishHook.width, 0.0D, (double)fishHook.width, 0.20000000298023224D);
                            fishHook.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2D;
                            fishWasSucess = true;
						}
					}
				}
			}
        }
		}
	}
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
