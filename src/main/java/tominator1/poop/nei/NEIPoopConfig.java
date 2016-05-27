package tominator1.poop.nei;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Guis.GuiIngotCaster;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIPoopConfig implements IConfigureNEI{

	@Override
	public void loadConfig() {
		// TODO Auto-generated method stub
		CasterRecipehandler handler = new CasterRecipehandler();
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
		
		API.registerGuiOverlay(GuiIngotCaster.class, mod_poop.MOD_ID + ".ingotCaster");
	}

	@Override
	public String getName() {
		return "Poop plugin";
	}

	@Override
	public String getVersion() {
		return mod_poop.VERSION;
	}

}
