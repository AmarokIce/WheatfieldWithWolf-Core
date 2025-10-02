package club.someoneice.www.client.gui.slot;

import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class SlotFlue extends Slot {
  ItemStack[] items;

  public SlotFlue(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ItemStack... itemFlue) {
    super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    this.items = itemFlue;
  }

  @Override
  public boolean isItemValid(ItemStack item) {
    return Arrays.stream(this.items).anyMatch(it -> Util.itemStackEquals(it, item));
  }
}
