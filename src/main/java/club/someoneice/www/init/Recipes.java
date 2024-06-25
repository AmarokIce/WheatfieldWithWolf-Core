package club.someoneice.www.init;

import club.someoneice.togocup.tags.Ingredient;
import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.init.recipe.RecipePot;
import club.someoneice.www.util.WWWApi;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Recipes {
    private Recipes() {}

    public static final Recipes INSTANCE = new Recipes();

    public void init() {
        this.recipeCommon();
        this.recipeSmelting();
        this.recipeCutting();
        this.recipeGrinder();
        this.recipeCookingPot();
    }

    private void recipeCommon() {
        GameRegistry.addShapedRecipe(new ItemStack(BlockList.pot), "WWW", "RSR", "RRR", 'W', Blocks.planks, 'R', Items.iron_ingot, 'S', Items.stick);
        GameRegistry.addShapedRecipe(new ItemStack(BlockList.cutting_board), "R  ", "WWW", 'R', Items.iron_ingot, 'W', Blocks.planks);
        GameRegistry.addShapedRecipe(new ItemStack(ItemList.knife), "  R", " R ", "S  ", 'R', Items.iron_ingot, 'S', Items.stick);
        GameRegistry.addShapedRecipe(new ItemStack(ItemList.grinder_knife, 6), "RR ", " R ", " RR", 'R', Items.iron_ingot);
        GameRegistry.addShapedRecipe(new ItemStack(BlockList.grinder), "BKB", "RFR", "BRB", 'B', Blocks.brick_block, 'R', Blocks.stonebrick, 'K', ItemList.grinder_knife, 'F', Blocks.furnace);
        GameRegistry.addShapedRecipe(new ItemStack(ItemList.lunch_bag), " P ", "P P", "PPP", 'P', Items.paper);
    }

    private void recipeCutting() {
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.porkchop)        , new ItemStack(ItemList.bacon, 2));
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.beef)            , new ItemStack(ItemList.meat_stuffing, 2));
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.chicken)         , new ItemStack(ItemList.chicken_breast, 2));
        WWWApi.CUT_RECIPES.put(new ItemStack(ItemList.eggplant)     , new ItemStack(ItemList.cut_eggplant, 3));
        WWWApi.CUT_RECIPES.put(new ItemStack(ItemList.tomato)       , new ItemStack(ItemList.cut_tomato, 3));
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.potato)          , new ItemStack(ItemList.cut_potato, 3));
        WWWApi.CUT_RECIPES.put(new ItemStack(BlockList.onion)       , new ItemStack(ItemList.cut_onion, 3));
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.bread)           , new ItemStack(ItemList.bread_slice, 6));

        WWWApi.CUT_RECIPES.put(new ItemStack(Items.cooked_porkchop) , new ItemStack(ItemList.cooked_bacon, 3));
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.cooked_beef)     , new ItemStack(ItemList.cooked_meat_stuffing, 3));
        WWWApi.CUT_RECIPES.put(new ItemStack(Items.cooked_chicken)  , new ItemStack(ItemList.cooked_chicken_breast, 3));

        WWWApi.CUT_RECIPES.put(new ItemStack(BlockList.cheese_wheel), new ItemStack(ItemList.cheese, 6));
    }

    private void recipeCookingPot() {
        WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(ItemList.cheese), null, Items.milk_bucket, Items.sugar));
        WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(BlockList.cheese_wheel), null, Items.milk_bucket, Items.milk_bucket, Items.sugar, ItemList.salt, Item.getItemFromBlock(Blocks.brown_mushroom)));
        WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(ItemList.butter), null, Items.milk_bucket, ItemList.salt));
        WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(ItemList.salt), null, Items.water_bucket, Items.paper));
        WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(ItemList.cooked_cut_onion), null, ItemList.cut_onion, ItemList.oil));
        WWWApi.POT_RECIPES.add(new RecipePot(new ItemStack(ItemList.artificial_meat), null, new Ingredient(Tags.MEATABLE_PLANT_TAG), new Ingredient(Tags.MEATABLE_PLANT_TAG), new Ingredient(Tags.MEATABLE_PLANT_TAG), new Ingredient(ItemList.salt)));
    }

    private void recipeSmelting() {
        GameRegistry.addSmelting(ItemList.bacon, new ItemStack(ItemList.cooked_bacon), 0.2f);
        GameRegistry.addSmelting(ItemList.meat_stuffing, new ItemStack(ItemList.cooked_meat_stuffing), 0.2f);
        GameRegistry.addSmelting(ItemList.chicken_breast, new ItemStack(ItemList.cooked_chicken_breast), 0.2f);
        GameRegistry.addSmelting(ItemList.cut_eggplant, new ItemStack(ItemList.cooked_cut_eggplant), 0.2f);
        GameRegistry.addSmelting(ItemList.bread_slice, new ItemStack(ItemList.toast), 0.2f);
        GameRegistry.addSmelting(ItemList.cornmeal, new ItemStack(Items.bread), 0.2f);
    }

    private void recipeGrinder() {
        WWWApi.GRINDER_RECIPES.add(RecipeGrinder.initRecipe(new Ingredient(Tags.OIL_CROP_TAG), new ItemStack(ItemList.oil), new ItemStack(Items.glass_bottle)));
        WWWApi.GRINDER_RECIPES.add(RecipeGrinder.initRecipe(new Ingredient(Tags.MILKABLE_PLANT_TAG), new ItemStack(ItemList.plant_base_milk), new ItemStack(Items.glass_bottle)));
        WWWApi.GRINDER_RECIPES.add(RecipeGrinder.initRecipe(new ItemStack(Items.wheat_seeds), new ItemStack(ItemList.cornmeal, 2), null));
    }
}

