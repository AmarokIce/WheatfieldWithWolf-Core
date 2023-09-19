package club.someoneice.www.util;

import club.someoneice.www.WWWMain;

public class Util {
    public static final Util init = new Util();

    public String getTexturesName(String name) {
        return WWWMain.MODID + ":" + name;
    }
}
