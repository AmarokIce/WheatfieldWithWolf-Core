package club.someoneice.www.init;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.common.tile.TilePot;
import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.init.recipe.RecipePot;
import club.someoneice.www.util.WWWApi;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Others {
    public Others() {
        this.TileInit();
        this.RecipeSmelting();
        this.RecipeCuttingInit();
        this.RecipeGrinderInit();
        this.RecipeCookingPot();
        this.EntityInit();
    }


    private void TileInit() {
        GameRegistry.registerTileEntity(TileGrinder.class, "tile_grinder");
        GameRegistry.registerTileEntity(TileCuttingBoard.class, "tile_cutting");
        GameRegistry.registerTileEntity(TilePot.class, "tile_pot");
    }

    private void RecipeCuttingInit() {
        WWWApi.CUT_MAP.put(Items.porkchop, new ItemStack(ItemList.bacon, 3));
        WWWApi.CUT_MAP.put(Items.beef, new ItemStack(ItemList.meat_stuffing, 3));
        WWWApi.CUT_MAP.put(Items.chicken, new ItemStack(ItemList.chicken_breast, 3));
        WWWApi.CUT_MAP.put(ItemList.eggplant, new ItemStack(ItemList.cut_eggplant, 3));
        WWWApi.CUT_MAP.put(ItemList.tomato, new ItemStack(ItemList.cut_tomato, 3));
        WWWApi.CUT_MAP.put(Items.potato, new ItemStack(ItemList.cut_potato, 3));
        WWWApi.CUT_MAP.put(Item.getItemFromBlock(BlockList.onion), new ItemStack(ItemList.cut_onion, 3));
        WWWApi.CUT_MAP.put(Items.bread, new ItemStack(ItemList.bread_slice, 2));

        WWWApi.CUT_MAP.put(Items.cooked_porkchop, new ItemStack(ItemList.cooked_bacon, 3));
        WWWApi.CUT_MAP.put(Items.cooked_beef, new ItemStack(ItemList.cooked_meat_stuffing, 3));
        WWWApi.CUT_MAP.put(Items.cooked_chicken, new ItemStack(ItemList.cooked_chicken_breast, 3));
    }

    private void RecipeCookingPot() {
        WWWApi.POT_MAP.add(new RecipePot(new ItemStack(ItemList.cheese), null, Items.milk_bucket, Items.milk_bucket, Items.sugar));
    }

    private void RecipeSmelting() {
        GameRegistry.addSmelting(ItemList.bacon, new ItemStack(ItemList.cooked_bacon), 0.2f);
        GameRegistry.addSmelting(ItemList.meat_stuffing, new ItemStack(ItemList.cooked_meat_stuffing), 0.2f);
        GameRegistry.addSmelting(ItemList.chicken_breast, new ItemStack(ItemList.cooked_chicken_breast), 0.2f);
        GameRegistry.addSmelting(ItemList.cut_eggplant, new ItemStack(ItemList.cooked_cut_eggplant), 0.2f);
        GameRegistry.addSmelting(ItemList.cut_onion, new ItemStack(ItemList.cooked_cut_onion), 0.2f);
        GameRegistry.addSmelting(ItemList.bread_slice, new ItemStack(ItemList.toast), 0.2f);
    }

    private void RecipeGrinderInit() {
        WWWApi.GRINDER.put(Item.getItemFromBlock(BlockList.peanut), RecipeGrinder.initRecipe(new ItemStack(BlockList.peanut), new ItemStack(ItemList.oil), new ItemStack(Items.glass_bottle)));
        WWWApi.GRINDER.put(Item.getItemFromBlock(BlockList.sesame), RecipeGrinder.initRecipe(new ItemStack(BlockList.sesame), new ItemStack(ItemList.oil), new ItemStack(Items.glass_bottle)));
        WWWApi.GRINDER.put(ItemList.olive, RecipeGrinder.initRecipe(new ItemStack(ItemList.olive), new ItemStack(ItemList.oil), new ItemStack(Items.glass_bottle)));
        WWWApi.GRINDER.put(Items.wheat_seeds, RecipeGrinder.initRecipe(new ItemStack(Items.wheat_seeds), new ItemStack(ItemList.cornmeal, 2), null));
    }

    private void EntityInit() {
        EntityRegistry.registerModEntity(EntityRottenTomato.class, "rotten_tomato_throw_entity", 4, WWWMain.INSTANCE, 64, 10, true);
    }
}

