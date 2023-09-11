package club.someoneice.www.init.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class RecipeGrinder {
    public final ItemStack input;
    public final ItemStack output;
    public final int cooking_time;
    public final ItemStack bottle;

    private RecipeGrinder(ItemStack input, ItemStack output, int cooking_time, @Nullable ItemStack bottle) {
        this.input = input;
        this.output = output;
        this.cooking_time = cooking_time;
        this.bottle = bottle;
    }

    public static RecipeGrinder initRecipe(ItemStack input, ItemStack output, int cooking_time, @Nullable ItemStack bottle) {
        return new RecipeGrinder(input, output, cooking_time, bottle);
    }

    public static RecipeGrinder initRecipe(Item input, Item output, int cooking_time, Item bottle) {
        return initRecipe(new ItemStack(input), new ItemStack(output), cooking_time, new ItemStack(bottle));
    }
}
