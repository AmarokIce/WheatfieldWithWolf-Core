package club.someoneice.www.common.block;

import club.someoneice.www.Util;
import club.someoneice.www.WWWMain;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockHot extends Block {
    public BlockHot() {
        super(Material.rock);
        this.setBlockName("hotter");
        this.setBlockTextureName(Util.init.getTexturesName("hotter"));
        this.setCreativeTab(WWWMain.TABS);

        GameRegistry.registerBlock(this, "hotter");
    }
}
