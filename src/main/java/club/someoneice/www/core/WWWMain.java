package club.someoneice.www.core;

import club.someoneice.www.common.entity.EntityRottenTomato;
import club.someoneice.www.core.init.*;
import club.someoneice.www.network.SimpleNetWorkHandler;
import club.someoneice.www.network.proxy.CommonProxy;
import club.someoneice.www.event.EventBlockEvent;
import club.someoneice.www.util.SeedTagUtil;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

@Mod(modid = WWWMain.ID, useMetadata = true)
public class WWWMain {
  public static final String ID = "wheatfieldwithwolf";
  public static final String NAME = "Wheatfield With Wolf";
  public static final String VERSION = "@VERSION@";

  public static Logger LOG;

  @SidedProxy(modId = WWWMain.ID, clientSide = "club.someoneice.www.network.proxy.ClientProxy", serverSide = "club.someoneice.www.network.proxy.CommonProxy")
  public static CommonProxy proxy;

  public static final CreativeTabs TABS = new CreativeTabs("wheatfieldwithwolf") {
    @Override
    public Item getTabIconItem() {
      return W3Items.pineapple;
    }
  };

  @Mod.Instance("wheatfieldwitlwolf")
  public static WWWMain INSTANCE;

  @Mod.EventHandler
  public void initPre(FMLPreInitializationEvent event) {
    INSTANCE = this;
    LOG = event.getModLog();

    W3Items.init();
    W3Blocks.init();
    entityInit();
    W3Achievement.init();

    SeedTagUtil.inputTags();

    W3Recipes.INSTANCE.init();
  }

  @Mod.EventHandler
  @SuppressWarnings("all")
  public void initCommon(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new W3Gui());

    W3Tags.registerToOD();

    proxy.initRender();
    SimpleNetWorkHandler.init();

    registryEvent(new EventBlockEvent());
  }

  private void entityInit() {
    EntityRegistry.registerModEntity(EntityRottenTomato.class, "rotten_tomato_throw_entity", 4, WWWMain.INSTANCE, 64, 10, true);
  }

  private void registryEvent(Object eventObj) {
    MinecraftForge.EVENT_BUS.register(eventObj);
    FMLCommonHandler.instance().bus().register(eventObj);
  }
}
