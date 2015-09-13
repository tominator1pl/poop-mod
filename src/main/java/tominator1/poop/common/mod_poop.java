package tominator1.poop.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
		GameRegistry.registerBlock(poop, "poop2");
		GameRegistry.registerItem(toiletPaper, "toilet_paper");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	    //proxy.postInit();
	}
}
