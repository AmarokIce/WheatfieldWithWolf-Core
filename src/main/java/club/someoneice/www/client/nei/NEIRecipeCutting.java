package club.someoneice.www.client.nei;

import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.core.WWWMain;
import club.someoneice.www.util.WWWApi;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class NEIRecipeCutting extends TemplateRecipeHandler {
  @Override
  public void loadTransferRects() {
    this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 31, 14, 13), "www_cutting"));
  }

  @Override
  public void drawBackground(int recipe) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GuiDraw.changeTexture(this.getGuiTexture());
    GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 160, 80);
  }

  @Override
  public String getGuiTexture() {
    return WWWMain.ID + ":textures/gui/nei/cutting.png";
  }

  @Override
  public String getRecipeName() {
    return NEIClientUtils.translate("www_cutting");
  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals("www_cutting") && getClass() == NEIRecipeCutting.class) this.findAllRecipe();
    super.loadCraftingRecipes(outputId, results);
  }

  @Override
  public void loadUsageRecipes(String outputId, Object... ingredient) {
    if (outputId.equals("www_cutting") && getClass() == NEIRecipeCutting.class) this.findAllRecipe();
    super.loadUsageRecipes(outputId, ingredient);
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if (result == null) findAllRecipe();
    else WWWApi.CUT_RECIPES.entrySet().forEach(it -> {
      if (Util.itemStackEquals(it.getValue(), result)) arecipes.add(this.getCachedRecipe(it));
    });
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    if (ingredient == null) findAllRecipe();
    else WWWApi.CUT_RECIPES.entrySet().forEach(it -> {
      if (Util.itemStackEquals(it.getKey(), ingredient)) arecipes.add(this.getCachedRecipe(it));
    });
  }

  private void findAllRecipe() {
    WWWApi.CUT_RECIPES.entrySet().stream().map(this::getCachedRecipe).forEach(this.arecipes::add);
  }

  private CachedRecipe getCachedRecipe(Map.Entry<ItemStack, ItemStack> kv) {
    return new CachedRecipe() {
      @Override
      public List<PositionedStack> getIngredients() {
        return Lists.newArrayList(new PositionedStack(kv.getKey(), 49, 23));
      }

      @Override
      public PositionedStack getResult() {
        return new PositionedStack(kv.getValue(), 101, 23);
      }
    };
  }
}
