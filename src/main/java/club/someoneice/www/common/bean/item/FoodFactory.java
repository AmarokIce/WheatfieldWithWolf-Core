package club.someoneice.www.common.bean.item;

import club.someoneice.www.WWWMain;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FoodFactory extends ItemFood {
    protected ItemStack returnItem;
    private final boolean isDrink, fast;

    public FoodFactory(String name, int hunger, float saturation, boolean wolf, boolean fast, boolean alwaysEat, boolean isDrink, ItemStack returnItem) {
        super(hunger, saturation, wolf);
        this.setUnlocalizedName(name);
        this.setTextureName(W3Util.init.getTexturesName(name));
        this.setCreativeTab(WWWMain.TABS);

        if (alwaysEat) this.setAlwaysEdible();

        this.fast = fast;
        this.returnItem = returnItem;
        this.isDrink = isDrink;

        GameRegistry.registerItem(this, name, WWWMain.MODID);
    }

    public FoodFactory(String name, int hunger, float saturation, boolean wolf, boolean fast) {
        this(name, hunger, saturation, wolf, fast, false, false, null);
    }

    public FoodFactory(String name, int hunger, float saturation) {
        this(name, hunger, saturation, false, false, false, false, null);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return this.fast ? 16 : 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return this.isDrink ? EnumAction.drink : EnumAction.eat;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        super.onEaten(item, world, player);
        if (this.returnItem != null) player.inventory.addItemStackToInventory(returnItem);
        return item;
    }
}
