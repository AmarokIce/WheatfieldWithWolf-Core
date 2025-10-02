package club.someoneice.www.core.network.proxy;

import club.someoneice.www.client.renderer.CuttingBoardRenderer;
import club.someoneice.www.client.renderer.PotRenderer;
import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.common.tile.TilePot;
import club.someoneice.www.core.init.W3Items;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ClientProxy extends CommonProxy {
  public static int CuttingBoardRenderID;
  public static int PotRenderID;

  @SideOnly(Side.CLIENT)
  public void initRender() {
    ClientProxy.CuttingBoardRenderID = RenderingRegistry.getNextAvailableRenderId();
    ClientProxy.PotRenderID = RenderingRegistry.getNextAvailableRenderId();

    ClientRegistry.bindTileEntitySpecialRenderer(TileCuttingBoard.class, new CuttingBoardRenderer());
    ClientRegistry.bindTileEntitySpecialRenderer(TilePot.class, new PotRenderer());

    RenderingRegistry.registerEntityRenderingHandler(EntityRottenTomato.class, new RenderSnowball(W3Items.rotten_tomato));
  }
}
