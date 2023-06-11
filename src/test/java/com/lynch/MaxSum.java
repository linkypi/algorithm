package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/30 9:04
 */
public class MaxSum {
    public static void main(String[] args) {
        int[] arr = {5, -3, 1, -2, 4, 3};
        int maxSum = getMaxSum(arr);
        System.out.println("max sum: " + maxSum);
    }

    private static int getMaxSum(int[] arr) {
        int max = Integer.MIN_VALUE;
        int current = 0;
        for (int item : arr) {
            current += item;

            if (current > max) {
                max = current;
            }
            if (current < 0) {
                current = 0;
            }
        }
        return max;
    }
}
