package club.someoneice.www.common.block;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.init.ItemList;
import club.someoneice.www.proxy.ClientProxy;
import club.someoneice.www.util.Util;
import club.someoneice.www.util.WWWApi;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Map;

public class CuttingBoard extends BlockContainer {
    public CuttingBoard() {
        super(Material.wood);
        this.setBlockName("cutting_board");
        this.setBlockTextureName(Util.init.getTexturesName("cutting_board"));
        this.setCreativeTab(WWWMain.TABS);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);

        GameRegistry.registerBlock(this, "cutting_board");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCuttingBoard();
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> list = Lists.newArrayList(new ItemStack(this));
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileCuttingBoard) {
            TileCuttingBoard tile = (TileCuttingBoard) tileEntity;
            if (tile.itemInv != null) list.add(tile.itemInv.copy());
        }
        return list;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileCuttingBoard) {
            TileCuttingBoard tile = (TileCuttingBoard) tileEntity;

            if (player.isSneaking() && player.getHeldItem() == null && tile.itemInv != null) {
                player.inventory.addItemStackToInventory(tile.itemInv.copy());
                tile.setItemInv(null);
                tile.updateItem();
                return true;
            }

            if (tile.itemInv == null && player.getHeldItem() != null) {
                tile.setItemInv(player.getHeldItem().copy());
                player.inventory.mainInventory[player.inventory.currentItem] = null;
                world.notifyBlockChange(x, y, z, this);
                tile.updateItem();
                return true;
            } else if (tile.itemInv != null && player.getHeldItem() != null && player.getHeldItem().getItem() == ItemList.knife) {
                return cutting(tile, world, player, x, y, z);
            }
            tile.updateItem();
        }
        return false;
    }

    private boolean cutting(TileCuttingBoard tile, World world, EntityPlayer player, int x, int y, int z) {
        for (Map.Entry<ItemStack, ItemStack> kv : WWWApi.CUT_RECIPES.entrySet()) {
            if (!Util.init.stackSameAs(kv.getKey(), tile.itemInv)) continue;
            ItemStack output = kv.getValue();
            if (!player.inventory.addItemStackToInventory(output.copy()))
                world.spawnEntityInWorld(new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, output.copy()));
            tile.itemInv.stackSize--;
            if (tile.itemInv.stackSize == 0) tile.setItemInv(null);
            else tile.updateItem();
            return true;
        }

        return false;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
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
        return ClientProxy.CuttingBoardRenderID;
    }
}
