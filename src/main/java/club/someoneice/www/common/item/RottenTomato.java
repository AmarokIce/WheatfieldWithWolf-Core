package club.someoneice.www.common.item;

import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.item.factory.ItemFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RottenTomato extends ItemFactory {
  public RottenTomato() {
    super("rotten_tomato");
  }

  public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
    if (!player.capabilities.isCreativeMode) {
      --item.stackSize;
    }

    world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

    if (!world.isRemote) {
      world.spawnEntityInWorld(new EntityRottenTomato(world, player));
    }

    return item;
  }
}
