package club.someoneice.www.common.item.factory;

import club.someoneice.www.WWWMain;
import club.someoneice.www.util.W3Util;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemFactory extends Item {
    public ItemFactory(String name) {
        this.setUnlocalizedName(name);
        this.setTextureName(W3Util.init.getResourceName(name));
        this.setCreativeTab(WWWMain.TABS);

        GameRegistry.registerItem(this, name, WWWMain.MODID);
    }
}
