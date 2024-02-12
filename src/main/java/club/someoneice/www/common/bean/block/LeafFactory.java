package club.someoneice.www.common.bean.block;

import club.someoneice.www.WWWMain;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class LeafFactory extends BlockLeaves {
    protected final String name;
    protected final Item fruit;

    private IIcon icon0, icon1;

    public LeafFactory(String name, Item fruit) {
        this.name = name;
        this.fruit = fruit;
        this.setBlockName(name);
        this.setCreativeTab(WWWMain.TABS);
        this.setTickRandomly(true);

        GameRegistry.registerBlock(this, name);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);

        if (world.getBlockLightValue(x, y + 1, z) >= 9) {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta < 1 && random.nextInt(100 + 1) <= 25)
                world.setBlockMetadataWithNotify(x, y, z, ++meta, 3);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return meta == 0 ? this.icon0 : this.icon1;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        icon0 = register.registerIcon(W3Util.init.getTexturesName(this.name) + 0);
        icon1 = register.registerIcon(W3Util.init.getTexturesName(this.name) + 1);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= 1) {
            player.inventory.addItemStackToInventory(new ItemStack(this.fruit, world.rand.nextInt(3) + 1));
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
            return true;
        }

        return false;
    }

    @Override
    public String[] func_150125_e() {
        return new String[0];
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
        return 0;
    }
}
