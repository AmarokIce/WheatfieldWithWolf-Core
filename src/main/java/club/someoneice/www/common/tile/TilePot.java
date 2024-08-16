package club.someoneice.www.common.tile;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.init.Tags;
import club.someoneice.www.init.recipe.RecipePot;
import club.someoneice.www.util.W3Util;
import club.someoneice.www.util.WWWApi;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class TilePot extends TileEntity {
    private final SimpleInventory inventory = new SimpleInventory("cooking_pot", 9, this) {
        @Override
        public boolean isItemValidForSlot(int slot, ItemStack item) {
            return slot < 7;
        }

        @Override
        public String getInventoryName() {
            return "www.pot.name";
        }
    };
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
        if (canBurn() && !this.getItemStackInInventory().isEmpty()) checkTheRecipe();
    }

    private void checkTheRecipe() {
        ItemStack[] itemInput = new ItemStack[6];

        for (int i = 0; i < 6; i ++) if (this.inventory.getStackInSlot(i) != null) itemInput[i] = this.inventory.getStackInSlot(i);

        Optional<RecipePot> recipeCup = WWWApi.POT_RECIPES.stream().filter(it -> W3Util.init.compareRecipe(it.input, itemInput)).findFirst();

        if (!recipeCup.isPresent()) {
            time = 0;
            return;
        }

        RecipePot recipe = recipeCup.get();
        if (++this.time < 200 || (recipe.bowl != null && !Util.itemStackEquals(this.inventory.getStackInSlot(6), recipe.bowl))) return;
        crafting(recipe);
        this.time = 0;
    }

    private void crafting(RecipePot recipe) {
        if (this.inventory.getStackInSlot(7) == null) this.inventory.setInventorySlotContents(7, recipe.output.copy());
        else if (this.inventory.getStackInSlot(7).getItem() == recipe.output.getItem()) this.inventory.getStackInSlot(7).stackSize++;
        else return;

        for (int i = 0; i < 7; i++) {
            ItemStack item = this.inventory.getStackInSlot(i);
            if (item == null) continue;
            if (item.getItem().hasContainerItem(item))
                W3Util.init.itemThrowOut(this.worldObj, new ChunkPosition(this.xCoord, this.yCoord + 1, this.zCoord), item.getItem().getContainerItem(item));
            this.inventory.decrStackSize(i, 1);
        }
    }

    public boolean canBurn() {
        return Tags.HOT_SOURCE.has(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("time", this.time);

        nbt.setTag("inventory", inventory.write());

        super.writeToNBT(nbt);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.time = nbt.getInteger("time");

        if (nbt.hasKey("inventory"))
            this.inventory.load(nbt.getCompoundTag("inventory"));
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
