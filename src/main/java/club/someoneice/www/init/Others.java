package club.someoneice.www.init;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.init.recipe.RecipeGrinder;
import club.someoneice.www.util.WWWApi;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Others {
    public Others() {
        this.TileInit();
        this.RecipeCuttingInit();
        this.RecipeGrinderInit();
        this.EntityInit();
    }


    private void TileInit() {
        GameRegistry.registerTileEntity(TileGrinder.class, "tile_grinder");
        GameRegistry.registerTileEntity(TileCuttingBoard.class, "tile_cutting");
    }

    private void RecipeCuttingInit() {
        WWWApi.CUT_MAP.put(Items.porkchop, new ItemStack(ItemList.bacon, 3));
        WWWApi.CUT_MAP.put(Items.beef, new ItemStack(ItemList.meat_stuffing, 3));
        WWWApi.CUT_MAP.put(Items.chicken, new ItemStack(ItemList.chicken_breast, 3));
    }

    private void RecipeGrinderInit() {
        WWWApi.GRINDER.put(Item.getItemFromBlock(BlockList.peanut), RecipeGrinder.initRecipe(new ItemStack(BlockList.peanut), new ItemStack(ItemList.oil), new ItemStack(Items.glass_bottle)));
        WWWApi.GRINDER.put(Items.wheat_seeds, RecipeGrinder.initRecipe(new ItemStack(Items.wheat_seeds), new ItemStack(ItemList.cornmeal, 2), null));}

    private void EntityInit() {
        EntityRegistry.registerModEntity(EntityRottenTomato.class, "rotten_tomato_throw_entity", 4, WWWMain.INSTANCE, 64, 10, true);
    }
}

