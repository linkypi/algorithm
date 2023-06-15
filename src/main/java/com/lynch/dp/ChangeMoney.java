package com.lynch.dp;

/**
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 * 要求每种面额只能使用一张 ！
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/12 15:02
 */
public class ChangeMoney {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5};
        int targetSumWays1 = change(nums, 5);
        int targetSumWays2 = changeOptimize(nums, 5);
        System.out.println("ways: " + targetSumWays1);
    }

    public static int change(int[] coins, int target) {
        int n = coins.length;
        // dp[i][j] 表示在前1...i种零钱中可以凑出总数为j的方法数
        // 其结果取决于当前第i种零钱选与不选
        // 1. 选择当前零钱 i 则可以凑出总数j的方法数取决于 dp[i-1][j-arr[i]] 即 dp[i][j] = dp[i-1][j-arr[i]]
        // 2. 不选择当前零钱 i 则可以凑出总数j的方法数为 dp[i-1][j] 即 dp[i][j] = dp[i-1][j]
        // 故总的方法数为 dp[i][j] = dp[i-1][j-arr[i]] + dp[i-1][j]
        int[][] dp = new int[n][target + 1];

        // 使用前i种硬币凑出0元的方法数只有一种，不使用即可
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // 使用第一种零钱可以凑 coins[0] 的方法数必定只有一种
        dp[0][coins[0]] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= target; j++) {
                int pre = i-1;
                if (j >= coins[i]) {
                    dp[i][j] = dp[pre][j] + dp[pre][j - coins[i]];
                    System.out.printf("d%s%s = d%s%s + d%s%s\n", i, j, pre, j, pre, j - coins[i]);
                } else {
                    dp[i][j] = dp[pre][j];
                    System.out.printf("d%s%s = d%s%s \n", i, j, pre, j);
                }
            }
            System.out.println("");
        }
        return dp[n - 1][target];
    }

    /**
     * 基于一维压缩数组求解
     * 由于  dp[i][j] = dp[i-1][j-arr[i]] + dp[i-1][j]
     * 即 dp[i][j] 的值全部取决于其上一层的数值，故可以使用一维数组来求解
     * @param coins
     * @param target
     * @return
     */
    public static int changeOptimize(int[] coins, int target) {
        int n = coins.length;
        //
        int[] dp = new int[target + 1];

        // 使用前i种硬币凑出0元的方法数只有一种，不使用即可
        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            // 总数必须倒叙遍历，因为正序遍历会将上一层保存的值覆盖
            // 如 d15 = d05 + d03 对应目前的是 d5 = d5 + d3 仅仅将上下两层的意思隐藏罢了
            // 但是实际的意思仍然是 当前层的方法数dp取值取决于上一层的数值，若 target 仍然使用正序遍历
            // 则 d3 首先会被覆盖，等到计算 d5 时， d3 已经不是上一层 d3 的值
            for (int j = target; j > 0; j--) {
                if (j >= coins[i]) {
                    dp[j] = dp[j] + dp[j - coins[i]];
                    System.out.printf("d%s = d%s + d%s \n", j, j, j - coins[i]);
                } else {
                    dp[j] = dp[j];
                    System.out.printf("d%s = d%s \n", i, j);
                }
            }
            System.out.println("");
        }
        return dp[target];
    }

}
