package club.someoneice.www.common.item;

import club.someoneice.www.common.bean.item.ItemFactory;

public class ItemKnife extends ItemFactory {

    public ItemKnife(String name) {
        super(name);
        this.maxStackSize = 1;
        this.canRepair = false;
    }
}
