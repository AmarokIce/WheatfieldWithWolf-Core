package club.someoneice.www.common.block;

import club.someoneice.www.WWWMain;
import club.someoneice.www.proxy.ClientProxy;
import club.someoneice.www.util.Util;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPot extends Block {
    public BlockPot() {
        super(Material.rock);
        this.setBlockName("pot");
        this.setBlockTextureName(Util.init.getTexturesName("pot"));
        this.setCreativeTab(WWWMain.TABS);

        GameRegistry.registerBlock(this, "pot");
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
        return ClientProxy.PotRenderID;
    }
}
