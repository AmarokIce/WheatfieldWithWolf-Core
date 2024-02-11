package club.someoneice.www.client.gui.grinder;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.tile.TileGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiGrinder extends GuiContainer {
    private final ResourceLocation tex = new ResourceLocation(WWWMain.MODID ,"textures/gui/grinder.png");
    private final TileGrinder tile;
    public GuiGrinder(TileGrinder tile, World world, int x, int y, int z, InventoryPlayer player) {
        super(new ContainerGrinder(player, world, x, y, z, tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(tex);
        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(foo, bar, 0, 0, this.xSize, this.ySize);

        if (tile.burnTime > 0) {
            int process = (int) (Math.floor(tile.burnTime) * (13 / 8000.0D));
            this.drawTexturedModalRect(foo + 56, bar + 36 + (16 - process), 176, 16 - process, 16, process);
        }

        if (tile.time > 0) {
            int process = (int) (Math.floor(tile.time) * (20 / 200.0D));
            this.drawTexturedModalRect(foo + 79, bar + 37, 176, 17, process, 11);
        }
    }
}
