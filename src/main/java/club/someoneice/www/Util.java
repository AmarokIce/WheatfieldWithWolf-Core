package club.someoneice.www;

public class Util {
    public static final Util init = new Util();

    public String getTexturesName(String name) {
        return WWWMain.MODID + ":" + name;
    }
}
