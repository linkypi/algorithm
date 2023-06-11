package com.lynch;

import java.util.Arrays;

/**
 * 给你 k 种面值的硬币，面值分别为 c1, c2 ... ck，每种硬币的数量无限，再给一个总金额 amount，
 * 问你最少需要几枚硬币凑出这个金额，如果不可能凑出，算法返回 -1。假设每一种面额的硬币有无限个。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/11 15:37
 */
public class CoinChange {
    public static void main(String[] args) {
        int[] arr = {1, 2, 5};
        int target = 5;
        int minWays = findMin(target, 0, arr);

        int[][] dp = new int[target + 1][arr.length];
        for (int i = 0; i < target + 1; i++) {
            Arrays.fill(dp[i], -1);
        }
        int minWithMem = findMinWithMem(target, 0, arr, dp);

        int ways = findWays(0, 5);

        System.out.println("min count: " + minWays);
    }

    public static int findWays(int index , int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 0 || index > 3) {
            return -1;
        }

        int[] coins = {1, 5, 10, 25};
        int ways = 0;

        for (int k = 0; coins[index] * k <= n; k++) {
            int rest = n - coins[index] * k;
            int res = findWays(index + 1, rest);
            if (res == 1) {
                ways += res;
            }
        }
        return ways;
    }

    static int findMin(int amount , int index, int [] arr) {

        if (amount == 0) {
            return 0;
        }

        if (amount < 0 || index > arr.length - 1) {
            return -1;
        }
        int count = Integer.MAX_VALUE;
        for (int i = 0; i * arr[index] <= amount; i++) {
            int rest = amount - arr[index] * i;
            int use = findMin(rest, index + 1, arr);
            if (use != -1 && use != Integer.MAX_VALUE) {
                count = Math.min(count, use + i);
            }
        }
        return count;
    }

    static int findMinWithMem(int amount , int index, int [] arr, int[][] dp) {
        if (amount == 0) {
            return 0;
        }

        if (amount < 0 || index > arr.length - 1) {
            return -1;
        }
        if (dp[amount][index] != -1) {
            return dp[amount][index];
        }
        int count = Integer.MAX_VALUE;
        for (int i = 0; i * arr[index] <= amount; i++) {
            int rest = amount - arr[index] * i;
            int use = findMinWithMem(rest, index + 1, arr, dp);
            if (use != -1 && use != Integer.MAX_VALUE) {
                count = Math.min(count, use + i);
            }
        }
        dp[amount][index] = count;
        return count;
    }

//    static int findMinWithMem(int amount , int [] arr){
//        int[][] dp = new int[amount + 1][arr.length];
//        for (int i = 0; i < amount + 1; i++) {
//            Arrays.fill(dp[i], -1);
//        }
//
//        for()
//    }

    static int find(int[] arr, int index, int target) {
        if (target == 0) {
            return 1;
        }
        if (target < 0 || index > arr.length - 1) {
            return 0;
        }

        int rest = 0;
        for (int piece = 0; piece * arr[index] < target; piece++) {
            int ways = find(arr, index + 1, target - piece * arr[index]);
            rest += ways;
        }
        return rest;
    }

//    static int findWithDp(int[] arr, int target){
//        int n = arr.length;
//        int[][] dp = new int[n][target];
//
//        // 通过递归可以发现 dp[i][j] 依赖于 dp[i+1][x] 即依赖于下一行的多个信息
//        // 所以遍历方向应该是自底向上
//        for(int i = n-1; i>=0;i--){
//            for (int piece = 0; piece * arr[i] < target; piece++) {
//                dp[i][]
//                int ways = find(arr, i + 1, target - piece * arr[i]);
//                rest += ways;
//            }
//        }
//    }
}
