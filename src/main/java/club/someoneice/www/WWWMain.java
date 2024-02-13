package club.someoneice.www;

import club.someoneice.www.event.EventBlockEvent;
import club.someoneice.www.init.*;
import club.someoneice.www.network.SimpleNetWorkHandler;
import club.someoneice.www.proxy.CommonProxy;
import club.someoneice.www.util.SeedTagUtil;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

// @Mod(modid = WWWMain.MODID, name = WWWMain.MODNAME, version = WWWMain.VERSION, dependencies = "required-after:pieapple_tags")
@Mod(modid = WWWMain.MODID, name = WWWMain.MODNAME, version = WWWMain.VERSION, dependencies = "")
public class WWWMain {
    public static final String MODID = "wheatfieldwithwolf";
    public static final String MODNAME = "WheatfieldWithWolf";
    public static final String VERSION = "day-1";
    @SidedProxy(modId = WWWMain.MODID, clientSide = "club.someoneice.www.proxy.ClientProxy", serverSide = "club.someoneice.www.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs TABS = new CreativeTabs("wheatfieldwithwolf") {
        @Override
        public Item getTabIconItem() {
            return ItemList.pineapple;
        }
    };

    @Mod.Instance("wheatfieldwitlwolf")
    public static WWWMain INSTANCE;

    @Mod.EventHandler @SuppressWarnings("all")
    public void initCommon(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        new ItemList();
        new BlockList();
        new Others();

        SeedTagUtil.inputTags();

        proxy.initRender();
        SimpleNetWorkHandler.init();

        registryEvent(new EventBlockEvent());
    }

    @Mod.EventHandler
    public void initPre(FMLPreInitializationEvent event) {
        INSTANCE = this;
        new Tags();
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }
}
