package club.someoneice.www.core.init;

import club.someoneice.togocup.tags.Ingredient;
import club.someoneice.www.api.recipe.RecipeGrinder;
import club.someoneice.www.api.recipe.RecipePot;
import club.someoneice.www.util.WWWApi;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class W3Recipes {
  private W3Recipes() {
  }

  public static final W3Recipes INSTANCE = new W3Recipes();

  public void init() {
    this.recipeCommon();
    this.recipeSmelting();
    this.recipeCutting();
    this.recipeGrinder();
    this.recipeCookingPot();
  }

  private void recipeCommon() {
    GameRegistry.addShapedRecipe(new ItemStack(W3Blocks.pot), "WWW", "RSR", "RRR", 'W', Blocks.planks, 'R', Items.iron_ingot, 'S', Items.stick);
    GameRegistry.addShapedRecipe(new ItemStack(W3Blocks.cutting_board), "R  ", "WWW", 'R', Items.iron_ingot, 'W', Blocks.planks);
    GameRegistry.addShapedRecipe(new ItemStack(W3Items.knife), "  R", " R ", "S  ", 'R', Items.iron_ingot, 'S', Items.stick);
    GameRegistry.addShapedRecipe(new ItemStack(W3Items.grinder_knife, 6), "RR ", " R ", " RR", 'R', Items.iron_ingot);
    GameRegistry.addShapedRecipe(new ItemStack(W3Blocks.grinder), "BKB", "RFR", "BRB", 'B', Blocks.brick_block, 'R', Blocks.stonebrick, 'K', W3Items.grinder_knife, 'F', Blocks.furnace);
    GameRegistry.addShapedRecipe(new ItemStack(W3Items.lunch_bag), " P ", "P P", "PPP", 'P', Items.paper);
  }

  private void recipeCutting() {
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.porkchop), new ItemStack(W3Items.bacon, 2));
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.beef), new ItemStack(W3Items.meat_stuffing, 2));
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.chicken), new ItemStack(W3Items.chicken_breast, 2));
    WWWApi.CUT_RECIPES.put(new ItemStack(W3Items.eggplant), new ItemStack(W3Items.cut_eggplant, 3));
    WWWApi.CUT_RECIPES.put(new ItemStack(W3Items.tomato), new ItemStack(W3Items.cut_tomato, 3));
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.potato), new ItemStack(W3Items.cut_potato, 3));
    WWWApi.CUT_RECIPES.put(new ItemStack(W3Blocks.onion), new ItemStack(W3Items.cut_onion, 3));
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.bread), new ItemStack(W3Items.bread_slice, 6));

    WWWApi.CUT_RECIPES.put(new ItemStack(Items.cooked_porkchop), new ItemStack(W3Items.cooked_bacon, 3));
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.cooked_beef), new ItemStack(W3Items.cooked_meat_stuffing, 3));
    WWWApi.CUT_RECIPES.put(new ItemStack(Items.cooked_chicken), new ItemStack(W3Items.cooked_chicken_breast, 3));

    WWWApi.CUT_RECIPES.put(new ItemStack(W3Blocks.cheese_wheel), new ItemStack(W3Items.cheese, 6));
  }

  private void recipeCookingPot() {
    WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(W3Items.cheese), null, Items.milk_bucket, Items.sugar));
    WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(W3Blocks.cheese_wheel), null, Items.milk_bucket, Items.milk_bucket, Items.sugar, W3Items.salt, Item.getItemFromBlock(Blocks.brown_mushroom)));
    WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(W3Items.butter), null, Items.milk_bucket, W3Items.salt));
    WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(W3Items.salt), null, Items.water_bucket, Items.paper));
    WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(W3Items.cooked_cut_onion), null, W3Items.cut_onion, W3Items.oil));
    WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(W3Items.artificial_meat), null, new Ingredient(W3Tags.MEATABLE_PLANT_TAG), new Ingredient(W3Tags.MEATABLE_PLANT_TAG), new Ingredient(W3Tags.MEATABLE_PLANT_TAG), new Ingredient(W3Items.salt)));
  }

  private void recipeSmelting() {
    GameRegistry.addSmelting(W3Items.bacon, new ItemStack(W3Items.cooked_bacon), 0.2f);
    GameRegistry.addSmelting(W3Items.meat_stuffing, new ItemStack(W3Items.cooked_meat_stuffing), 0.2f);
    GameRegistry.addSmelting(W3Items.chicken_breast, new ItemStack(W3Items.cooked_chicken_breast), 0.2f);
    GameRegistry.addSmelting(W3Items.cut_eggplant, new ItemStack(W3Items.cooked_cut_eggplant), 0.2f);
    GameRegistry.addSmelting(W3Items.bread_slice, new ItemStack(W3Items.toast), 0.2f);
    GameRegistry.addSmelting(W3Items.cornmeal, new ItemStack(Items.bread), 0.2f);
  }

  private void recipeGrinder() {
    WWWApi.GRINDER_RECIPES.add(RecipeGrinder.initRecipe(new Ingredient(W3Tags.OIL_CROP_TAG), new ItemStack(W3Items.oil), new ItemStack(Items.glass_bottle)));
    WWWApi.GRINDER_RECIPES.add(RecipeGrinder.initRecipe(new Ingredient(W3Tags.MILKABLE_PLANT_TAG), new ItemStack(W3Items.plant_base_milk), new ItemStack(Items.glass_bottle)));
    WWWApi.GRINDER_RECIPES.add(RecipeGrinder.initRecipe(new ItemStack(Items.wheat_seeds), new ItemStack(W3Items.cornmeal, 2), null));
  }
}

