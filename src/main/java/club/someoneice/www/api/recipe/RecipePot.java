package club.someoneice.www.api.recipe;

import club.someoneice.togocup.tags.Ingredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;

public class RecipePot {
  public final Ingredient[] input = new Ingredient[6];
  @Nullable
  public final ItemStack bowl;
  public final ItemStack output;

  public RecipePot(ItemStack output, @Nullable ItemStack bowl, Ingredient... items) {
    System.arraycopy(items, 0, this.input, 0, Math.min(items.length, 6));

    this.bowl = bowl;
    this.output = output;
  }

  public RecipePot(ItemStack output, @Nullable ItemStack bowl, ItemStack... items) {
    this(output, bowl, Arrays.stream(items).map(Ingredient::new).toArray(Ingredient[]::new));
  }

  public RecipePot(ItemStack output, @Nullable ItemStack bowl, Item... items) {
    for (int i = 0; i < Math.min(items.length, 6); i++) input[i] = new Ingredient(items[i]);

    this.bowl = bowl;
    this.output = output;
  }
}
