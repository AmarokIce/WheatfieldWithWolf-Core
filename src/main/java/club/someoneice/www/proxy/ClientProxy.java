package club.someoneice.www.proxy;

import club.someoneice.www.client.renderer.CuttingBoardRenderer;
import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.init.ItemList;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ClientProxy extends CommonProxy{
    public static int CuttingBoardRenderID;

    @SideOnly(Side.CLIENT)
    public void initRender() {
        ClientProxy.CuttingBoardRenderID = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer(TileCuttingBoard.class, new CuttingBoardRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityRottenTomato.class, new RenderSnowball(ItemList.rotten_tomato));
    }
}
