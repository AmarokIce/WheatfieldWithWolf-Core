package club.someoneice.www.init.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class RecipePot {
    public final ItemStack[] input = new ItemStack[6];
    @Nullable public final ItemStack bowl;
    public final ItemStack output;

    public RecipePot(ItemStack output, @Nullable ItemStack bowl, ItemStack ... items) {
        System.arraycopy(items, 0, this.input, 0, Math.min(items.length, 6));

        this.bowl = bowl;
        this.output = output;
    }

    public RecipePot(ItemStack output, @Nullable ItemStack bowl, Item... items) {
        for (int i = 0; i < Math.min(items.length, 6); i ++) input[i] = new ItemStack(items[i]);

        this.bowl = bowl;
        this.output = output;
    }
}
