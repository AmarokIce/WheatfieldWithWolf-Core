package club.someoneice.www.common.item;

import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.www.WWWMain;
import club.someoneice.www.common.bean.item.ItemFactory;
import club.someoneice.www.util.W3Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.UUID;

@SuppressWarnings("all")
public class ItemLunchBag extends ItemFactory {
    public ItemLunchBag() {
        super("lunch_bag");
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        onUse(item, world, player);
        return item;
    }

    @Override
    public boolean onItemUseFirst(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        onUse(item, world, player);
        return true;
    }

    private static void onUse(ItemStack item, World world, EntityPlayer player) {
        initNBT(item);
        if (player.isSneaking()) player.openGui(WWWMain.INSTANCE, 2, world, player.serverPosX, player.serverPosY, player.serverPosZ);
        else if (player.canEat(false)) {
            player.setItemInUse(item, 32);
        }
    }

    private static void initNBT(@Nonnull ItemStack item) {
        if (item.stackTagCompound == null) item.stackTagCompound = new NBTTagCompound();
        NBTTagCompound nbt = item.getTagCompound();
        if (nbt.hasKey("inv_uuid")) return;
        nbt.setString("inv_uuid", UUID.randomUUID().toString());
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        SimpleInventory inventory = W3Util.init.getInvFromItemStack(item, 3);
        for (int slot = 0; slot < inventory.getSizeInventory(); slot ++) {
            ItemStack it = inventory.getStackInSlot(slot);
            if (it == null) continue;
            if (!(it.getItem() instanceof ItemFood)) continue;
            ItemFood food = (ItemFood) it.getItem();
            player.getFoodStats().func_151686_a(food, it);

            if (food.hasContainerItem(item)) W3Util.init.giveOrThrowOut(player, food.getContainerItem(item));

            inventory.decrStackSize(slot, 1);
            W3Util.init.setInvFromItemStack(item, inventory);
            return item;
        }
        return item;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

}
