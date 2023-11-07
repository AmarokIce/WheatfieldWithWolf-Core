package club.someoneice.www.init;

import club.someoneice.togocup.tags.Tag;
import club.someoneice.togocup.tags.TagsManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Tags {
    static final TagsManager manager = TagsManager.manager();
    public static final Tag<Block> DIRT_TAG = manager.registerTag("blockDirt", Blocks.farmland, Blocks.grass, Blocks.dirt);
    public static final Tag<Block> SOUL_SAND_TAG = manager.registerTag("blockSoulBlock", Blocks.soul_sand);
    public static final Tag<Block> WATER_TAG = manager.registerTag("blockWater", Blocks.water, Blocks.flowing_water);

    public static final Tag<Item> SEED_TAG = manager.registerTag("seeds", Items.wheat_seeds, Items.melon_seeds, Items.pumpkin_seeds, Items.potato, Items.carrot);
    public static final Tag<Block> HOT_SOURCE = manager.registerTag("blockHotSource", Blocks.fire, Blocks.lava, Blocks.flowing_lava, Blocks.furnace, Blocks.lit_furnace);
}
