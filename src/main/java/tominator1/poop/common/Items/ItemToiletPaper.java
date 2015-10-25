package tominator1.poop.common.Items;

import tominator1.poop.common.mod_poop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemToiletPaper extends Item{
	public ItemToiletPaper(){
		this.setCreativeTab(mod_poop.tabShit);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir){
    	this.itemIcon = ir.registerIcon("kupa:pp");
    }
}
