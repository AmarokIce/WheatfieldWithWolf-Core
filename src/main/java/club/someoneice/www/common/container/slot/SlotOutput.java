package club.someoneice.www.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot {
  public SlotOutput(IInventory inv, int slot, int x, int y) {
    super(inv, slot, x, y);
  }

  public boolean isItemValid(ItemStack p_75214_1_) {
    return false;
  }
}
