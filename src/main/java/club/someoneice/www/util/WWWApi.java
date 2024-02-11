package club.someoneice.www.util;

import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.init.recipe.RecipePot;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class WWWApi {
    public static final List<RecipeGrinder> GRINDER_RECIPES = Lists.newArrayList();
    public static final Map<ItemStack, ItemStack> CUT_RECIPES = Maps.newHashMap();
    public static final List<RecipePot> POT_RECIPES = Lists.newArrayList();
}
