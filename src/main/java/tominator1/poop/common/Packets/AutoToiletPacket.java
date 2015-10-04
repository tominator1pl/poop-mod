package tominator1.poop.common.Packets;

import tominator1.poop.client.poopClientProxy;
import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Tiles.TileAutoToilet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class AutoToiletPacket implements IMessage{
	
	private int xCord;
	private int yCord;
	private int zCord;
	private int tankWaterAmount;
	private int tankPoopAmount;
	private int tankMobsLength;
	
	public AutoToiletPacket(){}
	
	public AutoToiletPacket(TileAutoToilet autoToilet){
		xCord = autoToilet.xCoord;
		yCord = autoToilet.yCoord;
		zCord = autoToilet.zCoord;
		tankWaterAmount = autoToilet.tankWater.getFluidAmount();
		tankPoopAmount = autoToilet.tankPoop.getFluidAmount();
		tankMobsLength = autoToilet.mobsLength;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		xCord = buf.readInt();
		yCord = buf.readInt();
		zCord = buf.readInt();
		tankWaterAmount = buf.readInt();
		tankPoopAmount = buf.readInt();
		tankMobsLength = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCord);
		buf.writeInt(yCord);
		buf.writeInt(zCord);
		buf.writeInt(tankWaterAmount);
		buf.writeInt(tankPoopAmount);
		buf.writeInt(tankMobsLength);
	}

	public static class Handler implements IMessageHandler<AutoToiletPacket, IMessage>{

		@Override
		public IMessage onMessage(AutoToiletPacket message, MessageContext ctx) {
			EntityPlayer playerMP = mod_poop.proxy.getPlayerEntity(ctx);
			TileAutoToilet autoToilet = (TileAutoToilet) playerMP.worldObj.getTileEntity(message.xCord, message.yCord, message.zCord);
			if(autoToilet != null){
				autoToilet.tankWater.setFluid(new FluidStack(FluidRegistry.WATER, message.tankWaterAmount));
				autoToilet.tankPoop.setFluid(new FluidStack(mod_poop.liquidPoop, message.tankPoopAmount));
				autoToilet.mobsLength = message.tankMobsLength;
			}
			return null;
		}
		
	}

}
