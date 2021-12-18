package com.lynch.extern;

import java.awt.geom.GeneralPath;

public class MaxSumInArr {
    public static void main(String[] args) {

        int[] arr = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println(getMaxSum(arr));
    }

    /**
     * 输入一个整型数组，数组里有正数也有负数。数组中一个或连续的多个整数
     * 组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为 O（n）。
     *
     * @param arr
     * @return
     */
    public static int getMaxSum(int[] arr) {

        int current = 0, sum = 0;
        for (int index = 0; index < arr.length; index++) {
            if (current <= 0) {
                current = arr[index];
            } else {
                current += arr[index];
            }
            if (current > sum)
                sum = current;
        }
        return sum;
    }
}
