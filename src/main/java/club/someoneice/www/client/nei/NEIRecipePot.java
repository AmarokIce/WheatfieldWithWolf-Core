package club.someoneice.www.client.nei;

import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.www.WWWMain;
import club.someoneice.www.init.recipe.RecipePot;
import club.someoneice.www.util.WWWApi;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

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
        return WWWMain.MODID + ":textures/gui/nei/pot.png";
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
            if (Util.itemStackEquals(it.output, result)) arecipes.add(this.getCachedRecipe(it));
        });
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient == null) findAllRecipe();
        else WWWApi.POT_RECIPES.forEach(it -> {
            if (Arrays.stream(it.input).anyMatch(in -> Util.itemStackEquals(in, ingredient)))
                arecipes.add(this.getCachedRecipe(it));
        });
    }

    private void findAllRecipe() {
        WWWApi.POT_RECIPES.stream().map(this::getCachedRecipe).forEach(this.arecipes::add);
    }

    private CachedRecipe getCachedRecipe(RecipePot it) {
        return new CachedRecipe() {
            @Override
            public List<PositionedStack> getIngredients() {
                ArrayList<PositionedStack> stacks = Lists.newArrayList();
                for (int h = 0; h < 2; h ++) for (int l = 0; l < 3; l ++) {
                    ItemStack item = it.input[l + h * 3];
                    if (item != null) stacks.add(new PositionedStack(item, 33 + l * 18, 10 + h * 18));
                }

                if (it.bowl != null) stacks.add(new PositionedStack(it.bowl, 91, 40));

                return stacks;
            }

            @Override
            public PositionedStack getResult() {
                return new PositionedStack(it.output, 117, 20);
            }
        };
    }
}
