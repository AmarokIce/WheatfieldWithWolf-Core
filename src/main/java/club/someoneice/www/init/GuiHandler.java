package club.someoneice.www.init;

import club.someoneice.www.client.gui.grinder.ContainerGrinder;
import club.someoneice.www.client.gui.grinder.GuiGrinder;
import club.someoneice.www.client.gui.pot.ContainerPot;
import club.someoneice.www.client.gui.pot.GuiPot;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.common.tile.TilePot;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new GuiGrinder((TileGrinder) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
        else if (ID == 1) return new GuiPot((TilePot) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
        else return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new ContainerGrinder(player.inventory, world, x, y, z, (TileGrinder) world.getTileEntity(x, y, z));
        else if (ID == 1) return new ContainerPot(player.inventory, world, x, y, z, (TilePot) world.getTileEntity(x, y, z));
        else return null;
    }
}
