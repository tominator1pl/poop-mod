package tominator1.poop.nei;

import static net.minecraft.init.Items.potionitem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import tominator1.poop.common.mod_poop;
import tominator1.poop.common.Guis.GuiIngotCaster;
import tominator1.poop.common.Handlers.CasterCraftingHandler;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import codechicken.nei.ItemStackSet;
import codechicken.nei.Label;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.BrewingRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.BrewingRecipeHandler.BrewingRecipe;
import codechicken.nei.recipe.BrewingRecipeHandler.CachedBrewingRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;

public class CasterRecipehandler extends TemplateRecipeHandler{

	public static class CasterRecipe{
		public PositionedStack fluidIn;
		public PositionedStack result;
		public ItemStack fluidContainer;
		public Label amount;
		
		public CasterRecipe(FluidStack fluid,ItemStack fluidCont, ItemStack resulted){
			fluidIn = new PositionedStack(new ItemStack(fluid.getFluid().getBlock()), 35, 30);
			fluidContainer = fluidCont;
			amount = new Label(fluid.amount + "mB", false);
			amount.x = 30;
			amount.y = 49;
			result = new PositionedStack(resulted, 110, 30);
		}
	}
	
	public class CachedCasterRecipe extends CachedRecipe
    {
        public CasterRecipe recipe;

        public CachedCasterRecipe(CasterRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
            return recipe.result;
        }

        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(recipe.fluidIn);
            return recipestacks;
        }
    }
	
    public static final HashSet<CasterRecipe> aresults = new HashSet<CasterRecipe>();
	
    @Override
    public void drawExtras(int recipe) {
    	
    	((CachedCasterRecipe) arecipes.get(recipe)).recipe.amount.draw(0, 0);
    }
    
    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiIngotCaster.class;
    }
    
	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.ingotCaster.name");
	}
	
	@Override
	public String getOverlayIdentifier(){
		return mod_poop.MOD_ID + ".ingotCaster";
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation("kupa:textures\\gui\\CasterRecipeUI.png").toString();
	}

	@Override
    public void loadCraftingRecipes(String outputId, Object... results){
		if (getClass() == CasterRecipehandler.class)
            for (CasterRecipe recipe : aresults)
                arecipes.add(new CachedCasterRecipe(recipe));
        else
            super.loadCraftingRecipes(outputId, results);
	}
	@Override
    public void loadCraftingRecipes(ItemStack result)
    {
		for (CasterRecipe recipe : aresults)
			arecipes.add(new CachedCasterRecipe(recipe));
    }
	
	public void loadUsageRecipes(ItemStack ingredient) {

        for (CasterRecipe recipe : aresults)
            if (NEIServerUtils.areStacksSameType(recipe.fluidContainer, ingredient) || NEIServerUtils.areStacksSameType(recipe.fluidIn.item, ingredient))
                arecipes.add(new CachedCasterRecipe(recipe));
    }
	
	public static void addRecipe(FluidStack fluidIn, ItemStack fluidCont, ItemStack result){
			aresults.add(new CasterRecipe(fluidIn,fluidCont,result));
	}
}
