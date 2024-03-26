package club.someoneice.www.init;

import club.someoneice.www.common.block.*;
import club.someoneice.www.common.block.factory.*;
import net.minecraft.block.Block;

@SuppressWarnings("unused")
public class BlockList {
    /* Crop */
    public static final Block red_beans = new CropFactory("red_beans");
    public static final Block green_beans = new CropFactory("green_beans");
    public static final Block vanilla = new CropFactory("vanilla");
    public static final Block pea = new PeaCrop();
    public static final Block coffee_beans = new CropFactory("coffee_beans");
    public static final Block chickpeas = new CropFactory("chickpeas");
    public static final Block soybean = new CropFactory("soybean");
    public static final Block sesame = new CropFactory("sesame");
    public static final Block kale = new CropFactory("kale");
    public static final Block sweet_potato = new CropFactory("sweetpotato");
    public static final Block ginger = new CropFactory("ginger");
    public static final Block garlic = new CropFactory("garlic");
    public static final Block peanut = new CropFactory("peanut");
    public static final Block onion = new CropFactory("onion");

    public static final Block pineapple_crop = new CropFactory("pineapple_seed", ItemList.pineapple);
    public static final Block strawberry_seed = new CropFactory("strawberry_seed", ItemList.strawberry);
    public static final Block tomato_seed = new TomatoCrop();
    public static final Block cucumber_seed = new CropFactory("cucumber_seed", ItemList.cucumber);
    public static final Block eggplant_seed = new CropFactory("eggplant_seed", ItemList.eggplant);
    public static final Block leek_seed = new CropFactory("leek_seed", ItemList.leek);
    public static final Block cabbage_seed = new CropFactory("cabbage_seed", ItemList.cabbage);
    public static final Block asparagus_seed = new CropFactory("asparagus_seed", ItemList.asparagus);
    public static final Block lettuce_seed = new CropFactory("lettuce_seed", ItemList.lettuce);
    public static final Block okra_seed = new CropFactory("okra_seed", ItemList.okra);
    public static final Block spinach_seed = new CropFactory("spinach_seed", ItemList.spinach);
    public static final Block tea_leaves_seed = new CropFactory("tea_leaves_seed", ItemList.tea_leaves);
    public static final Block beetroot_seed = new CropFactory("beetroot_seed", ItemList.beetroot);
    public static final Block broccoli_seed = new CropFactory("broccoli_seed", ItemList.broccoli);

    public static final Block chili_seed = new CropHellFactory("chili_seed", ItemList.chili);
    public static final Block rice = new CropWaterFactory("rice");


    /* Tree Leaf */
    public static final Block lemon_leaf = new LeafFactory("lemon_leaf", ItemList.lemon);
    public static final Block olive_leaf = new LeafFactory("olive_leaf", ItemList.olive);
    public static final Block orange_leaf = new LeafFactory("orange_leaf", ItemList.orange);
    public static final Block pear_leaf = new LeafFactory("pear_leaf", ItemList.pear);
    public static final Block peach_leaf = new LeafFactory("peach_leaf", ItemList.peach);
    public static final Block cherry_leaf = new LeafFactory("cherry_leaf", ItemList.cherry);

    public static final Block lemon_sapling = new TreeSaplingFactory("lemon_sapling", lemon_leaf);
    public static final Block olive_sapling = new TreeSaplingFactory("olive_sapling", olive_leaf);
    public static final Block orange_sapling = new TreeSaplingFactory("orange_sapling", orange_leaf);
    public static final Block pear_sapling = new TreeSaplingFactory("pear_sapling", pear_leaf);
    public static final Block peach_sapling = new TreeSaplingFactory("peach_sapling", peach_leaf);
    public static final Block cherry_sapling = new TreeSaplingFactory("cherry_sapling", cherry_leaf);

    /* Tile */
    public static final Block grinder = new Grinder();
    public static final Block cutting_board = new CuttingBoard();
    public static final Block pot = new CookingPot();
}
