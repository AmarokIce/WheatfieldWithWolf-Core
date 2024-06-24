package club.someoneice.www.client.nei;

import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.WWWMain;
import club.someoneice.www.init.ItemList;
import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.util.W3Util;
import club.someoneice.www.util.WWWApi;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NEIRecipeGrinder extends TemplateRecipeHandler {
    @Override
    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(77, 23, 16, 16), "www_grinder"));
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 160, 80);
    }

    @Override
    public String getGuiTexture() {
        return WWWMain.MODID + ":textures/gui/nei/grinder.png";
    }

    @Override
    public String getRecipeName() {
        return NEIClientUtils.translate("www_grinder");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("www_grinder") && getClass() == NEIRecipeGrinder.class) this.findAllRecipe();
        super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadUsageRecipes(String outputId, Object... ingredient) {
        if (outputId.equals("www_grinder") && getClass() == NEIRecipeGrinder.class) this.findAllRecipe();
        super.loadUsageRecipes(outputId, ingredient);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) findAllRecipe();
        else WWWApi.GRINDER_RECIPES.forEach(it -> {
            if (Util.itemStackEquals(it.output, result)) arecipes.add(new RecipeGrinderCached(it));
        });
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient == null) findAllRecipe();
        else if (Util.itemStackEquals(new ItemStack(ItemList.grinder_knife), ingredient)) findAllRecipe();
        else WWWApi.GRINDER_RECIPES.forEach(it -> {
            if (W3Util.init.compareIngredientContains(it.input, ingredient))
                arecipes.add(new RecipeGrinderCached(ingredient, it.bottle, it.output));
            else if (Util.itemStackEquals(it.bottle, ingredient)) {
                arecipes.add(new RecipeGrinderCached(it));
            }
        });
    }

    private void findAllRecipe() {
        WWWApi.GRINDER_RECIPES.stream().map(RecipeGrinderCached::new).forEach(this.arecipes::add);
    }

    private final class RecipeGrinderCached extends CachedRecipe {
        public final ImmutableList<ItemStack> items;
        public final @Nullable ItemStack bottle;
        public final ItemStack output;

        public RecipeGrinderCached(ItemStack input, @Nullable ItemStack bottle, ItemStack output) {
            this.items = ImmutableList.of(input);
            this.bottle = bottle;
            this.output = output;
        }

        public RecipeGrinderCached(RecipeGrinder recipe) {
            this.items = recipe.input.getObj();
            this.bottle = recipe.bottle;
            this.output = recipe.output;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> stacks = Lists.newArrayList();
            stacks.addAll(this.getCycledIngredients(cycleticks / 48, Lists.newArrayList(new PositionedStack(this.items, 31, 9))));
            if (this.bottle != null) stacks.add(new PositionedStack(this.bottle, 51, 9));

            return stacks;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(this.output, 106, 23);
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            ArrayList<PositionedStack> stacks = Lists.newArrayList();
            stacks.add(new PositionedStack(new ItemStack(ItemList.grinder_knife), 42, 36));
            return stacks;
        }
    }
}
