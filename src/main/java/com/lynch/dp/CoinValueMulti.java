package com.lynch.dp;

import java.util.PriorityQueue;

/**
 * 题意: 给定两个数组n1, n2, 元素中的每个数字代表一个面值,
 * 其中 n1 每种面值可以使用任意张, n2 每种面值只能使用一张
 * 求有多少种方法可以拼凑出总面值 m
 */
public class CoinValueMulti {
    public static void main(String[] args) {

        int[] arr1 = new int[]{3, 5, 1, 2, 4, 10};
        int[] arr2 = new int[]{3, 5, 10, 20};
//        int x1 = getByOne(0, 40, arr2);
//        int x2 = findByOne(40, arr2);
//
//        int x3 = getByAny(0,40, arr1);
//        int x4 = findByAny(40, arr1);

        int count = process(65, arr1, arr2);
        System.out.println("money 65 result: " + count);

        count = process(99, arr1, arr2);
        System.out.println("money 99 result: " + count);
    }

    private static int getByOne(int index, int value, int[] arr) {
        if (value == 0) {
            return 1;
        }
        if (index == arr.length || value < 0) {
            return 0;
        }
        int p1 = getByOne(index + 1, value, arr);
        int p2 = getByOne(index + 1, value - arr[index], arr);
        return p1 + p2;
    }

    private static int getByAny(int index, int value, int[] arr) {
        if (value == 0) {
            return 1;
        }
        if (index == arr.length || value < 0) {
            return 0;
        }
        int ways = 0;
        for (int k = 0; k * arr[index] <= value; k++) {
            ways += getByAny(index + 1, value - arr[index] * k, arr);
        }
        return ways;
    }

    private static int findByOne(int value, int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n + 1][value + 1];
        for (int row = n; row >= 0; row--) {
            dp[row][0] = 1;
        }

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= value; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                int result = rest - arr[index];
                if (result >= 0) {
                    dp[index][rest] += dp[index + 1][result];
                }
            }
        }

        return dp[0][value];
    }

    private static int process(int rest, int[] arr1, int[] arr2) {
        int count = 0;
        for (int k = 0; k <= rest; k++) {

            // 从可使用任意张的 arr1 中凑 k 元的方法数
            int x = findByAny(k,arr1);

            // 从仅可使用一张的 arr2 中凑 (rest-k) 元的方法数
            int y = findByOne(rest-k, arr2);
            count += x * y;
        }
        return count;
    }

    /**
     * 每种面值可使用任意张
     * @param value
     * @param arr
     * @return
     */
    private static int findByAny(int value, int[] arr) {

        if (value == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][value + 1];
        for (int row = n; row >= 0; row--) {
            dp[row][0] = 1;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= value; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                int pre = rest - arr[index];
                if (pre >= 0) {
                    dp[index][rest] += dp[index][pre];
                }
            }
        }

        return dp[0][value];
    }

}
