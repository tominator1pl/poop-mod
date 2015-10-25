package tominator1.poop.common.Guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import tominator1.poop.common.Containers.ContainerIngotCaster;
import tominator1.poop.common.Tiles.TileIngotCaster;

public class GuiIngotCaster extends GuiContainer{
	public static final int GUI_ID = 21;
	public TileIngotCaster tile;
	
	 public GuiIngotCaster(InventoryPlayer inventoryPlayer, TileIngotCaster tile2) {
		 	super(new ContainerIngotCaster(inventoryPlayer, tile2));
			tile = tile2;
			//xSize = 176;
			//ySize = 207;
	 }
	 
	 @Override
	    public boolean doesGuiPauseGame() {
	        return false;
	    }
	 
	 @Override
     protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		 fontRendererObj.drawString(StatCollector.translateToLocal("tile.ingotCaster.name"), 8, 6, 4210752);
		 fontRendererObj.drawString(StatCollector.translateToLocal(tile.tankPoop.getFluid().getUnlocalizedName())+ ": " + tile.tankPoop.getFluidAmount()+" / 4000mB", 8, 18, 4210752);
		 fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	 }
	 
	 @Override
	 protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
             //this.drawDefaultBackground();
             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             this.mc.renderEngine.bindTexture(new ResourceLocation("kupa:textures\\gui\\generic_ui.png"));
             int x = (width - xSize) / 2;
             int y = (height - ySize) / 2;
             this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
             this.drawTexturedModalRect(x+79, y+34, 0, ySize, 18, 18);
     }
}

