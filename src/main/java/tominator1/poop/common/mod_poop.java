package tominator1.poop.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "poop", name = "Poop Mod", version = "2.0")
public class mod_poop {
	@Mod.Instance("poop")
	public static mod_poop instance;
	
	@SidedProxy(clientSide="tominator1.poop.client.poopClientProxy", serverSide="tominator1.poop.common.poopCommonProxy")
	public static poopCommonProxy proxy;
	
	public static Block poop;
	public static Item toiletPaper;
	public static Item poopOnPaper;
	public static Fluid liquidPoop;
	public static Block liquidPoopBlock;
	public static Item poopBucket;
	public static Block toiletBlock;
	
	public static CreativeTabs tabShit= new CreativeTabs("shit") {

	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem(){
	    	return Item.getItemFromBlock(mod_poop.poop);
	    }
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	    //proxy.preInit();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
	    //proxy.load();
		poop = (new BlockPoop().setBlockName("poop"));
		toiletPaper = (new ItemToiletPaper().setUnlocalizedName("toilet_paper"));
		poopOnPaper = (new ItemPoopOnPaper().setUnlocalizedName("poop_on_paper"));
		liquidPoop = (new Fluid("liquidShit").setViscosity(3000));
		FluidRegistry.registerFluid(liquidPoop);
		liquidPoopBlock = (new BlockLiquidShit(liquidPoop, Material.water));
		GameRegistry.registerBlock(liquidPoopBlock, "liquidPoop");
		liquidPoop.setUnlocalizedName(liquidPoopBlock.getUnlocalizedName());
		poopBucket = new ItemPoopBucket(liquidPoopBlock);
		GameRegistry.registerItem(poopBucket, "poopBucket");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(liquidPoop.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(poopBucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(liquidPoopBlock, poopBucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		toiletBlock = (new BlockToilet().setBlockName("toilet"));
		GameRegistry.registerBlock(poop, "poop");
		GameRegistry.registerBlock(toiletBlock, "toilet");
		GameRegistry.registerTileEntity(TileToilet.class, "tileToilet");
		GameRegistry.registerItem(toiletPaper, "toilet_paper");
		GameRegistry.registerItem(poopOnPaper, "poop_on_paper");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	    //proxy.postInit();
	}
	
	@EventHandler
	@SideOnly(Side.CLIENT)
	public void postStitch(TextureStitchEvent.Post event)
	{
	    liquidPoop.setIcons(liquidPoopBlock.getBlockTextureFromSide(0), liquidPoopBlock.getBlockTextureFromSide(1));
	}
}
