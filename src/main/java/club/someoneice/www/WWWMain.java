package club.someoneice.www;

import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.common.tile.TileCuttingBoard;
import club.someoneice.www.common.tile.TileGrinder;
import club.someoneice.www.common.tile.TilePot;
import club.someoneice.www.event.EventBlockEvent;
import club.someoneice.www.init.*;
import club.someoneice.www.network.SimpleNetWorkHandler;
import club.someoneice.www.network.proxy.CommonProxy;
import club.someoneice.www.util.SeedTagUtil;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

@Mod(modid = WWWMain.MODID, name = WWWMain.MODNAME, version = WWWMain.VERSION, dependencies = "required-after:pineapple_tags", useMetadata = true)
public class WWWMain {
    public static final String MODID = "wheatfieldwithwolf";
    public static final String MODNAME = "Wheatfield With Wolf";
    public static final String VERSION = "@VERSION@";

    public static  Logger LOG;

    @SidedProxy(modId = WWWMain.MODID, clientSide = "club.someoneice.www.network.proxy.ClientProxy", serverSide = "club.someoneice.www.network.proxy.CommonProxy")
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
        LOG = event.getModLog();

        new ItemList();
        new BlockList();
    }

    @Mod.EventHandler @SuppressWarnings("all")
    public void initCommon(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        new AchievementInit();
        new Tags();

        Recipes.INSTANCE.init();

        tileInit();
        EntityInit();

        SeedTagUtil.inputTags();

        proxy.initRender();
        SimpleNetWorkHandler.init();

        registryEvent(new EventBlockEvent());
    }

    private void tileInit() {
        GameRegistry.registerTileEntity(TileGrinder.class, "tile_grinder");
        GameRegistry.registerTileEntity(TileCuttingBoard.class, "tile_cutting");
        GameRegistry.registerTileEntity(TilePot.class, "tile_pot");
    }

    private void EntityInit() {
        EntityRegistry.registerModEntity(EntityRottenTomato.class, "rotten_tomato_throw_entity", 4, WWWMain.INSTANCE, 64, 10, true);
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }
}
