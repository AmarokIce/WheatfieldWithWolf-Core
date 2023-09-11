package club.someoneice.www.common.tile;

import club.someoneice.www.WWWApi;
import club.someoneice.www.init.ItemList;
import club.someoneice.www.init.recipe.RecipeGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileGrinder extends TileEntity implements IInventory, ISidedInventory {
    private static final int[] slotsTop = new int[] { 0 };
    private static final int[] slotsBottom = new int[] { 2, 1 };
    private static final int[] slotsSides = new int[] { 1 };

    private final ItemStack[] inventory = new ItemStack[4];

    public int time, burnTime;

    public TileGrinder() {
        this.time = 0;
        this.burnTime = 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!canBurn() || this.inventory[0] == null) return;
        if (WWWApi.GRINDER.containsKey(this.inventory[0].getItem())) {
            RecipeGrinder recipe = WWWApi.GRINDER.get(this.inventory[0].getItem());
            if (time >= recipe.cooking_time) {

                if (recipe.bottle == null || this.inventory[3] == recipe.bottle) {
                    this.inventory[3].stackSize--;
                } else return;

                if (this.inventory[2] == null) {
                    this.inventory[2] = recipe.output;
                } else if (this.inventory[2] == recipe.output) {
                    if (inventory[2].stackSize < inventory[2].getMaxStackSize()) {
                        this.inventory[2].stackSize += recipe.output.stackSize;
                    }
                } else return;

                if (this.inventory[0].stackSize - 1 == 0) this.inventory[0] = null;
                else this.inventory[0].stackSize--;
                time = 0;

            } else ++time;

            if (burnTime > 0) burnTime--;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("time", this.time);
        nbt.setInteger("burn", this.burnTime);

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null)
                nbt.setTag("Craft" + i, inventory[i].writeToNBT(new NBTTagCompound()));
        }

        super.writeToNBT(nbt);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.time       = nbt.getInteger("time");
        this.burnTime   = nbt.getInteger("burn");

        for (int i = 0; i < inventory.length; i++) {
            if (nbt.hasKey("Craft" + i)) {
                inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Craft" + i));
            }
        }
    }

    private boolean canBurn() {
        if (this.inventory[1] != null && this.inventory[1].getItem() == ItemList.grinder_knife && burnTime <= 0) {
            if (this.inventory[1].stackSize == 1) this.inventory[1] = null;
            else this.inventory[1].stackSize--;
            this.burnTime = 8000;
            markDirty();
        }

        return burnTime > 0;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slotsBottom : side == 1 ? slotsTop : slotsSides;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return slot == 0 && side == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot == 2 && side == 1;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int size) {
        if (slot < 4 && this.inventory[slot] != null) {
            ItemStack item = this.inventory[slot].copy();
            this.inventory[slot] = null;
            return item;
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        this.inventory[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "www.grinder.name";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0) return true;
        else if (slot == 1) return item.getItem() == ItemList.grinder_knife;
        else if (slot == 2) return false;
        else if (slot == 3) return true;

        return true;
    }
}
