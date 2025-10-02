package club.someoneice.www.event;

import club.someoneice.www.core.init.W3Tags;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;
import java.util.Random;

public class EventBlockEvent {
  @SubscribeEvent
  public void onBlockBreakByPlayer(BlockEvent.BreakEvent event) {
    if (event.block != Blocks.tallgrass) return;
    if (event.world.rand.nextDouble() > 0.1d) return;

    List<ItemStack> itemSeeds = W3Tags.SEED_TAG.getList();
    ItemStack seed = itemSeeds.get(new Random().nextInt(itemSeeds.size()));

    EntityItem entityItem = new EntityItem(event.world, event.x, event.y + 0.5d, event.z, seed);
    event.world.spawnEntityInWorld(entityItem);
  }
}
