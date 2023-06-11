package com.lynch.tools;

import java.util.function.BiFunction;

/**
 * 算法结果比较器
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/14 17:13
 */
public class AlgoResultComparator {
    static int times = 20;
    static int minValue = 1;
    static int maxValue = 25;

    public AlgoResultComparator() {
    }

    public static void build(int min, int max, int times){
        maxValue = max;
        minValue = min;
        AlgoResultComparator.times = times;
    }

    public static void execute(BiFunction<int[], Integer, Integer> alg1,  BiFunction<int[], Integer, Integer> alg2) {
        boolean success = true;
        for (int index = 0; index < AlgoResultComparator.times; index++) {
            int[] arr = Utils.generatePositiveRandomArrNoZero(maxValue, maxValue);
            int value = arr.length * Utils.getPositiveRandomNoZero(minValue, maxValue);

            Utils.printArr("times: " + (index + 1) + ",", arr);
            if (value == 0) {
                System.out.println("");
            }
            int x = 0;
            int y = 0;

            try {
                x = alg1.apply(arr, value);
                y = alg2.apply(arr, value);
                success = x == y;
            } catch (Exception ex) {
                System.out.println("execute take error: " + ex.getMessage());
                success = false;
            }
            String format = String.format("times: %s, arr: %s, value: %s, way1: %s, way2: %s, result: %s",
                    index + 1, arr.length, value, x, y, (x == y ? "OK" : "Failed"));
            System.out.println(format);
        }
        System.out.println("All result: " + (success ? "OK" : "Failed"));
    }
}
