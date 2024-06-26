package club.someoneice.www.init;

import club.someoneice.www.common.item.ItemKnife;
import club.someoneice.www.common.item.ItemLunchBag;
import club.someoneice.www.common.item.RottenTomato;
import club.someoneice.www.common.item.factory.FoodFactory;
import club.someoneice.www.common.item.factory.ItemFactory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemList {
    /* crop */
    public static final Item pineapple = new FoodFactory("pineapple", 3, 0.1F).addTag(Tags.FRUIT_TAG);
    public static final Item strawberry = new FoodFactory("strawberry", 1, 0.1F, false, true).addTag(Tags.FRUIT_TAG);
    public static final Item tomato = new FoodFactory("tomato", 3, 0.2F, true, false).addTag(Tags.FRUIT_TAG, Tags.TOMATO_TAG);
    public static final Item cherry_tomato = new FoodFactory("cherry_tomato", 2, 0.2F, true, true).addTag(Tags.FRUIT_TAG);
    public static final Item rotten_tomato = new RottenTomato();
    public static final Item cucumber = new FoodFactory("cucumber", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item eggplant = new FoodFactory("eggplant", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG, Tags.MEATABLE_PLANT_TAG, Tags.EGGPLANT_TAG);
    public static final Item leek = new FoodFactory("leek", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item cabbage = new FoodFactory("cabbage", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item asparagus = new FoodFactory("asparagus", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item celery = new FoodFactory("celery", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item lettuce = new FoodFactory("lettuce", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item okra = new FoodFactory("okra", 1, 0.3F, true, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item spinach = new FoodFactory("spinach", 3, 0.2F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item tea_leaves = new FoodFactory("tea_leaves", 0, 0.1F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item beetroot = new FoodFactory("beetroot", 2, 0.1F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item broccoli = new FoodFactory("broccoli", 2, 0.1F, false, false).addTag(Tags.VEGETABLE_TAG);
    public static final Item chili = new FoodFactory("chili", 1, 0.1F, false, true).addTag(Tags.VEGETABLE_TAG);

    /* Tree fruit */
    public static final Item lemon = new FoodFactory("lemon", 2, 0.1F).addTag(Tags.FRUIT_TAG);
    public static final Item olive = new FoodFactory("olive", 2, 0.1F).addTag(Tags.FRUIT_TAG, Tags.OIL_CROP_TAG);
    public static final Item orange = new FoodFactory("orange", 2, 0.1F).addTag(Tags.FRUIT_TAG);
    public static final Item pear = new FoodFactory("pear", 2, 0.1F).addTag(Tags.FRUIT_TAG);
    public static final Item peach = new FoodFactory("peach", 2, 0.1F).addTag(Tags.FRUIT_TAG);
    public static final Item cherry = new FoodFactory("cherry", 2, 0.1F, false, true).addTag(Tags.FRUIT_TAG);


    /* Food */
    public static final Item cinnamon = new FoodFactory("cinnamon", 1, 0.2F).addTag(Tags.MILKABLE_PLANT_TAG);
    public static final Item oil = new ItemFactory("oil").addTag(Tags.OIL_TAG);
    public static final Item butter = new ItemFactory("butter").addTag(Tags.MILK);
    public static final Item salt = new ItemFactory("salt");
    public static final Item cheese = new FoodFactory("cheese", 2, 0.5F).addTag(Tags.MILK);
    public static final Item cornmeal = new FoodFactory("cornmeal", 2, 0.4F);
    public static final Item tofu = new FoodFactory("tofu", 1, 0.0F).addTag(Tags.MEATABLE_PLANT_TAG);
    public static final Item plant_base_milk = new FoodFactory("plant_base_milk", 0, 0.2f, false, false, true, true, new ItemStack(Items.glass_bottle)).addTag(Tags.MILK);
    public static final Item artificial_meat = new FoodFactory("artificial_meat", 6, 0.8f, true, false).addTag(Tags.MEAT_TAG);

    /* Item */
    public static final Item grinder_knife = new ItemFactory("grinder_knife");
    public static final Item knife = new ItemKnife("knife");
    public static final Item lunch_bag = new ItemLunchBag();

    /* Cutting */
    public static final Item bacon = new FoodFactory("bacon", 2, 0.3F).addTag(Tags.MEAT_TAG, Tags.PORK_TAG, Tags.ALL_PORK_TAG);
    public static final Item chicken_breast = new FoodFactory("chicken_breast", 2, 0.3F).addTag(Tags.MEAT_TAG, Tags.CHICKEN_TAG, Tags.ALL_CHICKEN_TAG);
    public static final Item meat_stuffing = new FoodFactory("meat_stuffing", 2, 0.3F).addTag(Tags.MEAT_TAG, Tags.BEEF_TAG, Tags.ALL_BEEF_TAG);
    public static final Item cut_potato = new FoodFactory("cut_potato", 1, 0.1F).addTag(Tags.VEGETABLE_TAG, Tags.POTATO_TAG);
    public static final Item cut_tomato = new FoodFactory("cut_tomato", 1, 0.1F).addTag(Tags.FRUIT_TAG, Tags.TOMATO_TAG);
    public static final Item cut_onion = new FoodFactory("cut_onion", 1, 0.1F).addTag(Tags.VEGETABLE_TAG, Tags.ONION_TAG);
    public static final Item cut_eggplant = new FoodFactory("cut_eggplant", 1, 0.1F).addTag(Tags.VEGETABLE_TAG, Tags.MEATABLE_PLANT_TAG, Tags.EGGPLANT_TAG);
    public static final Item bread_slice = new FoodFactory("bread_slice", 1, 0.2F).addTag(Tags.BREAD_TAG);

    public static final Item cooked_bacon = new FoodFactory("cooked_bacon", 5, 0.3F).addTag(Tags.MEAT_TAG, Tags.ALL_PORK_TAG);
    public static final Item cooked_chicken_breast = new FoodFactory("cooked_chicken_breast", 4, 0.3F).addTag(Tags.MEAT_TAG, Tags.ALL_CHICKEN_TAG);
    public static final Item cooked_meat_stuffing = new FoodFactory("cooked_meat_stuffing", 8, 0.3F).addTag(Tags.MEAT_TAG, Tags.ALL_BEEF_TAG);
    public static final Item cooked_cut_eggplant = new FoodFactory("cooked_cut_eggplant", 4, 0.3F).addTag(Tags.VEGETABLE_TAG, Tags.MEATABLE_PLANT_TAG);
    public static final Item cooked_cut_onion = new FoodFactory("cooked_cut_onion", 1, 0.1F).addTag(Tags.VEGETABLE_TAG);
    public static final Item toast = new FoodFactory("toast", 4, 0.3F).addTag(Tags.BREAD_TAG);
}
