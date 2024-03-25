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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class TilePot extends TileEntity implements IInventory {
    private final ItemStack[] inventory = new ItemStack[9];
    public int time;

    public TilePot(World world, int meta) {
        this();
        this.worldObj = world;
        this.blockMetadata = meta;
    }

    public TilePot() {
        this.time = 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (canBurn() && this.inventory[0] != null) {
            checkTheRecipe();
        }
    }

    private void checkTheRecipe() {
        ItemStack[] itemInput = new ItemStack[6];

        for (int i = 0; i < 6; i ++) if (this.inventory[i] != null) itemInput[i] = this.inventory[i];

        Optional<RecipePot> recipeCup = WWWApi.POT_RECIPES.stream().filter(it -> W3Util.init.compareRecipe(it.input, itemInput)).findFirst();

        if (!recipeCup.isPresent()) {
            time = 0;
            return;
        }

        RecipePot recipe = recipeCup.get();
        if (++this.time < 200 || (recipe.bowl != null && !Util.itemStackEquals(this.inventory[6], recipe.bowl))) return;
        if (!this.worldObj.isRemote) crafting(recipe);
        this.time = 0;
    }

    private void crafting(RecipePot recipe) {
        if (this.inventory[7] == null) this.inventory[7] = recipe.output.copy();
        else if (this.inventory[7].getItem() == recipe.output.getItem()) this.inventory[7].stackSize++;
        else return;

        for (int i = 0; i < 7; i ++) {
            ItemStack item = this.inventory[i];
            if (item == null) continue;
            if (item.getItem().hasContainerItem(item))
                this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord + 0.8D, this.zCoord, item.getItem().getContainerItem(item)));

            this.decrStackSize(i, 1);
        }
    }

    public boolean canBurn() {
        return Tags.HOT_SOURCE.has(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("time", this.time);

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

        this.time = nbt.getInteger("time");

        for (int i = 0; i < inventory.length; i++) {
            if (nbt.hasKey("craft" + i)) {
                inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("craft" + i));
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
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (slot >= this.getSizeInventory()) return null;
        return this.inventory[slot];
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
