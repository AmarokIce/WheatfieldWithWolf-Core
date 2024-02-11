package club.someoneice.www.common.bean.item;

import club.someoneice.www.WWWMain;
import club.someoneice.www.util.Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemFactory extends Item {
    public ItemFactory(String name) {
        this.setUnlocalizedName(name);
        this.setTextureName(Util.init.getTexturesName(name));
        this.setCreativeTab(WWWMain.TABS);

        GameRegistry.registerItem(this, name, WWWMain.MODID);
    }
}
