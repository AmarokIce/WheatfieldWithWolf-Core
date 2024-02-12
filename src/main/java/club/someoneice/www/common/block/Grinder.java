package club.someoneice.www.common.block;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.util.W3Util;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

public class Grinder extends BlockContainer {
    IIcon topIcon;
    IIcon sideIcon;
    IIcon buttomIcon;
    public Grinder() {
        super(Material.rock);
        this.setBlockName("grinder");
        this.setBlockTextureName(W3Util.init.getTexturesName("grinder"));
        this.setCreativeTab(WWWMain.TABS);

        GameRegistry.registerBlock(this, "grinder");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileGrinder();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        if (!world.isRemote)
            player.openGui(WWWMain.INSTANCE, 0, world, x, y ,z);
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileGrinder grinder = (TileGrinder) world.getTileEntity(x, y, z);
        ArrayList<ItemStack> list = Lists.newArrayList(new ItemStack(this));
        list.addAll(grinder.getInventory());
        return list;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon(WWWMain.MODID + ":grinder_side");
        this.sideIcon = icon.registerIcon(WWWMain.MODID + ":grinder_side");
        this.topIcon = icon.registerIcon(WWWMain.MODID + ":grinder_top");
        this.buttomIcon = icon.registerIcon(WWWMain.MODID + ":grinder_bottom");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 1) return topIcon;
        if (side == 0) return buttomIcon;

        return sideIcon;
    }
}
