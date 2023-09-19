package club.someoneice.www.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockPos {
    public int x;
    public int y;
    public int z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Block getBlock(World world) {
        return world.getBlock(x, y, z);
    }
}
