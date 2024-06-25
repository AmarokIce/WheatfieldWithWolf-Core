package club.someoneice.www.common.block;

import club.someoneice.www.common.block.factory.CropFactory;
import club.someoneice.www.init.ItemList;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class TomatoCrop extends CropFactory {
    public TomatoCrop() {
        super("tomato_seed", ItemList.tomato);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, face, hitX, hitY, hitX);

        if (world.isRemote) return false;
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 7) return false;

        List<ItemStack> rat = Lists.newArrayList();
        rat.add(new ItemStack(world.rand.nextDouble() < 0.25D ? ItemList.rotten_tomato : ItemList.tomato, world.rand.nextInt(3) + 1));
        if (world.rand.nextDouble() < 0.25D) rat.add(new ItemStack(ItemList.cherry_tomato, 2));
        if (world.rand.nextInt(10) > 6) rat.add(new ItemStack(this.func_149866_i()));
        for (ItemStack item : rat) world.spawnEntityInWorld(new EntityItem(world, x, y + 0.5D, z, item));

        world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        return true;
    }
}
