package club.someoneice.www.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SlotLunch extends Slot {
  public SlotLunch(IInventory inv, int slot, int x, int y) {
    super(inv, slot, x, y);
  }

  public boolean isItemValid(ItemStack item) {
    return item == null || item.getItem() instanceof ItemFood;
  }
}
