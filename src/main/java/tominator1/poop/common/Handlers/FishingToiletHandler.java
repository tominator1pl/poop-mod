package tominator1.poop.common.Handlers;



import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FishingToiletHandler {
	public static FishingToiletHandler INSTANCE = new FishingToiletHandler();
	
	private class RareItems{
		public ItemStack itemStack;
		public double rarity;
		public RareItems(ItemStack itemStack1,double rarity1){
			itemStack = itemStack1;
			rarity = rarity1;
		}
	}
    
	public Map<Integer,RareItems> itemStacks = new HashMap<Integer,RareItems>();
    
    private FishingToiletHandler() {
    }
    
    /**
     * Adds Recipe for Fishing.
     * @param itemStack for Output
     * @param rarity from 1000 to 0.1
     */
    public void addRecipe(ItemStack itemStack, double rarity){
    	itemStacks.put(1,new RareItems(itemStack, rarity));
    	Map<Integer, RareItems> tempItemStacksMap = new HashMap<Integer, RareItems>();
    	int actualPercentage = 0;
    	for (Integer entry : itemStacks.keySet()){
    		double sum = 0;
    		int tempPercentage = entry;
    		RareItems itemStack2 = itemStacks.get(tempPercentage);
    		double rarity1 = itemStack2.rarity;
    		for (Integer entry2 : itemStacks.keySet()){
    			sum += itemStacks.get(entry2).rarity;
    		}
    		tempPercentage = ((int) Math.ceil((rarity1/sum)*1000))+ actualPercentage;
    		tempItemStacksMap.put(tempPercentage, itemStack2);
    		actualPercentage = tempPercentage;
    	}
    	itemStacks = tempItemStacksMap;
    		
    }
    
    /**
     * Checks for recipe.
     * @return ItemStack of item to return.
     */
    public ItemStack getRecipe(){
    	int rand = (int) (Math.random()*1000);
    	while(itemStacks.get(rand) == null && rand <=1010){
    		rand++;
    	}
    	if(itemStacks.get(rand).itemStack != null){
    		return itemStacks.get(rand).itemStack.copy();
    	}else{
    		return null;
    	}
    }
}