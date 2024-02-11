package club.someoneice.www.client.gui.pot;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.tile.TilePot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiPot extends GuiContainer {
    private final ResourceLocation tex = new ResourceLocation(WWWMain.MODID ,"textures/gui/pot.png");
    private final TilePot tile;
    public GuiPot(TilePot tile, World world, int x, int y, int z, InventoryPlayer player) {
        super(new ContainerPot(player, world, x, y, z, tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(tex);
        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(foo, bar, 0, 0, this.xSize, this.ySize);

        if (tile.canBurn()) {
            this.drawTexturedModalRect(foo + 56, bar + 59 , 176, 0, 16, 16);
        }

        int process = (int) ((double) tile.time * (22 / 200.0D));
        this.drawTexturedModalRect(foo + 93, bar + 32, 176, 17, process, 16);
    }
}
