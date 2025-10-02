package club.someoneice.www.client.nei;

import club.someoneice.pineapplepsychic.util.MatchUtil;
import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.togocup.tags.Ingredient;
import club.someoneice.www.api.recipe.RecipePot;
import club.someoneice.www.core.WWWMain;
import club.someoneice.www.util.W3Util;
import club.someoneice.www.util.WWWApi;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NEIRecipePot extends TemplateRecipeHandler {
  @Override
  public void loadTransferRects() {
    this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(93, 31, 22, 16), "www_pot"));
  }

  @Override
  public void drawBackground(int recipe) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GuiDraw.changeTexture(this.getGuiTexture());
    GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 160, 80);
  }

  @Override
  public String getGuiTexture() {
    return WWWMain.ID + ":textures/gui/nei/pot.png";
  }

  @Override
  public String getRecipeName() {
    return NEIClientUtils.translate("www_pot");
  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals("www_pot") && getClass() == NEIRecipePot.class) this.findAllRecipe();
    super.loadCraftingRecipes(outputId, results);
  }

  @Override
  public void loadUsageRecipes(String outputId, Object... ingredient) {
    if (outputId.equals("www_pot") && getClass() == NEIRecipePot.class) this.findAllRecipe();
    super.loadUsageRecipes(outputId, ingredient);
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if (result == null) findAllRecipe();
    else WWWApi.POT_RECIPES.forEach(it -> {
      if (Util.itemStackEquals(it.output, result)) arecipes.add(new RecipePotCached(it));
    });
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    if (ingredient == null) findAllRecipe();
    else WWWApi.POT_RECIPES.forEach(it -> {
      if (Arrays.stream(it.input).anyMatch(in -> W3Util.init.compareIngredientContains(in, ingredient)))
        arecipes.add(new RecipePotCached(ingredient, it));
      if (Util.itemStackEquals(ingredient, it.bowl)) arecipes.add(new RecipePotCached(it));
    });
  }

  private void findAllRecipe() {
    WWWApi.POT_RECIPES.stream().map(RecipePotCached::new).forEach(this.arecipes::add);
  }

  private final class RecipePotCached extends CachedRecipe {
    public final List<Ingredient> items;
    public final @Nullable ItemStack item;
    public final @Nullable ItemStack bowl;
    public final ItemStack output;

    public RecipePotCached(@Nonnull ItemStack input, RecipePot recipe) {
      this.items = Lists.newArrayList(Arrays.stream(recipe.input).iterator());
      this.item = input;
      this.bowl = recipe.bowl;
      this.output = recipe.output;
    }

    public RecipePotCached(RecipePot recipe) {
      this.items = Lists.newArrayList(Arrays.stream(recipe.input).iterator());
      this.item = null;
      this.bowl = recipe.bowl;
      this.output = recipe.output;
    }

    @Override
    public List<PositionedStack> getIngredients() {
      ArrayList<PositionedStack> stacks = Lists.newArrayList();
      for (int h = 0; h < 2; h++)
        for (int l = 0; l < 3; l++) {
          Ingredient ingredient = this.items.get(l + h * 3);
          if (ingredient == null) continue;
          if (item != null && MatchUtil.matchItemStackInIngredient(ingredient, item))
            stacks.add(new PositionedStack(this.item, 33 + l * 18, 10 + h * 18));
          else
            stacks.addAll(this.getCycledIngredients(cycleticks / 48, Lists.newArrayList(new PositionedStack(ingredient.getObj(), 33 + l * 18, 10 + h * 18))));


        }

      if (this.bowl != null) stacks.add(new PositionedStack(this.bowl, 91, 40));

      return stacks;
    }

    @Override
    public PositionedStack getResult() {
      return new PositionedStack(this.output, 117, 20);
    }
  }
}
