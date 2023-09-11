package club.someoneice.www.init;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.common.tile.TileGrinder;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Others {
    public Others() {
        this.TileInit();
        this.RecipeCuttingInit();
        this.EntityInit();
    }


    private void TileInit() {
        GameRegistry.registerTileEntity(TileGrinder.class, "tile_grinder");
        GameRegistry.registerTileEntity(TileCuttingBoard.class, "tile_cutting");
    }

    private void RecipeCuttingInit() {
        // WWWApi.CUT_MAP.put(ItemList.eggplant, RecipeCutting.initCutting())
    }

    private void EntityInit() {
        EntityRegistry.registerModEntity(EntityRottenTomato.class, "rotten_tomato_throw_entity", 4, WWWMain.INSTANCE, 64, 10, true);
    }
}

