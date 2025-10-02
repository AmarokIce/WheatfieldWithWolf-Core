package club.someoneice.www.common.block;

import club.someoneice.www.common.block.factory.CropFactory;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class PeaCrop extends CropFactory {
  public PeaCrop() {
    super("pea", null, null, false);
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float hitX, float hitY, float hitZ) {
    super.onBlockActivated(world, x, y, z, player, face, hitX, hitY, hitX);

    int meta = world.getBlockMetadata(x, y, z);
    if (meta == 7) {
      List<ItemStack> rat = Lists.newArrayList();

      rat.add(new ItemStack(this.func_149866_i(), world.rand.nextInt(5) + 1));
      for (ItemStack item : rat) player.inventory.addItemStackToInventory(item);

      world.setBlockMetadataWithNotify(x, y, z, 0, 2);
      return true;
    } else if (meta == 6) {
      player.inventory.addItemStackToInventory(new ItemStack(this.func_149866_i(), this.oneOnly ? 1 : world.rand.nextInt(5) + 2));

      world.setBlockMetadataWithNotify(x, y, z, 0, 2);
      return true;
    }

    return false;
  }
}
