package club.someoneice.www.bean;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class TreeFactory extends WorldGenAbstractTree {
  private final int minTreeHeight;
  private final BlockBush Sapling;
  private final Block Wood;
  private final Block Leaf;


  public TreeFactory(boolean flag, int minHeight, BlockBush Sapling, Block Wood, Block Leaf) {
    super(flag);
    this.minTreeHeight = minHeight;
    this.Sapling = Sapling;
    this.Wood = Wood;
    this.Leaf = Leaf;
  }

  public TreeFactory(boolean flag, BlockBush Sapling, Block Leaf) {
    this(flag, 4, Sapling, Blocks.log, Leaf);
  }

  public boolean generate(World world, Random rand, int x, int y, int z) {
    int height = rand.nextInt(4) + this.minTreeHeight;
    boolean flag = true;

    if (y >= 1 && y + height + 1 <= 256) {
      byte leaves;
      int k1;
      Block block;

      for (int searchY = y; searchY <= y + 1 + height; ++searchY) {
        leaves = 1;

        if (searchY == y) leaves = 0;
        if (searchY >= y + 1 + height - 3) leaves = 2;

        for (int j1 = x - leaves; j1 <= x + leaves && flag; ++j1) {
          for (k1 = z - leaves; k1 <= z + leaves && flag; ++k1) {
            if (searchY >= 0 && searchY < 256) {
              if (!this.isReplaceable(world, j1, searchY, k1)) flag = false;
            } else flag = false;
          }
        }
      }

      if (!flag) return false;
      else {
        Block block2 = world.getBlock(x, y - 1, z);

        boolean isSoil = block2.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP,
            this.Sapling);
        if (isSoil && y < 256 - height - 1) {
          block2.onPlantGrow(world, x, y - 1, z, x, y, z);
          leaves = (byte) (height > 4 ? 4 : 3);
          byte b1 = 0;
          int l1;
          int i2;
          int j2;
          int i3;

          for (k1 = y - leaves + height; k1 <= y + height; ++k1) {
            i3 = k1 - (y + height);
            l1 = height > 5 ? (i3 < -3 ? b1 - i3 / 2 : b1 + 2 - i3 / 2) : (i3 < -3 ? b1 - 1 - i3 / 2 : b1
                + 1 - i3 / 2);

            for (i2 = x - l1; i2 <= x + l1; ++i2) {
              j2 = i2 - x;

              for (int k2 = z - l1; k2 <= z + l1; ++k2) {
                int l2 = k2 - z;

                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
                  Block block1 = world.getBlock(i2, k1, k2);

                  if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
                    this.setBlockAndNotifyAdequately(world, i2, k1, k2, this.Leaf, 0);
                  }
                }
              }
            }
          }

          for (k1 = 0; k1 < height; ++k1) {
            block = world.getBlock(x, y + k1, z);

            if (block.isAir(world, x, y + k1, z) || block.isLeaves(world, x, y + k1, z)
                || block.getMaterial() == Material.plants) {
              this.setBlockAndNotifyAdequately(world, x, y + k1, z, this.Wood, 0);
            }
          }

          return true;
        } else return false;
      }
    } else return false;
  }

  @Override
  protected boolean isReplaceable(World world, int x, int y, int z) {
    Block block = world.getBlock(x, y, z);
    return block.isAir(world, x, y, z) || block.isLeaves(world, x, y, z) || block.isWood(world, x, y, z) || block == this.Sapling || func_150523_a(block);
  }

}
