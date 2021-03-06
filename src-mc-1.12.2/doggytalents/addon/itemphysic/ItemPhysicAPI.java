package doggytalents.addon.itemphysic;

import java.lang.reflect.Method;

import doggytalents.DoggyTalents;
import doggytalents.helper.ReflectionUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author ProPercivalalb
 */
public class ItemPhysicAPI {
    
    public Class<?> serverPhysicClass;
    public Class<?> sortingListClass;
    public Method addSortingBlock;
    public Method addSortingItem;
    
    public ItemPhysicAPI() {
        this.serverPhysicClass = ReflectionUtil.getClass(ItemPhysicLib.SERVER_PHYSIC_CLASS);

        // Tries to find newest class from CreativeCore if not looks in old location
        this.sortingListClass = ReflectionUtil.getClass(ItemPhysicLib.SORTING_LIST_CLASS);
        if(this.sortingListClass == null)
            this.sortingListClass = ReflectionUtil.getClass(ItemPhysicLib.SORTING_LIST_CLASS_OLD);
        
        this.addSortingBlock = ReflectionUtil.getMethod(this.sortingListClass, ItemPhysicLib.ADD_SORTING_BLOCK, new Class[] {Block.class});
        this.addSortingItem = ReflectionUtil.getMethod(this.sortingListClass, ItemPhysicLib.ADD_SORTING_ITEM, new Class[] {Item.class});
    }
    
    public void addSortingBlocks(String sortingListName, Block... blocks) throws Exception {
        Object sortingList = ReflectionUtil.getField(this.serverPhysicClass, sortingListName).get(null);
        for(Block block : blocks) {
            this.addSortingBlock.invoke(sortingList, block);
            DoggyTalents.LOGGER.debug("Successefully registered {} in {} list", block.getRegistryName(), sortingListName);
        }
    }
    
    public void addSortingItems(String sortingListName, Item... items) throws Exception {
        Object sortingList = ReflectionUtil.getField(this.serverPhysicClass, sortingListName).get(null);
        for(Item item : items) {
            this.addSortingItem.invoke(sortingList, item);
            DoggyTalents.LOGGER.debug("Successefully registered {} in {} list", item.getRegistryName(), sortingListName);
        }
    }
}
