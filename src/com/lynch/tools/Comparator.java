package com.lynch.tools;

import com.lynch.tools.Utils;

import java.util.function.Consumer;

/**
 * 对数器: 用于对比两种算法的执行结果是否一致
 */
public class Comparator {
    /**
     * 测试次数
     */
    private int testTime = 10000;
    private int[] arr1;
    private int[] arr2;

    public Comparator(int testTime, int maxSize, int maxValue) {

        this.testTime = testTime;

        arr1 = Utils.generateRandomArr(maxSize, maxValue);
        arr2 = Utils.copyArr(arr1);
    }

    /**
     * 对比两个算法的结果是否一致
     * @param sort1
     * @param sort2
     * @return
     */
    public boolean compare(Consumer<int[]> sort1, Consumer<int[]> sort2) {
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            sort1.accept(arr1);
            sort2.accept(arr1);
            if (!Utils.isEqual(arr1, arr2)) {
                success = false;
                break;
            }
        }
        System.out.println("compare result: " + (success ? "success" : "failed"));
        return success;
    }
}
