package com.lynch.knapsack_problem;

/**
 * 01 背包问题
 *
 * 一个旅行者有个最多能装 M 公斤的背包，现在有 n 件物品，
 * 它们的重量分别是W1，W2，... Wn, 它们的价值分别为 C1.C2.... Cn
 * 求旅行者能获得的最大总价值.
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/9 21:39
 */
public class ZeroOneKnapsack {
    public static void main(String[] args) {
        int m = 10;
        int n = 4;
        // arr[i][0] 表示物品重量， arr[i][1]表示物品价值
        int[][] arr = {{2, 1}, {3, 3}, {4, 5}, {7, 9}};

        int maxValue = findMaxValue(m, n, arr);
        System.out.println("max value: "+ maxValue);

    }

    public static int findMaxValue(int m, int n, int[][] arr) {

        // dp[i][j]表示从前n件物品中装满最多不超过j公斤的背包所获得的最大价值
        // 该问题属于01背包问题，即每件物品可以选，可以不选
        // dp[i][j] 的最大价值在于当前物品 i 选与不选中的最大值 即
        // dp[i][j] = max(dp[i-1][j-Wi] + Ci, dp[i-1][j])
        // 从公式可知，dp[i][j]的结果取决于前一行 i-1 的结果，故遍历顺序可以从上往下，从左往右
        // 取背包容量M=10，n=4件物品为例，画出下发矩阵图（绘制工具使用 https://tableconvert.com/zh-cn/magic-generator ）
        //                                 容量 j
        //          +---+---+---+---+---+---+---+---+---+---+---+----+
        //          |   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
        //          +---+---+---+---+---+---+---+---+---+---+---+----+
        //          | 0 |   |   |   |   |   |   |   |   |   |   |    |
        //          +---+---+---+---+---+---+---+---+---+---+---+----+
        //  物品 i  | 1 |   |   |   |   |   |   |   |   |   |   |    |
        //          +---+---+---+---+---+---+---+---+---+---+---+----+
        //          | 2 |   |   |   |   |   |   |   |   |   |   |    |
        //          +---+---+---+---+---+---+---+---+---+---+---+----+
        //          | 3 |   |   |   |   |   |   |   |   |   |   |    |
        //          +---+---+---+---+---+---+---+---+---+---+---+----+
        int[][] dp = new int[n][m + 1];

        // 处理边界问题 第一行所获得的最大价值
        for (int col = 0; col <= m; col++) {
            // 即使用第一个物品装包可以获得的最大价值
            // 同时背包的容量必须足够容纳第一个物品才有意义
            if (col >= arr[0][0]) {
                dp[0][col] = arr[0][1];
            }
        }

        // 处理边界问题 第一列所获得的最大价值，
        // dp[x][0]即不装物品可以获得的最大价值，自然是0，故该段代码可以忽略
        for (int row = 0; row < n; row++) {
            dp[row][0] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= m; j++) {
                // 背包容量必须大于物品重量才有意义
                int weight = arr[i][0];
                int value = arr[i][1];
                if (j >= weight) {
                    dp[i][j] = Math.max(dp[i - 1][j - weight] + value, dp[i - 1][j]);
                }else{
                    // 如果当前物品重量大于背包容量，那只能放弃选择该物品，
                    // 此时的最大价值就取决于前 i-1 个 物品的最大价值 即 dp[i - 1][j]
                    // 该情况容易遗漏，因为dp[i][j] 的定义是前i个物品，而非仅仅当前一个物品！！！
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][m];
    }


}
