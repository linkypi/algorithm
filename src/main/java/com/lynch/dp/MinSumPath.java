package com.lynch.dp;

/**
 * 给定一个二维数组，其中的元素都是非负整数，要求从左上角开始，
 * 只能向右或向下移动，最终到达右小角，求经过的路径和
 * Created by troub on 2022/2/9 15:48
 */
public class MinSumPath {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        final int minPath = getMinPath(arr);
        final int minPath2 = getMinPath2(arr);
        System.out.println("min sum path: " + minPath + ", get min path optimize: "+ minPath2);
    }

    private static int getMinPath(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        int[][] dp = new int[m][n];

        // 初始值全部为 -1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return process(dp, arr, m - 1, n - 1);
    }

    private static int process(int[][] dp, int[][] arr, int i, int j) {

        if (i == 0 && j == 0) {
            return arr[0][0];
        }
        // 越界则返回一个最大值
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        final int process1 = process(dp, arr, i - 1, j);
        final int process2 = process(dp, arr, i, j - 1);
        dp[i][j] = Math.min(process1, process2) + arr[i][j];
        return dp[i][j];
    }

    private static int getMinPath2(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = arr[0][0];

        // 初始化首行与首列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + arr[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + arr[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + arr[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }
}
