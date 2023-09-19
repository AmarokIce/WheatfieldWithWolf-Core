package club.someoneice.www.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
}
