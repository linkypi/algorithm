package com.lynch.islands;

import java.text.MessageFormat;
import java.util.HashSet;

/**
 * 力扣第 694 题（会员专享题）「 不同的岛屿数量」:
 *  输入一个二维矩阵，0 表示海水，1 表示陆地，
 * 计算不同型状的 (distinct) 岛屿数量
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/30 10:19
 */
public class FindDiffIslands {
    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 0, 1, 1}
        };

        int distinctIslands = findDistinctIslands(grid);
        System.out.println("distinct islands: "+ distinctIslands);
    }

    private static final String[] DIRECTIONS = {"UP","DOWN","LEFT","RIGHT", "START"};

    private static int findDistinctIslands(int[][] grid){
        int m = grid.length;
        int n = grid[0].length;

        HashSet<String> set = new HashSet<>();
        for(int i=0;i<m;i++){
            for(int j = 0;j<n;j++){
                if(grid[i][j] == 1){
                    StringBuilder builder = new StringBuilder();
                    String model = infect(grid, i, j, builder, 4);
                    String messge = MessageFormat.format("island position: ({0},{1}), direction: {2}", i, j, model);
                    System.out.println(messge);
                    set.add(model);
                }
            }
        }
        return set.size();
    }

    private static String infect(int[][] grid, int row, int col, StringBuilder builder, int direction) {
        int m = grid.length;
        int n = grid[0].length;
        if (row < 0 || row >= m || col < 0 || col >= n) {
            return "";
        }
        if (grid[row][col] == 0) {
            return "";
        }

        builder.append(DIRECTIONS[direction]).append(";");
        grid[row][col] = 0;
        infect(grid, row - 1, col, builder,0);
        infect(grid, row + 1, col, builder,1);
        infect(grid, row, col - 1, builder,2);
        infect(grid, row, col + 1, builder,3);

        builder.append("-").append(DIRECTIONS[direction]).append(";");
        return builder.toString();
    }
}
