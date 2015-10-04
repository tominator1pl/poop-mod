package tominator1.poop.common.Tiles;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import scala.collection.generic.BitOperations.Int;
import scala.tools.nsc.ast.Trees.InjectDerivedValue;
import scala.xml.dtd.impl.WordBerrySethi;
import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Blocks.BlockAutoToilet;
import tominator1.poop.common.Blocks.BlockToilet;
import tominator1.poop.common.Handlers.AutoToiletHandler;
import tominator1.poop.common.Packets.AutoToiletPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileAutoToilet extends TileToilet{

	private BlockAutoToilet bloczek;
	public int mobsLength;
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	public void updateEntity(){
		if(!worldObj.isRemote){
		if (this.worldObj.getTotalWorldTime() % 20L == 0L)
        {
			List mobs = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class,AxisAlignedBB.getBoundingBox(this.xCoord-2, this.yCoord+1, this.zCoord-2, this.xCoord+3, this.yCoord+2, this.zCoord+3));
			int tempMobs = 0;
			for(int p = 0;p < mobs.size(); ++p){
				if(AutoToiletHandler.INSTANCE.isThisMine((EntityAnimal) mobs.get(p), this)){
					tempMobs++;
				}
			}
			mobsLength = tempMobs;
			if(tankPoop.getFluidAmount() < 1000 && tankWater.getFluidAmount() > 0){
				FluidStack i = tankWater.drain(mobsLength, true);
				int j = i.amount;
				tankPoop.fill(new FluidStack(mod_poop.liquidPoop, j), true);
				((BlockToilet) worldObj.getBlock(xCoord, yCoord, zCoord)).metaUpdated(this, worldObj, xCoord, yCoord, zCoord);
			}	
				mod_poop.network.sendToAllAround(new AutoToiletPacket(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 10));
        }
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
