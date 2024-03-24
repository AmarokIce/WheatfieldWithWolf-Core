package club.someoneice.www.init.recipe;

import club.someoneice.togocup.tags.Ingredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class RecipeGrinder {
    public final Ingredient input;
    public final ItemStack output;
    public final int cooking_time;
    public final ItemStack bottle;

    public RecipeGrinder(Ingredient input, ItemStack output, int cooking_time, @Nullable ItemStack bottle) {
        this.input = input;
        this.output = output;
        this.cooking_time = cooking_time;
        this.bottle = bottle;
    }

    public static RecipeGrinder initRecipe(ItemStack input, ItemStack output, @Nullable ItemStack bottle) {
        return new RecipeGrinder(new Ingredient(input), output, 200, bottle);
    }

    public static RecipeGrinder initRecipe(Item input, Item output, @Nullable Item bottle) {
        return initRecipe(new ItemStack(input), new ItemStack(output), new ItemStack(bottle));
    }
}
