package club.someoneice.www.client.gui.pot;

import club.someoneice.www.client.gui.slot.SlotOutput;
import club.someoneice.www.common.tile.TilePot;
import club.someoneice.www.init.BlockList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ContainerPot extends Container {
    public final TilePot tile;
    private final World worldObj;
    private final ChunkPosition pos;

    public ContainerPot(InventoryPlayer player, World world, int x, int y, int z, TilePot tile) {
        this.worldObj = world;
        this.pos = new ChunkPosition(x, y, z);
        this.tile = tile;

        this.addSlotToContainer(new SlotOutput(this.tile.getInventory(), 7, 122, 32));
        for (int h = 0; h < 2; h ++) for (int l = 0; l < 3; l ++)
            this.addSlotToContainer(new Slot(this.tile.getInventory(), l + h * 3, 38 + l * 18, 22 + h * 18));
        this.addSlotToContainer(new Slot(this.tile.getInventory(), 6, 96, 52));

        for (int h = 0; h < 3; ++h) for (int l = 0; l < 9; ++l)
            this.addSlotToContainer(new Slot(player, l + h * 9 + 9, 8 + l * 18, 84 + h * 18));

        for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 142));
    }

    public boolean canInteractWith(EntityPlayer player) {
        return worldObj.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ) == BlockList.pot && player.getDistanceSq((double)this.pos.chunkPosX + 0.5D, (double)this.pos.chunkPosY + 0.5D, (double)this.pos.chunkPosZ + 0.5D) <= 64.0D;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack itemstack;
        Slot slot = (Slot)this.inventorySlots.get(slotNumber);

        if (slot == null || !slot.getHasStack()) return null;
        ItemStack itemstack1 = slot.getStack();
        itemstack = itemstack1.copy();

        if (slotNumber < this.tile.getInventory().getSizeInventory()) {
            if (!this.mergeItemStack(itemstack1, this.tile.getInventory().getSizeInventory(), this.inventorySlots.size(), true)) return null;
        }
        else if (!this.mergeItemStack(itemstack1, 1, this.tile.getInventory().getSizeInventory(), false)) {
            return null;
        }
        if (itemstack1.stackSize == 0) slot.putStack(null);
        else slot.onSlotChanged();
        return itemstack;
    }
}
