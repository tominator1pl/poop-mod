package tominator1.poop.common.Blocks;

import java.util.Random;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Guis.GuiIngotCaster;
import tominator1.poop.common.Tiles.TileIngotCaster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockIngotCaster extends BlockContainer{
	private final Random rand = new Random();
	
	public BlockIngotCaster() {
		super(Material.iron);
		this.setCreativeTab(mod_poop.tabShit);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setStepSound(Block.soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par1){
		return new TileIngotCaster();
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (super.onBlockActivated(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
			return true;
		}

		ItemStack current = entityplayer.inventory.getCurrentItem();
		if (current != null) {
			
		}else{
			entityplayer.openGui(mod_poop.instance, GuiIngotCaster.GUI_ID, world, i, j, k);
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	private IIcon topTexture;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister var1)
    {
    	this.blockIcon = var1.registerIcon("kupa:casterSide");
    	this.topTexture = var1.registerIcon("kupa:toiletTop0");
    }
    @SideOnly(Side.CLIENT)
    @Override
	public IIcon getIcon(int side, int meta){
        return side == 1 ? this.topTexture : this.blockIcon;
        
	}
    
    public void breakBlock(World world, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
            TileIngotCaster tileentityfurnace = (TileIngotCaster)world.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.rand.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(world, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }

        super.breakBlock(world, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
}
