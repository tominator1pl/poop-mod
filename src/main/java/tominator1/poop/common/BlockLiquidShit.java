package tominator1.poop.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockLiquidShit extends BlockFluidClassic{
	
	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
	
	public BlockLiquidShit(Fluid fluid, Material material){
		super(fluid, material);
		setBlockName("liquidPoop");
		setCreativeTab(mod_poop.tabShit);
	}
	
	@Override
    public IIcon getIcon(int side, int meta) {
            return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public void registerBlockIcons(IIconRegister register) {
            stillIcon = register.registerIcon("kupa:liquidKupa_still");
            flowingIcon = register.registerIcon("kupa:liquidKupa_flow");
            mod_poop.liquidPoop.setIcons(mod_poop.liquidPoopBlock.getBlockTextureFromSide(0), mod_poop.liquidPoopBlock.getBlockTextureFromSide(1));
    }
	
	@Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return true;
            return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.displaceIfPossible(world, x, y, z);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity){
		  if(entity instanceof EntityLivingBase){
			  ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 250, 3)); //100 = 5 Seconds , 20 = 1 Second
			  
		  }	  
	  }
}
