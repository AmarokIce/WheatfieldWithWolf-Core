package club.someoneice.www.common.tile;

import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.init.Tags;
import club.someoneice.www.init.recipe.RecipePot;
import club.someoneice.www.util.W3Util;
import club.someoneice.www.util.WWWApi;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TilePot extends TileEntity implements IInventory {
    private final ItemStack[] inventory = new ItemStack[9];

    public int time = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (canBurn() && this.inventory[0] != null) {
            checkTheRecipe();
        }
    }

    private void checkTheRecipe() {
        ItemStack[] itemInput = new ItemStack[6];

        for (int i = 0; i < 6; i ++) {
            if (this.inventory[i] != null) itemInput[i] = this.inventory[i];
        }

        RecipePot recipe = WWWApi.POT_RECIPES.stream().filter(it -> W3Util.init.stackArraySame(it.input, itemInput)).findFirst().orElse(null);;

        if (recipe == null) {
            time = 0;
            return;
        }

        if (this.time >= 200 && (recipe.bowl == null || Util.itemStackEquals(this.inventory[6], recipe.bowl))) {
            if (this.inventory[7] == null) {
                this.inventory[7] = recipe.output.copy();
            } else if (this.inventory[7].getItem() == recipe.output.getItem()){
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
        } else time++;
    }

    public boolean canBurn() {
        return Tags.HOT_SOURCE.has(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord));
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
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    public ItemStack decrStackSize(int slot, int size) {
        if (slot < 8 && this.inventory[slot] != null) {
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
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot < 7;
    }

    public List<ItemStack> getInventory() {
        return Lists.newArrayList(this.inventory);
    }
}
