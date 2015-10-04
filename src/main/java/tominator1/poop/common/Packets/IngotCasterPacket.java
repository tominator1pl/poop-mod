package tominator1.poop.common.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Tiles.TileIngotCaster;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class IngotCasterPacket implements IMessage{
	
	private int xCord;
	private int yCord;
	private int zCord;
	private int tankPoopAmount;
	
	public IngotCasterPacket(){}
	
	public IngotCasterPacket(TileIngotCaster ingotCaster){
		xCord = ingotCaster.xCoord;
		yCord = ingotCaster.yCoord;
		zCord = ingotCaster.zCoord;
		tankPoopAmount = ingotCaster.tankPoop.getFluidAmount();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		xCord = buf.readInt();
		yCord = buf.readInt();
		zCord = buf.readInt();
		tankPoopAmount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCord);
		buf.writeInt(yCord);
		buf.writeInt(zCord);
		buf.writeInt(tankPoopAmount);
	}

	public static class Handler implements IMessageHandler<IngotCasterPacket, IMessage>{

		@Override
		public IMessage onMessage(IngotCasterPacket message, MessageContext ctx) {
			EntityPlayer playerMP = mod_poop.proxy.getPlayerEntity(ctx);
			TileIngotCaster ingotCaster = (TileIngotCaster) playerMP.worldObj.getTileEntity(message.xCord, message.yCord, message.zCord);
			if(ingotCaster != null){
				ingotCaster.tankPoop.setFluid(new FluidStack(mod_poop.liquidPoop, message.tankPoopAmount));
			}
			return null;
		}
		
	}

}
