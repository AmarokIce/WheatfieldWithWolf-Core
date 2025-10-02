package club.someoneice.www.client.gui.grinder;

import club.someoneice.www.client.gui.slot.SlotFlue;
import club.someoneice.www.client.gui.slot.SlotOutput;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.core.init.W3Blocks;
import club.someoneice.www.core.init.W3Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ContainerGrinder extends Container {
  public IInventory inventory;
  private final World worldObj;
  private final ChunkPosition pos;

  public ContainerGrinder(InventoryPlayer player, World world, int x, int y, int z, TileGrinder tile) {
    this.worldObj = world;
    this.pos = new ChunkPosition(x, y, z);
    inventory = tile;

    this.addSlotToContainer(new SlotOutput(tile, 2, 116, 35)); // 0
    this.addSlotToContainer(new SlotFlue(tile, 1, 56, 53, new ItemStack(W3Items.grinder_knife))); // 1
    this.addSlotToContainer(new Slot(tile, 0, 56, 17)); // 2
    this.addSlotToContainer(new Slot(tile, 3, 141, 35)); // 3

    for (int h = 0; h < 3; ++h)
      for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l + h * 9 + 9, 8 + l * 18, 84 + h * 18));
    for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 142));
  }

  public boolean canInteractWith(EntityPlayer player) {
    return worldObj.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ) == W3Blocks.grinder && player.getDistanceSq((double) this.pos.chunkPosX + 0.5D, (double) this.pos.chunkPosY + 0.5D, (double) this.pos.chunkPosZ + 0.5D) <= 64.0D;
  }

  public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
    ItemStack itemstack = null;
    Slot slot = (Slot) this.inventorySlots.get(slotNumber);

    if (slot == null || !slot.getHasStack()) return itemstack;

    ItemStack itemstack1 = slot.getStack();
    itemstack = itemstack1.copy();

    if (slotNumber < this.inventory.getSizeInventory()) {
      if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
        return null;
    } else if (itemstack1.getItem() == W3Items.grinder_knife) {
      if (!this.mergeItemStack(itemstack1, 1, 2, true)) return null;
    } else if (!this.mergeItemStack(itemstack1, 2, this.inventory.getSizeInventory(), false)) return null;

    if (itemstack1.stackSize == 0) slot.putStack(null);
    else slot.onSlotChanged();

    return itemstack;
  }

}
