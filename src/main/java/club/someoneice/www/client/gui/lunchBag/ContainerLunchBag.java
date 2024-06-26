package club.someoneice.www.client.gui.lunchBag;

import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.www.util.W3Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;


public class ContainerLunchBag extends Container {
    private final SimpleInventory inventory;
    private final ItemStack itemIn;

    public ContainerLunchBag(InventoryPlayer player) {
        this.itemIn = player.getCurrentItem();

        this.inventory = W3Util.init.getInvFromItemStack(itemIn, 3);

        for (int l = 0; l < 3; ++l) this.addSlotToContainer(new SlotLunch(inventory, l, 53 + l * 27, 31));

        for (int h = 0; h < 3; ++h) for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l + h * 9 + 9, 8 + l * 18, 84 + h * 18));
        for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 142));
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        W3Util.init.setInvFromItemStack(itemIn, this.inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.itemIn != null && player != null && player.getHeldItem() == this.itemIn;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        Slot slot = (Slot) this.inventorySlots.get(slotNumber);
        if (slot == null || !slot.getHasStack()) return null;
        ItemStack item = slot.getStack();
        if (item == this.itemIn) return item;
        if (!(item.getItem() instanceof ItemFood)) return mergeItemStackInPlayer(slotNumber, item, slot);
        else return mergeFood(slotNumber, item, slot);
    }

    public ItemStack mergeItemStackInPlayer(int slotNumber, ItemStack item, Slot slot) {
        if (slotNumber < 27 + this.inventory.getSizeInventory()) {
            if (!this.mergeItemStack(item, 27 + this.inventory.getSizeInventory(), this.inventorySlots.size(), false))
                return null;
        }
        else if (!this.mergeItemStack(item, this.inventory.getSizeInventory(), 27 + this.inventory.getSizeInventory(), false)) return null;

        if (item.stackSize == 0) slot.putStack(null);
        else slot.onSlotChanged();

        return item;
    }

    private ItemStack mergeFood(int slotNumber, ItemStack item, Slot slot) {
        if (slotNumber < this.inventory.getSizeInventory()) {
            if (!this.mergeItemStack(item, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
                return null;
        }
        else if (!this.mergeItemStack(item, 0, this.inventory.getSizeInventory(), false)) return null;

        if (item.stackSize == 0) slot.putStack(null);
        else slot.onSlotChanged();

        return item;
    }
}
