package club.someoneice.www.common.tile;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.api.recipe.RecipeGrinder;
import club.someoneice.www.core.init.W3Items;
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
  private static final int[] slotsTop = new int[]{0};
  private static final int[] slotsBottom = new int[]{2, 1};
  private static final int[] slotsSides = new int[]{1};

  private final SimpleInventory inventory = new SimpleInventory(4) {
    @Override
    public String getInventoryName() {
      return "www.grinder.name";
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
      if (slot == 0) return false;
      else if (slot == 1) return item.getItem() == W3Items.grinder_knife;
      else if (slot == 2) return true;
      else if (slot == 3) return true;

      return true;
    }
  };

  public int time, burnTime;

  public TileGrinder() {
    this.time = 0;
    this.burnTime = 0;
  }

  @Override
  public void updateEntity() {
    super.updateEntity();
    if (!canBurn() || this.inventory.getStackInSlot(0) == null) time = 0;
    WWWApi.GRINDER_RECIPES.stream().filter(it -> W3Util.init.compareIngredientContains(it.input, this.inventory.getStackInSlot(0))).findFirst().ifPresent(it -> {
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
    if (Util.itemStackEquals(this.inventory.getStackInSlot(3), recipe.bottle)) {
      decrStackSize(3, 1);
      return true;
    }
    return false;
  }

  private void setOutput(RecipeGrinder recipe) {
    ItemStack item = this.inventory.getStackInSlot(2);
    if (item == null) this.inventory.setInventorySlotContents(2, recipe.output.copy());
    else if (item.getItem() == recipe.output.getItem() && inventory.getStackInSlot(2).stackSize < inventory.getStackInSlot(2).getMaxStackSize()) {
      item.stackSize += recipe.output.stackSize;
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound nbt) {
    nbt.setInteger("time", this.time);
    nbt.setInteger("burn", this.burnTime);

    nbt.setTag("inventory", this.inventory.write());

    super.writeToNBT(nbt);
    this.markDirty();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);

    this.time = nbt.getInteger("time");
    this.burnTime = nbt.getInteger("burn");

    if (nbt.hasKey("inventory"))
      this.inventory.load(nbt.getCompoundTag("inventory"));
  }

  private boolean canBurn() {
    if (this.inventory.getStackInSlot(1) != null
        && this.inventory.getStackInSlot(1).getItem() == W3Items.grinder_knife
        && burnTime <= 0) {
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
    return this.inventory.getSizeInventory();
  }

  @Override
  public ItemStack getStackInSlot(int slot) {
    return this.inventory.getStackInSlot(slot);
  }

  @Override
  public ItemStack decrStackSize(int slot, int size) {
    return this.inventory.decrStackSize(slot, size);
  }

  @Override
  public ItemStack getStackInSlotOnClosing(int slot) {
    return this.inventory.getStackInSlotOnClosing(slot);
  }

  @Override
  public void setInventorySlotContents(int slot, ItemStack item) {
    this.inventory.setInventorySlotContents(slot, item);
  }

  @Override
  public String getInventoryName() {
    return this.inventory.getInventoryName();
  }

  @Override
  public boolean hasCustomInventoryName() {
    return this.inventory.hasCustomInventoryName();
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
    this.inventory.openInventory();
  }

  @Override
  public void closeInventory() {
    this.inventory.closeInventory();
  }

  @Override
  public boolean isItemValidForSlot(int slot, ItemStack item) {
    return this.inventory.isItemValidForSlot(slot, item);
  }

  public List<ItemStack> getItemStackInInventory() {
    return ObjectUtil.objectLet(Lists.newArrayList(), it -> {
      for (int i = 0; i < inventory.getSizeInventory(); i++)
        it.add(inventory.getStackInSlot(i));
    });
  }

  public SimpleInventory getInventory() {
    return this.inventory;
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
