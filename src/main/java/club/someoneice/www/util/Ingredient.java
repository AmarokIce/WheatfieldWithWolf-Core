package club.someoneice.www.util;

import club.someoneice.pineapplepsychic.util.ObjectUtil;
import club.someoneice.togocup.tags.ItemStackTag;
import club.someoneice.togocup.tags.Tag;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Ingredient {
    final Object obj;

    public Ingredient(Tag<? extends Item> obj) {
        List<? extends Item> items = obj.getList();
        this.obj = ObjectUtil.objectRun(new ItemStack[items.size()], it -> {
            for (int i = 0; i < items.size(); i ++) {
                it[i] = new ItemStack(items.get(i));
            }
        });
    }

    public Ingredient(ItemStackTag obj) {
        List<ItemStack> items = obj.getList();
        this.obj = ObjectUtil.objectRun(new ItemStack[items.size()], it -> {
            for (int i = 0; i < items.size(); i ++) {
                it[i] = items.get(i).copy();
            }
        });
    }

    public Ingredient(ItemStack obj) {
        this.obj = obj;
    }

    public Ingredient(Item obj) {
        this.obj = new ItemStack(obj);
    }

    public Ingredient(Block obj) {
        this.obj = new ItemStack(obj);
    }

    public Object getObj() {
        return obj;
    }
}
