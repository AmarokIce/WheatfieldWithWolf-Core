package club.someoneice.www.network.message;

import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.network.RawPackageHandle;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class CuttingBoardMessage extends AbstractMessageKey<CuttingBoardMessage> {
    public int x;
    public int y;
    public int z;

    public int id;
    public int size;
    public int meta;

    public CuttingBoardMessage() {}

    public CuttingBoardMessage(TileCuttingBoard tile, ItemStack item) {
        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;

        this.id   = Item.getIdFromItem(item.getItem());
        this.size = item.stackSize;
        this.meta = item.getItemDamage();
    }

    @Override
    public IMessage onMessage(RawPackageHandle message, MessageContext ctx) {
        TileEntity blockEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(this.x, this.y, this.z);
        if (blockEntity instanceof TileCuttingBoard) {
            TileCuttingBoard tile = (TileCuttingBoard) blockEntity;

            ItemStack item = new ItemStack(Item.getItemById(this.id), this.size, this.meta);

            tile.setItemInv(item);
        }

        return null;
    }

    @Override
    public void writeTo(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);

        buf.writeInt(this.id);
        buf.writeInt(this.size);
        buf.writeInt(this.meta);
    }

    @Override
    public void readFrom(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        this.id   = buf.readInt();
        this.size = buf.readInt();
        this.meta = buf.readInt();
    }
}
