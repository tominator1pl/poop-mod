package tominator1.poop.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;

public class AutoToiletHandler {
	public static AutoToiletHandler INSTANCE = new AutoToiletHandler();
    public Map<EntityAnimal, TileAutoToilet> mobs = new HashMap<EntityAnimal, TileAutoToilet>();
    
    private AutoToiletHandler() {
    }
    
    public boolean isThisMine(EntityAnimal animal,TileAutoToilet autoToilet){
    	if(mobs.containsKey(animal)){
    		if(mobs.get(animal) == autoToilet){
    			return true;
    		}else{
    			if(mobs.get(animal).isInvalid()){
    				mobs.remove(animal);
    			}
    			return false;
    		}
    	}else{
    		mobs.put(animal, autoToilet);
    		return true;
    	}
    }
}
