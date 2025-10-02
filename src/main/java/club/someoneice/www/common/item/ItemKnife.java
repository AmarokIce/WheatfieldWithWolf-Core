package club.someoneice.www.common.item;

import club.someoneice.www.bean.item.ItemFactory;
import club.someoneice.www.core.init.W3Items;
import net.minecraft.item.Item;

public class ItemKnife extends ItemFactory {

  public ItemKnife(String name) {
    super(name);
    this.maxStackSize = 1;
    this.canRepair = false;
  }

  @Override
  public Item getContainerItem() {
    return W3Items.knife;
  }
}
