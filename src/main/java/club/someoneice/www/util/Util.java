package club.someoneice.www.util;

import club.someoneice.www.WWWMain;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Util {
    public static final Util init = new Util();

    public String getTexturesName(String name) {
        return WWWMain.MODID + ":" + name;
    }

    public <A> boolean arraySame(A[] aArray, A[] bArray){
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

    public boolean stackArraySame(ItemStack[] A, ItemStack[] B) {
        if (A.length != B.length) return false;
        List<Integer> intArray = Lists.newArrayList();
        for (ItemStack a : A) {
            if (a == null) continue;
            boolean has = false;
            for (int i = 0; i < A.length; i++) {
                if (!intArray.contains(i) && stackSameAs(a, B[i])) {
                    intArray.add(i);
                    has = true;
                    break;
                }
            }
            if (!has) return false;
        }

        return true;
    }

    public boolean stackSameAs(ItemStack A, ItemStack B) {
        return (A == null && B == null) || (A != null && B != null && A.getItem() == B.getItem() && A.getItemDamage() == B.getItemDamage());
    }
}
