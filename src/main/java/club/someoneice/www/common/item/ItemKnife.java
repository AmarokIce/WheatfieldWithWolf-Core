package club.someoneice.www.common.item;

import club.someoneice.www.common.item.factory.ItemFactory;
import club.someoneice.www.init.ItemList;
import net.minecraft.item.Item;

public class ItemKnife extends ItemFactory {

    public ItemKnife(String name) {
        super(name);
        this.maxStackSize = 1;
        this.canRepair = false;
    }

    @Override
    public Item getContainerItem() {
        return ItemList.knife;
    }
}
