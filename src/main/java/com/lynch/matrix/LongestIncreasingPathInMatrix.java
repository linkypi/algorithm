package com.lynch.matrix;

import org.junit.Test;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/
 *
 * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。
 *
 * 输入：matrix = [[9,9,4],[6,6,8],[2,1,1]]
 * 输出：4
 * 解释：最长递增路径为 [1, 2, 6, 9]。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/24 14:57
 */
public class LongestIncreasingPathInMatrix {
    @Test
    public void test() {
        int[][] matrix = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        int[][] matrix2 = {
                {3, 4, 5},
                {3, 2, 6},
                {2, 2, 1}
        };

        int[][] matrix3 = {
                {1}
        };

        int count = find(matrix);
        int count2 = find(matrix2);
        int count3 = find(matrix3);
        System.out.println("count: "+ count);
        System.out.println("count2: "+ count2);
        System.out.println("count3: "+ count3);
    }

    private static int[][] directions = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    private int[][] dp = null;
    private int[][] cache = null;
    private int max = Integer.MIN_VALUE;

    /**
     * 超出时间限制 : 135 / 138 个通过的测试用例
     * 朴素深度优先搜索，时间复杂度是指数级，会超出时间限制
     * 朴素深度优先搜索的时间复杂度过高的原因是进行了大量的重复计算，同一个单元格会被访问多次
     * 每次访问都要重新计算。由于同一个单元格对应的最长递增路径的长度是固定不变的，因此可以使用记忆化的方法进行优化
     * @param matrix
     * @return
     */
    private int find(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        dp = new int[m][n];
        max = Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], 1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 遍历四个方向的数字是否存在递增路径
                scan(matrix, i, j);
            }
        }
        return max == Integer.MIN_VALUE ? 1 : max;
    }

    private void scan(int[][] matrix, int i, int j) {
        int m = matrix.length;
        int n = matrix[0].length;

        // 遍历四个方向的数字是否存在递增路径
        for (int[] item : directions) {
            int x = i + item[0];
            int y = j + item[1];
            if (x > -1 && x < m && y > -1 && y < n && matrix[i][j] > matrix[x][y]) {
                // 下一个位置很有可能已在其他方向上有更长的递增路径，此时需要取其最大路径
                dp[x][y] = Math.max(dp[x][y], dp[i][j] + 1);
                max = Math.max(dp[x][y], max);
                // 递归遍历下一个位置（x,y）的四个方向是否还存在更长的递增路径
                scan(matrix, x, y);
            }
        }
    }

    private int findWithCache(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        dp = new int[m][n];
        max = Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 遍历四个方向的数字是否存在递增路径
                max = Math.max(max, scanWithCache(matrix, i, j));
            }
        }
        return max == Integer.MIN_VALUE ? 1 : max;
    }

    private int scanWithCache(int[][] matrix, int i, int j) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        int m = matrix.length;
        int n = matrix[0].length;
        // 以matrix[i][j]为起点的最长递增路径
        int maxLen = 1;
        // 遍历四个方向的数字是否存在递增路径
        for (int[] item : directions) {
            int x = i + item[0];
            int y = j + item[1];
            // 若某一个方向的下一个数比当前数大，则说明找到了递增路径。
            if (x > -1 && x < m && y > -1 && y < n && matrix[i][j] > matrix[x][y]) {
                // 选择 4 个方向的最大路径的最大值作为 maxLen
                maxLen = Math.max(maxLen, scanWithCache(matrix, x, y) + 1);
            }
        }
        // 将以matrix[i][j]为起点的最长递增路径存储在 dp[i][j] 中
        dp[i][j] = maxLen;
        return dp[i][j];
    }
}
