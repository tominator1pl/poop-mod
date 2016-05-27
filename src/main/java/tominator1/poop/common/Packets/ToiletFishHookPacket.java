package tominator1.poop.common.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.Fluid;
import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Tiles.TileIngotCaster;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ToiletFishHookPacket implements IMessage{
	
	private double xCord;
	private double yCord;
	private double zCord;
	private int fishHookID;
	
	public ToiletFishHookPacket(){}
	
	public ToiletFishHookPacket(EntityFishHook fishHook){
		xCord = fishHook.posX;
		yCord = fishHook.posY;
		zCord = fishHook.posZ;
		fishHookID = fishHook.getEntityId();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		xCord = buf.readDouble();
		yCord = buf.readDouble();
		zCord = buf.readDouble();
		fishHookID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(xCord);
		buf.writeDouble(yCord);
		buf.writeDouble(zCord);
		buf.writeInt(fishHookID);
	}

	public static class Handler implements IMessageHandler<ToiletFishHookPacket, IMessage>{

		@Override
		public IMessage onMessage(ToiletFishHookPacket message, MessageContext ctx) {
			EntityPlayer playerMP = mod_poop.proxy.getPlayerEntity(ctx);
			EntityFishHook fishHook = (EntityFishHook) playerMP.worldObj.getEntityByID(message.fishHookID);
			if(fishHook != null){
				fishHook.setPosition(message.xCord, message.yCord, message.zCord);	
			}
			return null;
		}
		
	}

}
