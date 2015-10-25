package tominator1.poop.common;

import tominator1.poop.common.Containers.ContainerAutoToilet;
import tominator1.poop.common.Containers.ContainerIngotCaster;
import tominator1.poop.common.Guis.GuiAutoToilet;
import tominator1.poop.common.Guis.GuiIngotCaster;
import tominator1.poop.common.Tiles.TileAutoToilet;
import tominator1.poop.common.Tiles.TileIngotCaster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class poopCommonProxy implements IGuiHandler{

	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileAutoToilet){
            return new ContainerAutoToilet(player.inventory, (TileAutoToilet) tileEntity);
		}
		if(tileEntity instanceof TileIngotCaster){
            return new ContainerIngotCaster(player.inventory, (TileIngotCaster) tileEntity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileAutoToilet){
            return new GuiAutoToilet(player.inventory, (TileAutoToilet) tileEntity);
		}
		if(tileEntity instanceof TileIngotCaster){
            return new GuiIngotCaster(player.inventory, (TileIngotCaster) tileEntity);
		}
    
    return null;
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		 return ctx.getServerHandler().playerEntity;
		}
}
