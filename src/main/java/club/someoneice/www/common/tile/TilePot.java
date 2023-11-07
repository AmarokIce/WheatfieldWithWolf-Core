package club.someoneice.www.common.tile;

import club.someoneice.www.init.Tags;
import club.someoneice.www.init.recipe.RecipePot;
import club.someoneice.www.util.Util;
import club.someoneice.www.util.WWWApi;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TilePot extends TileEntity implements IInventory, ISidedInventory {
    private final ItemStack[] inventory = new ItemStack[8];

    public int time;

    public TilePot() {
        this.time = 0;
    }

    RecipePot recipe = null;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (canBurn() && this.inventory[0] != null) {
            checkTheRecipe();
            canCook();
        }
    }

    private void checkTheRecipe() {
        if (this.recipe != null) {
            if (this.time >= 200 && (this.recipe.bowl == null || this.inventory[6].getItem() == this.recipe.bowl)) {
                if (this.inventory[7] == null) {
                    this.inventory[7] = this.recipe.output.copy();
                } else if (this.inventory[7].getItem() == this.recipe.output.getItem()){
                    this.inventory[7].stackSize++;
                }else return;

                for (int i = 0; i < 7; i ++) {
                    ItemStack item = this.inventory[i];
                    if (item == null) continue;
                    if (!this.worldObj.isRemote && item.getItem().hasContainerItem()) {
                        this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord + 0.8D, this.zCoord, new ItemStack(item.getItem().getContainerItem())));
                    }
                    if (item.stackSize > 1) item.stackSize--;
                    else this.inventory[i] = null;
                }

                this.time = 0;
                this.recipe = null;
            }
        } else this.time = 0;
    }

    public boolean canBurn() {
        return Tags.HOT_SOURCE.has(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord));
    }

    private void canCook() {
        Item[] itemInput = new Item[6];

        for (int i = 0; i < 6; i ++) {
            if (this.inventory[i] != null) itemInput[i] = this.inventory[i].getItem();
        }

        for (RecipePot potRecipe : WWWApi.POT_MAP) {
            if (Util.init.arraySame(itemInput, potRecipe.input)) {
                if (this.recipe == null) {
                    this.recipe = potRecipe;
                } else if (this.recipe == potRecipe) {
                    if (this.time < 200) this.time ++;
                } else {
                    this.recipe = potRecipe;
                    this.time = 0;
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("time", this.time);

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                nbt.setTag("Craft" + i, inventory[i].writeToNBT(new NBTTagCompound()));
            }
        }

        super.writeToNBT(nbt);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.time = nbt.getInteger("time");

        for (int i = 0; i < inventory.length; i++) {
            if (nbt.hasKey("Craft" + i)) {
                inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Craft" + i));
            }
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[1];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

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
        return "www.pot.name";
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
        return slot < 7;
    }

    public List<ItemStack> getInventory() {
        return Lists.newArrayList(this.inventory);
    }
}
