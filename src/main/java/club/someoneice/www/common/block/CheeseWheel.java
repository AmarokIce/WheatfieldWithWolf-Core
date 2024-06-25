package club.someoneice.www.common.block;

import club.someoneice.www.WWWMain;
import club.someoneice.www.common.item.ItemKnife;
import club.someoneice.www.init.ItemList;
import club.someoneice.www.init.Tags;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class CheeseWheel extends BlockCake {
    @SideOnly(Side.CLIENT)
    private IIcon inner;
    @SideOnly(Side.CLIENT)
    private IIcon top;

    public CheeseWheel() {
        String name = "cheese_wheel";
        this.setBlockName(name);
        this.setBlockTextureName(name);
        this.setTickRandomly(false);

        this.setStepSound(Block.soundTypeCloth);
        this.setCreativeTab(WWWMain.TABS);

        GameRegistry.registerBlock(this, name);

        Tags.MILK.put(new ItemStack(this));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon  = register.registerIcon(W3Util.init.getResourceName(this.getTextureName()+ "_side"));
        this.inner      = register.registerIcon(W3Util.init.getResourceName(this.getTextureName()+ "_inner"));
        this.top     = register.registerIcon(W3Util.init.getResourceName(this.getTextureName()+ "_top"));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.top : (p_149691_1_ == 0 ? this.top : (p_149691_2_ > 0 && p_149691_1_ == 4 ? this.inner : this.blockIcon));
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack item = player.getHeldItem();
        if (item == null || !(item.getItem() instanceof ItemKnife)) return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        item.damageItem(1, player);
        W3Util.init.giveOrThrowOut(player, new ItemStack(ItemList.cheese));
        damageBlock(world, new ChunkPosition(x, y, z));
        return true;
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        ItemStack item = player.getHeldItem();
        if (item == null || !(item.getItem() instanceof ItemKnife)) {
            super.onBlockClicked(world, x, y, z, player);
            return;
        }

        item.damageItem(1, player);
        W3Util.init.giveOrThrowOut(player, new ItemStack(ItemList.cheese));
        damageBlock(world, new ChunkPosition(x, y, z));
    }

    private void damageBlock(World world, ChunkPosition pos) {
        int l = world.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ) + 1;
        if ((l >= 6)) world.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
        else world.setBlockMetadataWithNotify(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, l, 2);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public String getItemIconName() {
        return W3Util.init.getResourceName("cheese_wheel");
    }
}
