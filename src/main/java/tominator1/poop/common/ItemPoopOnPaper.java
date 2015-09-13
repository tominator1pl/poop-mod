package tominator1.poop.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemPoopOnPaper extends Item {
	public ItemPoopOnPaper(){
		this.setCreativeTab(mod_poop.tabShit);
	}
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir){
    	this.itemIcon = ir.registerIcon("kupa:pkupa");
    }
}
