package club.someoneice.www;

import club.someoneice.www.init.recipe.RecipeGrinder;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class WWWApi {
    public static final Map<Item, RecipeGrinder> GRINDER = Maps.newHashMap();
    public static final Map<Item, ItemStack> CUT_MAP = Maps.newHashMap();

}
