package club.someoneice.www.common.block.factory;

import club.someoneice.togocup.tags.ItemStackTag;
import club.someoneice.www.common.item.factory.ItemSeedFactory;
import club.someoneice.www.core.WWWMain;
import club.someoneice.www.core.init.W3Tags;
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
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class CropFactory extends BlockCrops {
  public final Item crop, seed;
  protected final boolean oneOnly;
  public final String name;

  public CropFactory(String name, Item crop, Item seed, boolean oneOnly) {
    this.name = name;
    this.crop = crop;
    this.seed = seed;
    this.oneOnly = oneOnly;

    this.setBlockName(name);
    this.setBlockTextureName(W3Util.init.getResourceName(name));
    this.setCreativeTab(WWWMain.TABS);

    SeedTagUtil.CROPS.add(this);
    GameRegistry.registerBlock(this, ItemSeedFactory.class, name);

    if (this.seed != null) {
      GameRegistry.addShapelessRecipe(new ItemStack(seed, 2), this);
    }

    OreDictionary.registerOre(this.name, this);
  }

  public CropFactory(String name, Item crop, boolean oneOnly) {
    this(name, crop, null, oneOnly);
  }

  public CropFactory(String name, Item crop) {
    this(name, crop, null, false);
  }

  public CropFactory(String name) {
    this(name, null, null, false);
  }

  public CropFactory addTag(ItemStackTag... tag) {
    Arrays.stream(tag).forEach(it -> it.put(new ItemStack(this)));
    return this;
  }

  @Override
  protected boolean canPlaceBlockOn(Block block) {
    return W3Tags.DIRT_TAG.has(block);
  }

  private int havenLevel = 0;

  public CropFactory setHavenLevel(int i) {
    this.havenLevel = i;
    return this;
  }

  IIcon[] icons;

  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister register) {
    this.icons = new IIcon[4];

    for (int i = 0; i < 4; ++i) {
      this.icons[i] = register.registerIcon(this.textureName.replaceAll("_seed", "") + "_crop_" + i);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
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
  public Item func_149866_i() {
    return this.seed != null ? seed : Item.getItemFromBlock(this);
  }

  @Override
  public Item func_149865_P() {
    return this.crop != null ? this.crop : Item.getItemFromBlock(this);
  }

  @Override
  public int quantityDropped(Random random) {
    return super.quantityDropped(random);
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float hitX, float hitY, float hitZ) {
    super.onBlockActivated(world, x, y, z, player, face, hitX, hitY, hitX);

    if (world.isRemote) return false;
    int meta = world.getBlockMetadata(x, y, z);
    if (meta < 7) return false;
    List<ItemStack> rat = Lists.newArrayList();

    rat.add(new ItemStack(this.func_149865_P(), this.oneOnly ? 1 : world.rand.nextInt(5) + 1));
    if (world.rand.nextInt(10) > 6) rat.add(new ItemStack(this.func_149866_i()));
    for (ItemStack item : rat) world.spawnEntityInWorld(new EntityItem(world, x, y + 0.5D, z, item));

    world.setBlockMetadataWithNotify(x, y, z, havenLevel, 2);
    return true;

  }

  @SideOnly(Side.CLIENT)
  public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
    return this.seed;
  }
}
