package tominator1.poop.common.Handlers;

import java.util.HashMap;
import java.util.Map;

import tominator1.poop.nei.CasterRecipehandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

public class CasterCraftingHandler {
	public static CasterCraftingHandler INSTANCE = new CasterCraftingHandler();
    
	public class RecipeOutputs{
		public FluidStack fluidStack;
		public ItemStack itemStack;
		public RecipeOutputs(FluidStack fluidStack2, ItemStack itemStack2){
			fluidStack = fluidStack2;
			itemStack = itemStack2;
		}
	}
    
	public Map<FluidStack, RecipeOutputs> recipes = new HashMap<FluidStack, RecipeOutputs>();
    
    private CasterCraftingHandler() {
    }
    
    /**
     * Adds Recipe for Caster.
     * @param fluidStack with Fluid and amount for Input
     * @param itemStack for Output
     */
    public void addRecipe(FluidStack fluidStack, ItemStack itemStack){
    	
    	FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();
    	ItemStack fluCont = (ItemStack)null;
    	for(int i = 0; i < data.length; i++){
    		if(data[i].fluid.isFluidEqual(fluidStack)) fluCont = data[i].filledContainer;
    	}
    	if(fluCont == (ItemStack)null) fluCont = new ItemStack(fluidStack.getFluid().getBlock());
    	CasterRecipehandler.addRecipe(fluidStack, fluCont, itemStack);
    	recipes.put(fluidStack, new RecipeOutputs(fluidStack, itemStack));
    }
    
    /**
     * Checks for recipe.
     * @param fluidStack If Fluid type exist and amount required is lower.
     * @return RecipeOutputs has fluidStack with exact amount that is required for recipe and itemStack for output.
     */
    public RecipeOutputs getRecipe(FluidStack fluidStack){
    	FluidStack outerOutputs = null;
    	if(fluidStack != null && fluidStack.amount > 0){
    	for (FluidStack entry : recipes.keySet())
    	{
    		if(entry.isFluidEqual(fluidStack) && entry.amount <= fluidStack.amount){
    			if(outerOutputs == null){
    				outerOutputs = entry;
    			}else if(outerOutputs.amount < entry.amount){
    				outerOutputs = entry;
    			}
    		}
    	}
    	}
    	return recipes.get(outerOutputs);
    }
}
