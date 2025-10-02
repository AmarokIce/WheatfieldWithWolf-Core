package club.someoneice.www.common.block;

import club.someoneice.www.common.tile.TilePot;
import club.someoneice.www.core.WWWMain;
import club.someoneice.www.core.network.proxy.ClientProxy;
import club.someoneice.www.util.W3Util;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CookingPot extends BlockContainer {
  public CookingPot() {
    super(Material.iron);
    this.setBlockName("pot");
    this.setBlockTextureName(W3Util.init.getResourceName("pot"));
    this.setCreativeTab(WWWMain.TABS);
    this.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 0.4F, 0.85F);
    this.setHardness(0.05f);
    this.setStepSound(Block.soundTypeMetal);

    GameRegistry.registerBlock(this, "pot");
    GameRegistry.registerTileEntity(TilePot.class, "tile_pot");
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    if (!(tileEntity instanceof TilePot)) return false;
    TilePot tile = (TilePot) tileEntity;
    if (!world.isRemote) {
      ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
      player.openGui(WWWMain.INSTANCE, 1, world, x, y, z);
    }
    return true;
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    List<ItemStack> list = Lists.newArrayList();
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    if (tileEntity instanceof TilePot) {
      TilePot tile = (TilePot) tileEntity;
      list.addAll(tile.getItemStackInInventory());
    }

    W3Util.init.itemThrowOut(world, new ChunkPosition(x, y, z), list);
    super.breakBlock(world, x, y, z, block, meta);
  }

  @Override
  public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    return Lists.newArrayList(new ItemStack(this));
  }

  public boolean isToolEffective(String type, int metadata) {
    return true;
  }

  @Override
  public boolean hasTileEntity(int metadata) {
    return true;
  }

  @Override
  public TileEntity createNewTileEntity(World world, int meta) {
    return new TilePot(world, meta);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean isBlockNormalCube() {
    return false;
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public int getRenderType() {
    return ClientProxy.PotRenderID;
  }

  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
    super.onBlockPlacedBy(world, x, y, z, entity, item);
    int face = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
    world.setBlockMetadataWithNotify(x, y, z, face, 2);
  }

  @SideOnly(Side.CLIENT)
  public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
    return Item.getItemFromBlock(this);
  }
}
