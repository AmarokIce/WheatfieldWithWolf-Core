package club.someoneice.www.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileCuttingBoard extends TileEntity {
    public ItemStack itemInv;

    public TileCuttingBoard() {
        this.itemInv = null;
    }

    public ItemStack getItemInv() {
        return this.itemInv.copy();
    }

    public void setItemInv(ItemStack item) {
        this.itemInv = item;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        if (this.itemInv != null)
            nbt.setTag("itemInv", itemInv.writeToNBT(new NBTTagCompound()));
        super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("itemInv"))
            this.itemInv = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("itemInv"));
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 5, nbttagcompound);
    }
}
