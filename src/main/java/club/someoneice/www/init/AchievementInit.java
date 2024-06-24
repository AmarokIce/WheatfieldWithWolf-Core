package club.someoneice.www.init;

import club.someoneice.www.WWWMain;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import javax.annotation.Nullable;
import java.util.List;

public class AchievementInit {
    private static final List<Achievement> achievements = Lists.newArrayList();

    public static final Achievement Welcome;
    public static final Achievement TimeToCook;

    public static AchievementPage page;

    public static void init() {}

    static {
        Welcome = register("welcome", 0, 0, new ItemStack(Items.wheat), null, true);
        TimeToCook = register("time_to_cook", -1, -1, new ItemStack(BlockList.pot));

        page = new AchievementPage(WWWMain.MODNAME, achievements.toArray(new Achievement[0]));
        AchievementPage.registerAchievementPage(page);
    }

    private static Achievement register(String name, int x, int y, ItemStack item, @Nullable Achievement dependence, boolean hard) {
        Achievement achievement = new Achievement("achievement.wheatfieldwithwolf." + name, name, x, y, item, dependence);
        if (hard) achievement.setSpecial();
        achievements.add(achievement);
        return achievement.registerStat();
    }

    private static Achievement register(String name, int x, int y, ItemStack item) {
        return register(name, x, y, item, null, false);
    }
}
