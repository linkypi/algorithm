package com.lynch.islands;

/**
 * https://leetcode.cn/problems/number-of-closed-islands
 *
 * 二维矩阵 grid由 0（土地）和 1（水）组成。岛是由最大的 4 个方向连通的 0 组成的群，
 * 封闭岛是一个完全 由 1 包围（左、上、右、下）的岛。
 * 请返回 封闭岛屿 的数目。
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/30 11:24
 */
public class FindClosedIsland {
    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 1, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0}
        };
        int count = findClosedIsland(grid);
        System.out.println("closed islands : "+ count);
    }

    private static int findClosedIsland(int[][] grid){
        int rows = grid.length;
        int columns = grid[0].length;

        // 处理上下边缘
        for(int col = 0; col < columns; col++){
            infect(grid,0, col);
            infect(grid, rows -1, col);
        }

        // 处理左右边缘
        for(int row = 0; row < rows; row++){
            infect(grid, row, 0);
            infect(grid, row, columns - 1);
        }

        int count = 0;
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < columns; col++) {
                if(grid[row][col] == 0) {
                    count ++;
                    infect(grid, row, col);
                }
            }
        }
        return count;
    }

    private static void infect(int[][] grid, int row, int col) {
        int m = grid.length;
        int n = grid[0].length;
        if (row >= m || col >= n || row < 0 || col < 0) {
            return;
        }
        if (grid[row][col] == 1) {
            return;
        }

        grid[row][col] = 1;
        infect(grid, row - 1, col);
        infect(grid, row + 1, col);
        infect(grid, row, col - 1);
        infect(grid, row, col + 1);
    }
}
