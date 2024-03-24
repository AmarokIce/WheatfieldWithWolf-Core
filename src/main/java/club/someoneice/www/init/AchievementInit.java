package club.someoneice.www.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

import javax.annotation.Nullable;

public class AchievementInit {
    public static Achievement Welcome = register("welcome", -8, -8, new ItemStack(Items.wheat), null, true);
    public static Achievement TimeToCook = register("time_to_cook", -7, -8, new ItemStack(BlockList.pot));
    public static Achievement DinnerOn = register("dinner_on", -6, -8, new ItemStack(ItemList.cooked_bacon), TimeToCook, false);

    private static Achievement register(String name, int x, int y, ItemStack item, @Nullable Achievement dependence, boolean hard) {
        Achievement achievement = new Achievement("achievement.wheatfieldwithwolf." + name, name, x, y, item, dependence);
        if (hard) achievement.setSpecial();
        return achievement.registerStat();
    }

    private static Achievement register(String name, int x, int y, ItemStack item) {
        return register(name, x, y, item, null, false);
    }
}
