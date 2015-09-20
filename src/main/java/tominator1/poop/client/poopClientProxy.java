package tominator1.poop.client;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import tominator1.poop.common.poopCommonProxy;

public class poopClientProxy extends poopCommonProxy{

	public EntityPlayer getPlayerEntity(MessageContext ctx) {

	 return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
}
