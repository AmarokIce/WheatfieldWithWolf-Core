package club.someoneice.www.event;

import club.someoneice.www.core.init.W3Achievement;
import club.someoneice.www.core.init.W3Blocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.Item;

public class PlayerAchievementEvent {
  @SubscribeEvent
  public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
    event.player.triggerAchievement(W3Achievement.Welcome);
  }

  @SubscribeEvent
  public void onPlayerCrafting(PlayerEvent.ItemCraftedEvent event) {
    if (event.crafting.getItem() != Item.getItemFromBlock(W3Blocks.pot)) return;
    event.player.triggerAchievement(W3Achievement.TimeToCook);
  }
}
