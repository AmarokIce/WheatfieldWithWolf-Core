package club.someoneice.www.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFlue extends Slot {
    Item item;
    public SlotFlue(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, Item itemFlue) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.item = itemFlue;
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        return item.getItem() == this.item;
    }
}
