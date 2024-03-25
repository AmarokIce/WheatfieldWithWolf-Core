package club.someoneice.www.common.tile;

import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.init.ItemList;
import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.util.W3Util;
import club.someoneice.www.util.WWWApi;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TileGrinder extends TileEntity implements ISidedInventory {
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
        if (!canBurn() || this.inventory[0] == null) time = 0;
        WWWApi.GRINDER_RECIPES.stream().filter(it -> W3Util.init.compareIngredientContains(it.input, this.inventory[0])).findFirst().ifPresent(it -> {
            if (time < it.cooking_time) {
                burnTime--;
                ++time;
                return;
            }
            if (!this.worldObj.isRemote) crafting(it);
            time = 0;
        });
    }

    private void crafting(RecipeGrinder recipe) {
        if (!checkTheBottle(recipe)) return;
        setOutput(recipe);
        decrStackSize(0, 1);
    }

    private boolean checkTheBottle(RecipeGrinder recipe) {
        if (recipe.bottle == null) return true;
        if (Util.itemStackEquals(this.inventory[3], recipe.bottle)) {
            decrStackSize(3, 1);
            return true;
        }
        return false;
    }

    private void setOutput(RecipeGrinder recipe) {
        if (this.inventory[2] == null) this.inventory[2] = recipe.output.copy();
        else if (this.inventory[2].getItem() == recipe.output.getItem() && inventory[2].stackSize < inventory[2].getMaxStackSize()) {
            this.inventory[2].stackSize += recipe.output.stackSize;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("time", this.time);
        nbt.setInteger("burn", this.burnTime);

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null)
                nbt.setTag("craft" + i, inventory[i].writeToNBT(new NBTTagCompound()));
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
            if (nbt.hasKey("craft" + i)) {
                inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("craft" + i));
            }
        }
    }

    private boolean canBurn() {
        if (this.inventory[1] != null && this.inventory[1].getItem() == ItemList.grinder_knife && burnTime <= 0) {
            decrStackSize(1, 1);
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
        if (slot >= this.getSizeInventory()) return null;
        ItemStack item = this.inventory[slot];
        ItemStack out = item.copy();
        if (item.stackSize < size) {
            this.inventory[slot] = null;
            return out;
        }

        item.stackSize -= size;
        out.stackSize = size;
        return out;
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
        if (slot == 0) return false;
        else if (slot == 1) return item.getItem() == ItemList.grinder_knife;
        else if (slot == 2) return true;
        else if (slot == 3) return true;

        return true;
    }

    public List<ItemStack> getInventory() {
        return Lists.newArrayList(this.inventory);
    }

    @Override
    public Packet getDescriptionPacket() {
        super.getDescriptionPacket();
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.func_148857_g());
    }
}
