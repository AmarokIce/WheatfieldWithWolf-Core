package club.someoneice.www.event;

import club.someoneice.www.init.AchievementInit;
import club.someoneice.www.init.BlockList;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.Item;

public class PlayerAchievementEvent {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.triggerAchievement(AchievementInit.Welcome);
    }

    @SubscribeEvent
    public void onPlayerCrafting(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() != Item.getItemFromBlock(BlockList.pot)) return;
        event.player.triggerAchievement(AchievementInit.TimeToCook);
    }
}
