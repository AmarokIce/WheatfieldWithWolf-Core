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

    public TileCuttingBoard(World world, int meta) {
        this.worldObj = world;
        this.blockMetadata = meta;

        this.itemInv = null;
    }

    public ItemStack getItemInv() {
        return this.itemInv.copy();
    }

    @SuppressWarnings("unchecked")
    public void setItemInv(ItemStack item) {
        this.itemInv = item;
        this.updateItem();
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
        else this.itemInv = null;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 5, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.func_148857_g());
    }

    @SuppressWarnings("unchecked")
    public void updateItem() {
        if (worldObj.isRemote) return;
        ((List<EntityPlayer>) this.worldObj.playerEntities).forEach(it -> {
            ((EntityPlayerMP) it).playerNetServerHandler.sendPacket(this.getDescriptionPacket());
        });
    }
}
