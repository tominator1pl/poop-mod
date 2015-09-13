package tominator1.poop.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBucket;

public class ItemPoopBucket extends ItemBucket{

	public ItemPoopBucket(Block p_i45331_1_) {
		super(p_i45331_1_);
		this.setCreativeTab(mod_poop.tabShit);
		this.setUnlocalizedName("poopBucket");
	}

	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir){
    	this.itemIcon = ir.registerIcon("kupa:bucketKupa");
    }
}
