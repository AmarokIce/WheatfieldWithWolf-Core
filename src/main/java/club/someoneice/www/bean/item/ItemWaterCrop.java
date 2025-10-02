package club.someoneice.www.bean.item;

import club.someoneice.www.bean.block.CropWaterFactory;
import club.someoneice.www.core.init.W3Tags;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWaterCrop extends ItemBlock {
  @SideOnly(Side.CLIENT)
  private IIcon icon;
  private final String name;

  public ItemWaterCrop(Block block) {
    super(block);

    this.name = ((CropWaterFactory) block).name;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister register) {
    this.icon = register.registerIcon(W3Util.init.getResourceName(this.name));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIconFromDamage(int meta) {
    return this.icon;
  }

  @Override
  public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
    // DO Nothing.
    return false;
  }

  public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
    MovingObjectPosition moving = getMovingObjectPositionFromPlayer(world, player, true);
    if (moving != null && moving.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
      int x = moving.blockX;
      int y = moving.blockY;
      int z = moving.blockZ;

      if (moving.sideHit != 1) return item;
      if (W3Tags.WATER_TAG.has(world.getBlock(x, y, z))) {
        int meta = this.getMetadata(item.getItemDamage());
        this.field_150939_a.onBlockPlaced(world, x, y, z, 1, (float) moving.hitVec.xCoord, (float) moving.hitVec.yCoord, (float) moving.hitVec.zCoord, meta);

        if (placeBlockAt(item, player, world, x, y + 1, z, 1, (float) moving.hitVec.xCoord, (float) moving.hitVec.yCoord, (float) moving.hitVec.zCoord, meta)) {
          world.playSoundEffect((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
          --item.stackSize;
        }
      }
    }
    return item;
  }
}
