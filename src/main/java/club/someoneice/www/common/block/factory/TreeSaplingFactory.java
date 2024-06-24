package club.someoneice.www.common.block.factory;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.factory.TreeFactory;
import club.someoneice.www.init.Tags;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("unused")
public class TreeSaplingFactory extends BlockBush implements IGrowable {
    private final Block leaf;
    public TreeSaplingFactory(String name, Block leaf) {
        super(Material.plants);

        float f = 0.5F;
        this.setLightLevel(0.3f);
        this.setBlockName(name);
        this.setBlockTextureName(W3Util.init.getResourceName(name));
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(WWWMain.TABS);
        this.setStepSound(soundTypeGrass);

        Tags.SEED_TAG.put(new ItemStack(this));

        this.leaf = leaf;
        GameRegistry.registerBlock(this, name);
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return block.getMaterial() == Material.ground;
    }

    public void updateTick(World world, int par2, int par3, int par4, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, par2, par3, par4, rand);

            if (world.getBlockLightValue(par2, par3 + 1, par4) >= 9 && rand.nextInt(7) == 0) {
                this.func_149853_b(world, rand, par2, par3, par4);
            }
        }
    }

    public boolean fertilize(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);

        if (!world.isRemote) {
            if ((l & 8) == 0) {
                return world.setBlockMetadataWithNotify(x, y, z, l | 8, 3);
            } else {
                this.growTree(world, x, y, z, world.rand);
                return true;
            }
        }
        return true;
    }

    public void growTree(World world, int x, int y, int z, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z))
            return;

        int l = world.getBlockMetadata(x, y, z);
        TreeFactory object = new TreeFactory(true, this, this.leaf);

        if (!object.generate(world, rand, x, y, z)) {
            world.setBlock(x, y, z, this, l, 4);
        }
    }

    public boolean growable(World world, int x, int y, int z, int meta) {
        return world.getBlock(x, y, z) == this && (world.getBlockMetadata(x, y, z) & 7) == meta;
    }

    public boolean func_149851_a(World world, int x, int y, int z, boolean flag) {
        return true;
    }

    public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
        return true;
    }

    public void func_149853_b(World world, Random rand, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        if ((l & 8) == 0) world.setBlockMetadataWithNotify(x, y, z, l | 8, 3);
        else this.growTree(world, x, y, z, rand);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemFromBlock(this);
    }
}
