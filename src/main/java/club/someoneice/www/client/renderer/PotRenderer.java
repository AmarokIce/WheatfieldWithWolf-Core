package club.someoneice.www.client.renderer;

import club.someoneice.www.core.WWWMain;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class PotRenderer extends TileEntitySpecialRenderer {
  public static final ResourceLocation texture = new ResourceLocation(WWWMain.ID, "model/textures/pot.png");
  public static final ResourceLocation objModelLocation = new ResourceLocation(WWWMain.ID, "model/pot.obj");
  IModelCustom model;

  public PotRenderer() {
    this.model = AdvancedModelLoader.loadModel(objModelLocation);
  }

  @Override
  public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float f) {
    GL11.glPushMatrix();
    {
      GL11.glTranslated(posX + 0.45D, posY + 0.75D, posZ + 0.55D);
      GL11.glScalef(1.5f, 1.5f, 1.5f);
      GL11.glPushMatrix();
      {
        GL11.glRotatef(1.0f, 0F, 1.0F, 0.5F);
        (FMLClientHandler.instance().getClient()).renderEngine.bindTexture(texture);
        this.model.renderAll();
      }
      GL11.glPopMatrix();
    }
    GL11.glPopMatrix();
  }
}
