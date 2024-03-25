package club.someoneice.www.common.bean.item;

import club.someoneice.www.common.bean.block.CropFactory;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSeedFactory extends ItemBlock {
    @SideOnly(Side.CLIENT)
    private IIcon icon;
    private final String name;

    public ItemSeedFactory(Block block) {
        super(block);
        // Never registry a ItemBlock.

        this.name = ((CropFactory) block).name;
        // this.setTextureName(Util.init.getTexturesName(this.name));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icon = register.registerIcon(W3Util.init.getResourceName(this.name));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        player.getFoodStats().addStats(2, 0.1F);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        --item.stackSize;
        return item;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (player.canEat(false))
            player.setItemInUse(item, this.getMaxItemUseDuration(item));

        return item;
    }
}
