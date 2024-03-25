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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = WWWMain.MODID, name = WWWMain.MODNAME, version = WWWMain.VERSION, dependencies = "required-after:pineapple_tags", useMetadata = true)
public class WWWMain {
    public static final String MODID = "wheatfieldwithwolf";
    public static final String MODNAME = "Wheatfield With Wolf";
    public static final String VERSION = "Day-2";

    public static final Logger LOG = LogManager.getLogger(MODID);

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

    @Mod.EventHandler
    public void initPre(FMLPreInitializationEvent event) {
        INSTANCE = this;
        new ItemList();
        new BlockList();
    }

    @Mod.EventHandler @SuppressWarnings("all")
    public void initCommon(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        new AchievementInit();
        new Others();
        new Tags();

        SeedTagUtil.inputTags();

        proxy.initRender();
        SimpleNetWorkHandler.init();

        registryEvent(new EventBlockEvent());
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }
}
