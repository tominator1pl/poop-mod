package tominator1.poop.common.Blocks;

import tominator1.poop.common.mod_poop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPoop extends Block {
	public BlockPoop() {
		super(Material.ground);
		this.setCreativeTab(mod_poop.tabShit);
		this.setHardness(0.5F);
		this.setResistance(1.0F);
		this.setStepSound(Block.soundTypeGravel);
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister var1)
    {
        this.blockIcon = var1.registerIcon("kupa:kupa");
    }
}
