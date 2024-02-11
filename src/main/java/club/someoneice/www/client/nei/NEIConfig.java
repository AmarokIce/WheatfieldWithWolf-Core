package club.someoneice.www.client.nei;


import club.someoneice.www.WWWMain;
import club.someoneice.www.init.BlockList;
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
        setinfo(NEIRecipePot.class, new ItemStack(BlockList.pot));
        setinfo(NEIRecipeCutting.class, new ItemStack(BlockList.cutting_board));
        setinfo(NEIRecipeGrinder.class, new ItemStack(BlockList.grinder));
    }

    @Override
    public String getName() {
        return WWWMain.MODID + "_NeiPlugin";
    }

    @Override
    public String getVersion() {
        return WWWMain.VERSION;
    }

    public void registerDualHandler(TemplateRecipeHandler handler) {
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    public static void setinfo(Class<? extends TemplateRecipeHandler> clazz, ItemStack item, ItemStack ... otheritem) {
        String nameId = clazz.getName();
        GuiRecipeTab.handlerMap.put(nameId, (new HandlerInfo.Builder(nameId, WWWMain.MODNAME, WWWMain.MODID)).setDisplayStack(item).build());
        API.addRecipeCatalyst(item, nameId);
        for (int s = 0; s < otheritem.length; s++)
            API.addRecipeCatalyst(otheritem[s], nameId, s + 1);
    }
}
