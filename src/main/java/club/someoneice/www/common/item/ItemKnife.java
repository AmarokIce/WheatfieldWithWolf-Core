package club.someoneice.www.common.item;

import club.someoneice.www.core.WWWMain;
import club.someoneice.www.core.init.W3Items;
import club.someoneice.www.util.W3Util;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemKnife extends ItemSword {
  public static final Supplier<ItemStack> RECIPE_HELPER = Suppliers.memoize(() ->
    new ItemStack(W3Items.knife, 1, Short.MAX_VALUE)
  );

  public ItemKnife() {
    super(ToolMaterial.IRON);
    this.maxStackSize = 1;
    this.canRepair = true;

    this.setUnlocalizedName("knife");
    this.setTextureName(W3Util.init.getResourceName("knife"));
    this.setCreativeTab(WWWMain.TABS);
    this.setMaxDamage(376);

    GameRegistry.registerItem(this, "knife");
  }

  @Override
  public Item getContainerItem() {
    return W3Items.knife;
  }

  @Override
  public ItemStack getContainerItem(ItemStack itemStack) {
    if (!itemStack.isItemStackDamageable()) {
      return itemStack;
    }

    final int damage = itemStack.getItemDamage() - 1;
    if (damage < 0) {
      return null;
    }

    itemStack.setItemDamage(damage);
    return itemStack;
  }

}
