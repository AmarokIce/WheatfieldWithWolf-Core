package club.someoneice.www.common.item.factory;

import club.someoneice.togocup.tags.ItemStackTag;
import club.someoneice.togocup.tags.Tag;
import club.someoneice.www.core.WWWMain;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class ItemFactory extends Item {
  public ItemFactory(String name) {
    this.setUnlocalizedName(name);
    this.setTextureName(W3Util.init.getResourceName(name));
    this.setCreativeTab(WWWMain.TABS);

    GameRegistry.registerItem(this, name, WWWMain.ID);
  }

  public ItemFactory addTag(Tag<Item> tag) {
    tag.put(this);
    return this;
  }

  public ItemFactory addTag(ItemStackTag... tag) {
    Arrays.stream(tag).forEach(it -> it.put(new ItemStack(this)));
    return this;
  }
}
