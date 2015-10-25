package tominator1.poop.common.Handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.omg.CORBA.PRIVATE_MEMBER;

import tominator1.poop.common.Tiles.TileAutoToilet;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
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
    			//System.out.println(entry + "/" + recipes.get(entry).itemStack.stackSize);
    			if(outerOutputs == null){
    				outerOutputs = entry;
    			}else if(outerOutputs.amount < entry.amount){
    				outerOutputs = entry;
    			}
    		}
    	}
    	}
    	if(outerOutputs != null){
    		System.out.println(recipes.get(outerOutputs).itemStack.stackSize);
    	}
    	return recipes.get(outerOutputs);
    }
}