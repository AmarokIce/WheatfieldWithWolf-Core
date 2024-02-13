package club.someoneice.www.util;

import club.someoneice.www.common.bean.block.CropFactory;
import club.someoneice.www.common.bean.block.CropHellFactory;
import club.someoneice.www.common.bean.block.CropWaterFactory;
import club.someoneice.www.init.Tags;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SeedTagUtil {
    public static final List<CropFactory> CROPS = Lists.newArrayList();
    public static final List<CropWaterFactory> WATER_CROPS = Lists.newArrayList();
    public static final List<CropHellFactory> HELL_CROPS = Lists.newArrayList();

    public static void inputTags() {
        CROPS.forEach(      (it) -> Tags.SEED_TAG.put(new ItemStack(it.func_149866_i())));
        WATER_CROPS.forEach((it) -> Tags.SEED_TAG.put(new ItemStack(it.func_149866_i())));
        HELL_CROPS.forEach( (it) -> Tags.SEED_TAG.put(new ItemStack(it.func_149866_i())));
    }
}
