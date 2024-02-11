package club.someoneice.www.client.renderer;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.init.ItemList;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class CuttingBoardRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(WWWMain.MODID, "model/textures/cut_board.png");
    public static final ResourceLocation objModelLocation = new ResourceLocation(WWWMain.MODID, "model/cut_board.obj");
    IModelCustom model;

    public CuttingBoardRenderer() {
        this.model = AdvancedModelLoader.loadModel(objModelLocation);
    }
    private final EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld, 0.0D, 0.0D, 0.0D);

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float f) {
        // bindTexture(CommonProxy.texture);

        GL11.glPushMatrix();
        {
            GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            {
                GL11.glRotatef(1.0f, 0F, 1F, 0.5F);
                (FMLClientHandler.instance().getClient()).renderEngine.bindTexture(texture);
                this.model.renderAll();
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();

        if (tileEntity instanceof TileCuttingBoard) {
            TileCuttingBoard tile = (TileCuttingBoard) tileEntity;
            if (tile.itemInv != null) {
                item.setEntityItemStack(tile.itemInv);

                GL11.glPushMatrix();
                {
                    item.hoverStart = 0.0F;
                    RenderItem.renderInFrame = true;

                    if (item.getEntityItem().getItem() == ItemList.knife) {
                        GL11.glTranslatef((float) (posX + 0.26F), (float) (posY), (float) (posZ + 0.255D));
                        GL11.glScaled(1.25D, 1.25D, 1.25D);
                        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 1.0F);
                        GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                    } else {
                        GL11.glTranslatef((float) (posX + 0.30F), (float) (posY - 0.20F), (float) (posZ + 0.50F));
                        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(180.0F, 0.0F, 1.0F, 1.0F);
                    }

                    RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.35D, 0.0F, 0.0F);
                    RenderItem.renderInFrame = false;
                }
                GL11.glPopMatrix();
            } else {
                item.setEntityItemStack(null);
            }
        }
    }
}
