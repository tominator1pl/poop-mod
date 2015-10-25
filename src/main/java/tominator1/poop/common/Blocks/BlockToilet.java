package tominator1.poop.common.Blocks;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Tiles.TileToilet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockToilet extends BlockContainer{
	
	private IIcon[] iconBuffer;
	
	public BlockToilet() {
		super(Material.iron);
		this.setCreativeTab(mod_poop.tabShit);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setStepSound(Block.soundTypeMetal);
	}
	
	public TileEntity createNewTileEntity(World world, int par1){
		return new TileToilet();
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (super.onBlockActivated(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
			return true;
		}

		ItemStack current = entityplayer.inventory.getCurrentItem();
		if (current != null) {
			TileEntity tile = world.getTileEntity(i, j, k);
			if (tile instanceof TileToilet) {
				TileToilet tank = (TileToilet) tile;
				if (FluidContainerRegistry.isContainer(current)) {
					FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
					if (liquid != null) {
					if (liquid.getFluid() == FluidRegistry.WATER) {
						
						int qty = tank.fill(ForgeDirection.UNKNOWN, liquid, true);
						
						if (qty != 0 && !entityplayer.capabilities.isCreativeMode) {
							if (current.stackSize > 1) {
								if (!entityplayer.inventory.addItemStackToInventory(FluidContainerRegistry.drainFluidContainer(current))) {
									entityplayer.dropPlayerItemWithRandomChoice(FluidContainerRegistry.drainFluidContainer(current), false);
								}

								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,current.splitStack(1));
							} else {
								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, FluidContainerRegistry.drainFluidContainer(current));
							}
						}
						metaUpdated(tank, world, i, j, k);
						return true;
					}
					}
				}else if(current.getItem() == mod_poop.toiletPaper && tank.tankWater.getFluidAmount() >= 334){
					ItemStack var12 = new ItemStack(mod_poop.poopOnPaper, 1, 0);

                    if (!entityplayer.inventory.addItemStackToInventory(var12))
                    {
                        world.spawnEntityInWorld(new EntityItem(world, (double)i + 0.5D, (double)j + 1.5D, (double)k + 0.5D, var12));
                    }
                    else if (entityplayer instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)entityplayer).sendContainerToPlayer(entityplayer.inventoryContainer);
                    }

                    current.splitStack(1);

                    if (current.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                    }
                    tank.drain(ForgeDirection.UNKNOWN, 333, true);
                    metaUpdated(tank, world, i, j, k);
                    return true;
				}
			}
		}
		return false;
	}
	
	public void metaUpdated(TileToilet tank, World world,int i, int j, int k){
		if(tank.tankWater.getFluidAmount() < 334){
			world.setBlockMetadataWithNotify(i, j, k, 0, 2);
		}else if(tank.tankWater.getFluidAmount() >= 334 && tank.tankWater.getFluidAmount() < 667){
			world.setBlockMetadataWithNotify(i, j, k, 1, 2);
		}else if(tank.tankWater.getFluidAmount() >= 667 && tank.tankWater.getFluidAmount() < 1000){
			world.setBlockMetadataWithNotify(i, j, k, 2, 2);
		}else if(tank.tankWater.getFluidAmount() >= 1000){
			world.setBlockMetadataWithNotify(i, j, k, 3, 2);
		}
	}
	
	public IIcon getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return var1 == 1 ? this.iconBuffer[1] : this.iconBuffer[0];
    }
	
	@SideOnly(Side.CLIENT)
	private IIcon topTexture;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister var1)
    {
    	this.blockIcon = var1.registerIcon("kupa:toiletSide");
    	this.topTexture = var1.registerIcon("kupa:toiletTop0");
        this.iconBuffer = new IIcon[5];
        this.iconBuffer[0] = var1.registerIcon("kupa:toiletSide");
        this.iconBuffer[1] = var1.registerIcon("kupa:toiletTop0");
        this.iconBuffer[2] = var1.registerIcon("kupa:toiletTop1");
        this.iconBuffer[3] = var1.registerIcon("kupa:toiletTop2");
        this.iconBuffer[4] = var1.registerIcon("kupa:toiletTop3");
    }
    @SideOnly(Side.CLIENT)
    @Override
	public IIcon getIcon(int side, int meta){
        return side == 1 ? (meta == 0 ? this.iconBuffer[1] : (meta == 1 ? this.iconBuffer[2] : (meta == 2 ? this.iconBuffer[3] : (meta == 3 ? this.iconBuffer[4] : this.iconBuffer[1])))) : this.iconBuffer[0];
        
	}
    
}