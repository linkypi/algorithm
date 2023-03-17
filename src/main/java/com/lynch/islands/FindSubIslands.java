package com.lynch.islands;

/**
 * https://leetcode.cn/problems/count-sub-islands/
 * 
 * 给你两个m x n的二进制矩阵grid1 和grid2，它们只包含0（表示水域）和 1（表示陆地）。
 * 一个 岛屿是由 四个方向（水平或者竖直）上相邻的1组成的区域。任何矩阵以外的区域都视为水域。
 *
 * 如果 grid2的一个岛屿，被 grid1的一个岛屿完全 包含，也就是说 grid2中该岛屿的每一个格子
 * 都被 grid1中同一个岛屿完全包含，那么我们称 grid2中的这个岛屿为 子岛屿。
 *
 * 请你返回 grid2中 子岛屿的 数目。
 * 
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/30 9:24
 */
public class FindSubIslands {

    public static void main(String[] args) {
        int[][] grid1 = {
                {1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 1, 1}
        };
        int[][] grid2 = {
                {1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1},
                {0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 0}
        };
        int result = countSubIslands(grid1, grid2);
        System.out.println("sub islands: " + result);
    }

    static int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 只要grid2中任意一个位置是陆地而在grid1是海水
                // 则说明grid2与该位置组成的岛屿都不可能是grid1的子岛屿，
                // 因为子岛屿必须被 grid1 完全包含，故需要使用 dfs 将不想关的岛屿设置为0
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    infect(grid2, i, j);
                }
            }
        }
        // 现在 grid2 中剩下的岛屿都是子岛，计算岛屿数量
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    res++;
                    infect(grid2, i, j);
                }
            }
        }
        return res;
    }

    private static void infect(int[][] grid, int row, int col) {
        int m = grid.length;
        int n = grid[0].length;
        if (row >= m || col >= n || row < 0 || col < 0) {
            return;
        }
        if (grid[row][col] == 0) {
            return;
        }

        grid[row][col] = 0;
        infect(grid, row - 1, col);
        infect(grid, row + 1, col);
        infect(grid, row, col - 1);
        infect(grid, row, col + 1);
    }
}
