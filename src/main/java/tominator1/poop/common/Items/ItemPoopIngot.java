package tominator1.poop.common.Items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import tominator1.poop.common.mod_poop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPoopIngot extends Item{
	public ItemPoopIngot(){
		this.setCreativeTab(mod_poop.tabShit);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir){
    	this.itemIcon = ir.registerIcon("kupa:kupai");
    }
}
