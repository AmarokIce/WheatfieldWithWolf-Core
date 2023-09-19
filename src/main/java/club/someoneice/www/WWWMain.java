package club.someoneice.www;

import club.someoneice.www.init.BlockList;
import club.someoneice.www.init.GuiHandler;
import club.someoneice.www.init.ItemList;
import club.someoneice.www.init.Others;
import club.someoneice.www.network.SimpleNetWorkHandler;
import club.someoneice.www.proxy.CommonProxy;
import club.someoneice.www.util.Tags;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = WWWMain.MODID, version = WWWMain.VARSTON, dependencies = "required-after:pieapple_tags")
public class WWWMain {
    public static final String MODID = "wheatfieldwitlwolf";
    public static final String VARSTON = "day-0";
    @SidedProxy(modId = WWWMain.MODID, clientSide = "club.someoneice.www.proxy.ClientProxy", serverSide = "club.someoneice.www.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs TABS = new CreativeTabs("www_crop") {
        @Override
        public Item getTabIconItem() {
            return ItemList.pineapple;
        }
    };

    @Mod.Instance("wheatfieldwitlwolf")
    public static WWWMain INSTANCE;

    @Mod.EventHandler
    public void initCommon(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        new ItemList();
        new BlockList();
        new Others();

        proxy.initRender();
        SimpleNetWorkHandler.init();
    }

    @Mod.EventHandler
    public void initPre(FMLPreInitializationEvent event) {
        INSTANCE = this;
        new Tags();
    }
}
