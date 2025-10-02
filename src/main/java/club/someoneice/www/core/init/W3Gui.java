package club.someoneice.www.core.init;

import club.someoneice.www.common.container.ContainerGrinder;
import club.someoneice.www.client.gui.GuiGrinder;
import club.someoneice.www.common.container.ContainerLunchBag;
import club.someoneice.www.client.gui.GuiLunchBag;
import club.someoneice.www.common.container.ContainerPot;
import club.someoneice.www.client.gui.GuiPot;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.common.tile.TilePot;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class W3Gui implements IGuiHandler {
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == 0) return new GuiGrinder((TileGrinder) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
    if (ID == 1) return new GuiPot((TilePot) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
    if (ID == 2) return new GuiLunchBag(player.inventory);

    return null;
  }

  @Override
  public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    if (id == 0)
      return new ContainerGrinder(player.inventory, world, x, y, z, (TileGrinder) world.getTileEntity(x, y, z));
    if (id == 1) return new ContainerPot(player.inventory, world, x, y, z, (TilePot) world.getTileEntity(x, y, z));
    if (id == 2) return new ContainerLunchBag(player.inventory);

    return null;
  }
}
