package club.someoneice.www.util;

import club.someoneice.www.WWWMain;
import com.google.common.collect.Lists;

import java.util.List;

public class Util {
    public static final Util init = new Util();

    public String getTexturesName(String name) {
        return WWWMain.MODID + ":" + name;
    }

    public  <A> boolean arraySame(A[] aArray, A[] bArray){
        if (aArray.length != bArray.length) return false;
        List<Integer> intArray = Lists.newArrayList();

        for (A a : aArray) {
            boolean has = false;
            for (int i = 0; i < bArray.length; i++) {
                if (!intArray.contains(i) && a == bArray[i]) {
                    intArray.add(i);
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }

        return true;
    }
}
