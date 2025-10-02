package club.someoneice.www.client.nei;


import club.someoneice.www.core.WWWMain;
import club.someoneice.www.core.init.W3Blocks;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiRecipeTab;
import codechicken.nei.recipe.HandlerInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
public class NEIConfig implements IConfigureNEI {
  @Override
  public void loadConfig() {
    registerDualHandler(new NEIRecipePot());
    registerDualHandler(new NEIRecipeCutting());
    registerDualHandler(new NEIRecipeGrinder());

    gthnConfig();
  }

  public void gthnConfig() {
    setinfo(NEIRecipePot.class, new ItemStack(W3Blocks.pot));
    setinfo(NEIRecipeCutting.class, new ItemStack(W3Blocks.cutting_board));
    setinfo(NEIRecipeGrinder.class, new ItemStack(W3Blocks.grinder));
  }

  @Override
  public String getName() {
    return WWWMain.ID + "_NeiPlugin";
  }

  @Override
  public String getVersion() {
    return WWWMain.VERSION;
  }

  public void registerDualHandler(TemplateRecipeHandler handler) {
    API.registerRecipeHandler(handler);
    API.registerUsageHandler(handler);
  }

  public static void setinfo(Class<? extends TemplateRecipeHandler> clazz, ItemStack item, ItemStack... otheritem) {
    String nameId = clazz.getName();
    GuiRecipeTab.handlerMap.put(nameId, (new HandlerInfo.Builder(nameId, WWWMain.NAME, WWWMain.ID)).setDisplayStack(item).build());
    API.addRecipeCatalyst(item, nameId);
    for (int s = 0; s < otheritem.length; s++)
      API.addRecipeCatalyst(otheritem[s], nameId, s + 1);
  }
}
