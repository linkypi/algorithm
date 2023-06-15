package com.lynch;

/**
 * https://leetcode.cn/problems/maximal-square/
 *
 * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/23 13:48
 */
public class MaxSquare {
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 1, 1, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 0},
        };

        int result = find(matrix);
        int result2 = count(matrix);
        System.out.println("result： "+ result);
    }

    public static int find(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;
        // dp[i][j] 表示以(i,j)为右下角的正方形最大边长
        // dp[i][j] 取决于 左边，上边 及 左上角 三者的最小值
        int[][] dp = new int[m][n];

        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        int min = Math.min(dp[i - 1][j], dp[i][j - 1]);
                        dp[i][j] = Math.min(dp[i - 1][j - 1], min) + 1;
                    }

                    max = Math.max(dp[i][j], max);
                }
            }
        }
        return max;
    }

    public static int count(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;

        // dp[i][j] 表示以i行j列为右下角的矩阵中的最大正方形边长
        // dp[i][j]当前的边长由左边一个位置，上边一个位置以及左上角位置边长中的最小者来决定
        // 即 dp[i][j] = min(dp[i][j-1], dp[i-][j], dp[i-1][j-1]), 若当前位置是 1 增需要对边长加一
        int[][] dp = new int[row][col];

        // 处理第一行
        for (int j = 0; j < col; j++) {
            if (arr[0][j] == 1) {
                dp[0][j] = 1;
            }
        }
        // 处理第一列
        for (int i = 0; i < row; i++) {
            if (arr[i][0] == 1) {
                dp[i][0] = 1;
            }
        }
        // 最终最大矩形边长由dp中的最大值决定
        int max = Integer.MIN_VALUE;
        // 注意下面的索引从 1 开始，因为前面已经处理第一行第一列的情况
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (arr[i][j] == 1) {
                    int min = Math.min(dp[i][j - 1], dp[i - 1][j]);
                    dp[i][j] = Math.min(min, dp[i - 1][j - 1]) + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
}
