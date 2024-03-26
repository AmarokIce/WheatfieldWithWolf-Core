package club.someoneice.www.common.block.factory;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.item.factory.ItemHellCrop;
import club.someoneice.www.init.Tags;
import club.someoneice.www.util.SeedTagUtil;
import club.someoneice.www.util.W3Util;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unused")
public class CropHellFactory extends BlockCrops {
    protected final Item crop, seed;
    protected final boolean oneOnly;
    public final String name;

    public CropHellFactory(String name, Item crop, Item seed, boolean oneOnly) {
        this.name = name;
        this.crop = crop;
        this.seed = seed;
        this.oneOnly = oneOnly;

        this.setBlockName(name);
        this.setBlockTextureName(W3Util.init.getResourceName(name));
        this.setCreativeTab(WWWMain.TABS);

        SeedTagUtil.HELL_CROPS.add(this);
        GameRegistry.registerBlock(this, ItemHellCrop.class, name);

        if (this.seed != null) {
            GameRegistry.addShapelessRecipe(new ItemStack(seed, 2), this);
        }
    }

    public CropHellFactory(String name, Item crop, boolean oneOnly) {
        this(name, crop, null, oneOnly);
    }

    public CropHellFactory(String name, Item crop) {
        this(name, crop, null, false);
    }

    public CropHellFactory(String name) {
        this(name, null, null, false);
    }

    IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[4];

        for(int i = 0; i < 4; ++i) {
            this.icons[i] = register.registerIcon(this.textureName.replaceAll("_seed", "") + "_crop_" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch(meta) {
            case 0:
            case 1:
            default:
                return this.icons[0];
            case 2:
            case 3:
                return this.icons[1];
            case 4:
            case 5:
            case 6:
                return this.icons[2];
            case 7:
            case 8:
                return this.icons[3];
        }
    }


    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return Tags.SOUL_SAND_TAG.has(block);
    }

    @Override
    public Item func_149866_i() {
        return this.seed != null? seed : Item.getItemFromBlock(this);
    }

    @Override
    public Item func_149865_P() {
        return this.crop != null? this.crop : Item.getItemFromBlock(this);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, face, hitX, hitY, hitX);

        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta == 7) {
                List<ItemStack> rat = Lists.newArrayList();

                rat.add(new ItemStack(this.func_149865_P(), this.oneOnly ? 1 : world.rand.nextInt(5) + 1));
                if (world.rand.nextInt(10) > 6) rat.add(new ItemStack(this.func_149866_i()));
                for (ItemStack item : rat) world.spawnEntityInWorld(new EntityItem(world, x, y + 0.5D, z, item));

                world.setBlockMetadataWithNotify(x, y, z, 0, 2);
                return true;
            }
        }

        return false;
    }
}
