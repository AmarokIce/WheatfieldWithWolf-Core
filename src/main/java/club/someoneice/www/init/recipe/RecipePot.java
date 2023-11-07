package club.someoneice.www.init.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class RecipePot {
    public final Item[] input = new Item[6];
    @Nullable public final Item bowl;
    public final ItemStack output;

    public RecipePot(ItemStack output, @Nullable Item bowl, Item ... items) {
        System.arraycopy(items, 0, this.input, 0, Math.min(items.length, 6));

        this.bowl = bowl;
        this.output = output;
    }
}
