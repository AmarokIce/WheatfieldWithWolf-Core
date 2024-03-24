package club.someoneice.www.client.gui.pot;

import club.someoneice.www.client.gui.slot.SlotOutput;
import club.someoneice.www.init.AchievementInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class PotSlotOutput extends SlotOutput {
    public PotSlotOutput(IInventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack item) {
        super.onPickupFromSlot(player, item);
        player.triggerAchievement(AchievementInit.DinnerOn);
    }
}
