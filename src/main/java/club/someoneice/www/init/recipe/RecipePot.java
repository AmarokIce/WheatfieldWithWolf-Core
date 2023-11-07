package club.someoneice.www.init.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipePot {
    public final Item[] input = new Item[6];
    public final Item bowl;
    public final ItemStack output;

    public RecipePot(ItemStack output, Item ... items) {
        System.arraycopy(items, 0, this.input, 0, 6);
        this.bowl = items[6];
        this.output = output;
    }
}
