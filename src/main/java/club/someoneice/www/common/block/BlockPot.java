package club.someoneice.www.common.block;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.tile.TilePot;
import club.someoneice.www.proxy.ClientProxy;
import club.someoneice.www.util.Util;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockPot extends BlockContainer {
    public BlockPot() {
        super(Material.rock);
        this.setBlockName("pot");
        this.setBlockTextureName(Util.init.getTexturesName("pot"));
        this.setCreativeTab(WWWMain.TABS);
        this.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 0.4F, 0.85F);

        GameRegistry.registerBlock(this, "pot");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        if (!world.isRemote)
            player.openGui(WWWMain.INSTANCE, 1, world, x, y ,z);
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TilePot tile = (TilePot) world.getTileEntity(x, y, z);
        ArrayList<ItemStack> list = Lists.newArrayList(new ItemStack(this));
        list.addAll(tile.getInventory());
        return list;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TilePot();
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
}
