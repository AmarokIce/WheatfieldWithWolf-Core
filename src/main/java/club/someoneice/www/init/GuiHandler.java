package club.someoneice.www.init;

import club.someoneice.www.client.gui.ContainerGrinder;
import club.someoneice.www.client.gui.GuiGrinder;
import club.someoneice.www.common.tile.TileGrinder;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new GuiGrinder((TileGrinder) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new ContainerGrinder(player.inventory, world, x, y, z, (TileGrinder) world.getTileEntity(x, y, z));
        return null;
    }
}
