package club.someoneice.www.common.item.factory;

import club.someoneice.www.common.block.factory.CropHellFactory;
import club.someoneice.www.init.Tags;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHellCrop extends ItemBlock {
    @SideOnly(Side.CLIENT)
    private IIcon icon;
    private final String name;

    public ItemHellCrop(Block block) {
        super(block);

        this.name = ((CropHellFactory) block).name;
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
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (side != 1 || !Tags.SOUL_SAND_TAG.has(world.getBlock(x, y, z))) return false;

        int meta = this.getMetadata(item.getItemDamage());
        this.field_150939_a.onBlockPlaced(world, x, y ,z, 1, hitX, hitY, hitZ, meta);

        if (placeBlockAt(item, player, world, x, y + 1, z, 1,hitX, hitY, hitZ, meta)) {
            world.playSoundEffect((float)x + 0.5F, (float)y+ 0.5F, (float)z + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
            --item.stackSize;
            return true;
        }
        return false;
    }
}
