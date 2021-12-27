package com.lynch.dp;

import com.lynch.tools.Utils;

/**
 * 题意: 给定的数组中有多个不同的数, 每个数字代表一个面值, 每种面值都有无数个, 给定一个数 X , 求找零的方式有多少种
 */
public class CoinValue2 {
    public static void main(String[] args) {

        int[] arr1 = new int[]{3, 5, 1, 2, 4};
        int count = process(arr1, 0, 10);
        System.out.println("result: " + count);


        int times = 20;
        int minValue = 1, maxValue = 25;
        boolean success = true;
        for (int index = 0; index < times; index++) {
            int[] arr = Utils.generatePositiveRandomArrNoZero(maxValue, maxValue);
            int value = arr.length * Utils.getPositiveRandomNoZero(minValue, maxValue);

            Utils.printArr("times: " + (index + 1) + ",", arr);
            if (value == 0) {
                System.out.println("");
            }
            int x = process(arr, 0, value);
            int y = dp(arr, value);
            if (x != y) {
                success = false;
            }

            String format = String.format("times: %s, arr: %s, value: %s, way1: %s, way2: %s, result: %s",
                    index + 1, arr.length, value, x, y, (x == y ? "OK" : "Failed"));
            System.out.println(format);


        }
        System.out.println("All result: " + (success ? "OK" : "Failed"));
    }

    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }

        int ways = 0;
        int value = arr[index];
        for (int piece = 0; piece * value <= rest; piece++) {
            ways += process(arr, index + 1, rest - piece * value);
        }
        return ways;
    }

    private static int dp(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (aim == 0) {
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {

                int ways = 0;
                int value = arr[index];
                for (int piece = 0; piece * value <= rest; piece++) {
                    ways += dp[index + 1][rest - piece * value];
                }
                dp[index][rest] = ways;
            }
        }

        return dp[0][aim];
    }

    private static int dpOptimize(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (aim == 0) {
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 由于每一层的元素都依赖于其下一层正下方的元素 及 与正下方元素相差 arr[index] 距离的元素之和
                // 所以可以将某个元素的值简化为 其正下方的值 + 其前面距离为 arr[index] 元素之和
                int before = rest - arr[index];
                dp[index][rest] = dp[index + 1][rest];
                if (before >= 0) {
                    dp[index][rest] += dp[index][before];
                }
            }
        }

        return dp[0][aim];
    }
}
