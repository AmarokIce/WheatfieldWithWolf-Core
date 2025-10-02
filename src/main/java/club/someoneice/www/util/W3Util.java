package club.someoneice.www.util;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.togocup.tags.Ingredient;
import club.someoneice.www.core.WWWMain;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class W3Util {
  public static final W3Util init = new W3Util();

  private W3Util() {
  }

  public String getResourceName(String name) {
    return String.format("%s:%s", WWWMain.ID, name);
  }

  public <A> boolean arraySame(A[] aArray, A[] bArray) {
    if (!checkArraySizeSame(aArray, bArray)) return false;
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
    if (!checkArraySizeSame(recipeIn, input)) return false;
    List<Integer> intArray = Lists.newArrayList();
    for (Ingredient recipeIngredient : recipeIn) {
      if (recipeIngredient == null || recipeIngredient.getObj().isEmpty()) continue;
      boolean has = false;
      for (int i = 0; i < recipeIn.length; i++) {
        if (intArray.contains(i)) continue;
        if (!isStackArrayContains(recipeIngredient.getObj(), input[i])) continue;
        intArray.add(i);
        has = true;
        break;
      }
      if (!has) return false;
    }

    return true;
  }

  public boolean compareIngredientContains(Ingredient ingredient, ItemStack itemStack) {
    if (ingredient == null || ingredient.getObj().isEmpty()) return false;
    return isStackArrayContains(ingredient.getObj(), itemStack);
  }

  public boolean isStackArrayContains(List<ItemStack> array, ItemStack target) {
    return array.stream().anyMatch(it -> Util.itemStackEquals(it, target));
  }

  public void giveOrThrowOut(EntityPlayer player, ItemStack item) {
    if (player.worldObj.isRemote) return;
    if (player.inventory.addItemStackToInventory(item)) return;
    player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, item));
  }

  public void itemThrowOut(World world, ChunkPosition pos, ItemStack... item) {
    itemThrowOut(world, pos, ObjectUtil.objectLet(Lists.newArrayList(), it -> {
      it.addAll(Arrays.asList(item));
    }));
  }

  public void itemThrowOut(World world, ChunkPosition pos, List<ItemStack> item) {
    if (world.isRemote) return;
    item.forEach(it -> {
      if (it == null) return;
      else if (it.getItem() == null) return;
      world.spawnEntityInWorld(new EntityItem(world, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, it));
    });
  }

  public boolean stackArraySame(ItemStack[] A, ItemStack[] B) {
    if (!checkArraySizeSame(A, B)) return false;
    List<Integer> intArray = Lists.newArrayList();
    for (ItemStack a : A) {
      if (a == null) continue;
      boolean has = false;
      for (int i = 0; i < A.length; i++) {
        if (intArray.contains(i) || !Util.itemStackEquals(a, B[i])) continue;
        intArray.add(i);
        has = true;
        break;
      }
      if (!has) return false;
    }

    return true;
  }

  public boolean checkArraySizeSame(Object[] A, Object[] B) {
    List<Object> objectsA = ObjectUtil.objectLet(Lists.newArrayList(), it -> {
          Arrays.stream(A).forEach(item -> {
            if (item == null) return;
            it.add(item);
          });
        }
    );
    List<Object> objectsB = ObjectUtil.objectLet(Lists.newArrayList(), it -> {
          Arrays.stream(B).forEach(item -> {
            if (item == null) return;
            it.add(item);
          });
        }
    );

    return objectsA.size() == objectsB.size();
  }

  @SuppressWarnings("all")
  public SimpleInventory getInvFromItemStack(ItemStack item, int size) {
    SimpleInventory inventory = new SimpleInventory(size);
    if (item == null || item.getTagCompound() == null) return inventory;
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
