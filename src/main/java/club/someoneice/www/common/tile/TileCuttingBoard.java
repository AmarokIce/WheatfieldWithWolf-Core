package club.someoneice.www.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class TileCuttingBoard extends TileEntity {
  public ItemStack itemInv;

  public TileCuttingBoard() {
    this.itemInv = null;
  }

  public TileCuttingBoard(World world, int meta) {
    this();

    this.worldObj = world;
    this.blockMetadata = meta;
  }

  @Override
  public boolean canUpdate() {
    return false;
  }

  public void setItemInv(ItemStack item) {
    this.itemInv = item;
  }

  @Override
  public void writeToNBT(NBTTagCompound nbt) {
    if (this.itemInv != null)
      nbt.setTag("item_inv", itemInv.writeToNBT(new NBTTagCompound()));

    super.writeToNBT(nbt);
    this.markDirty();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    this.itemInv = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item_inv"));
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

  @SuppressWarnings("unchecked")
  public void updateItem() {
    if (worldObj.isRemote) return;
    ((List<EntityPlayer>) this.worldObj.playerEntities).forEach(it ->
        ((EntityPlayerMP) it).playerNetServerHandler.sendPacket(this.getDescriptionPacket()));
  }
}
