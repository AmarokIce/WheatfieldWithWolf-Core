package club.someoneice.www.util;

import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.WWWMain;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class W3Util {
    public static final W3Util init = new W3Util();

    private W3Util() {}

    public String getTexturesName(String name) {
        return WWWMain.MODID + ":" + name;
    }

    public <A> boolean arraySame(A[] aArray, A[] bArray){
        if (aArray.length != bArray.length) return false;
        List<Integer> intArray = Lists.newArrayList();

        for (A a : aArray) {
            boolean has = false;
            for (int i = 0; i < bArray.length; i++) {
                if (!intArray.contains(i) && a == bArray[i]) {
                    intArray.add(i);
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }

        return true;
    }

    public boolean compareRecipe(final Ingredient[] recipeIn, final ItemStack[] input) {
        if (recipeIn.length != input.length) return false;
        List<Integer> intArray = Lists.newArrayList();
        for (Ingredient recipeIngredient : recipeIn) {
            if (recipeIngredient == null || recipeIngredient.obj == null) continue;
            Object obj = recipeIngredient.obj;
            boolean has = false;
            for (int i = 0; i < recipeIn.length; i++) {
                if (intArray.contains(i)) continue;
                if (obj instanceof ItemStack[]) {
                    if (!isStackArrayContains((ItemStack[]) obj, input[i])) continue;
                    intArray.add(i);
                    has = true;
                    break;
                } else if (obj instanceof ItemStack) {
                    if (!Util.itemStackEquals((ItemStack) obj, input[i])) continue;
                    intArray.add(i);
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }

        return true;
    }

    public boolean compareIngredientContains(Ingredient ingredient, ItemStack itemStack) {
        if (ingredient == null || ingredient.obj == null) return false;
        Object obj = ingredient.obj;
        if (obj instanceof ItemStack[])
            return isStackArrayContains((ItemStack[]) obj, itemStack);
        if (obj instanceof ItemStack)
            return Util.itemStackEquals((ItemStack) obj, itemStack);

        return false;
    }

    public boolean isStackArrayContains(ItemStack[] array, ItemStack target) {
        return Arrays.stream(array).anyMatch(it -> Util.itemStackEquals(it, target));
    }

    public void giveOrThrowOut(EntityPlayer player, ItemStack item) {
        if (player.inventory.addItemStackToInventory(item)) return;
        player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, item));
    }

    public boolean stackArraySame(ItemStack[] A, ItemStack[] B) {
        if (A.length != B.length) return false;
        List<Integer> intArray = Lists.newArrayList();
        for (ItemStack a : A) {
            if (a == null) continue;
            boolean has = false;
            for (int i = 0; i < A.length; i++) {
                if (!intArray.contains(i) && Util.itemStackEquals(a, B[i])) {
                    intArray.add(i);
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }

        return true;
    }

    @SuppressWarnings("all")
    public SimpleInventory getInvFromItemStack(ItemStack item, int size) {
        if (item == null || item.getTagCompound() == null) return null;
        SimpleInventory inventory = new SimpleInventory(size);
        if (item.getTagCompound().hasKey("inv_data"))
            inventory.load(item.getTagCompound().getCompoundTag("inv_data"));
        return inventory;
    }

    @SuppressWarnings("all")
    public void setInvFromItemStack(ItemStack item, SimpleInventory inventory) {
        if (item == null) return;
        if (item.getTagCompound() == null) item.stackTagCompound = new NBTTagCompound();
        item.getTagCompound().setTag("inv_data", inventory.write());
    }
}
