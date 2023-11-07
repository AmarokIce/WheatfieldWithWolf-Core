package club.someoneice.www.util;

import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.init.recipe.RecipePot;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class WWWApi {
    public static final Map<Item, RecipeGrinder> GRINDER = Maps.newHashMap();
    public static final Map<Item, ItemStack> CUT_MAP = Maps.newHashMap();
    public static final List<RecipePot> POT_MAP = Lists.newArrayList();

}
