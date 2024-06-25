package club.someoneice.www.init;

import club.someoneice.pineapplepsychic.util.ObjectUtil;
import club.someoneice.togocup.tags.ItemStackTag;
import club.someoneice.togocup.tags.Tag;
import club.someoneice.togocup.tags.TagsManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class Tags {
    static final TagsManager manager = TagsManager.manager();
    public static final Tag<Block> DIRT_TAG = manager.registerTag("blockDirt", Blocks.farmland, Blocks.grass, Blocks.dirt);
    public static final Tag<Block> SOUL_SAND_TAG = manager.registerTag("blockSoulBlock", Blocks.soul_sand);
    public static final Tag<Block> WATER_TAG = manager.registerTag("blockWater", Blocks.water, Blocks.flowing_water);
    public static final Tag<Block> HOT_SOURCE = manager.registerTag("blockHotSource", Blocks.fire, Blocks.lava, Blocks.flowing_lava, Blocks.furnace, Blocks.lit_furnace);

    public static final ItemStackTag SEED_TAG = creativeItemStackTag("seeds", Items.wheat_seeds, Items.melon_seeds, Items.pumpkin_seeds, Items.potato, Items.carrot);
    public static final ItemStackTag FRUIT_TAG = creativeItemStackTag("fruit", Blocks.melon_block, Items.melon, Items.apple, Items.golden_apple);
    public static final ItemStackTag VEGETABLE_TAG = creativeItemStackTag("vegetable", Blocks.pumpkin, Items.potato, Items.carrot, Items.golden_carrot);
    public static final ItemStackTag MEAT_TAG = creativeItemStackTag("meat", Items.porkchop, Items.cooked_porkchop, Items.chicken, Items.cooked_chicken, Items.beef, Items.cooked_beef);
    public static final ItemStackTag OIL_CROP_TAG = creativeItemStackTag("oil_crop");
    public static final ItemStackTag MILKABLE_PLANT_TAG = creativeItemStackTag("milkable_plants");
    public static final ItemStackTag MEATABLE_PLANT_TAG = creativeItemStackTag("meatable_plants");

    public static final ItemStackTag MILK = creativeItemStackTag("milk", Items.milk_bucket);
    public static final ItemStackTag OIL_TAG = creativeItemStackTag("oil");

    public Tags() {
        MILK.asOreDict();
        SEED_TAG.asOreDict();
        FRUIT_TAG.asOreDict();
        VEGETABLE_TAG.asOreDict();
    }

    private static ItemStackTag creativeItemStackTag(String name, Object ... o) {
        return ObjectUtil.objectLet(TagsManager.manager().registerItemStackTag(name),
                tag -> Arrays.stream(o).forEach(it -> {
                    if (it instanceof Item)             tag.put(new ItemStack((Item) it));
                    else if (it instanceof Block)       tag.put(new ItemStack((Block) it));
                    else if (it instanceof ItemStack)   tag.put((ItemStack) it);
        }));
    }
}
