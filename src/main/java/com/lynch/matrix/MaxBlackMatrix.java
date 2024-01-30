package com.lynch.matrix;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/max-black-square-lcci/
 * 给定一个方阵，其中每个单元 (像素) 非黑即白。设计一个算法，找出 4 条边皆为黑色像素的最大子方阵。
 * 返回一个数组 [r, c, size] ，其中r,c分别代表子方阵左上角的行号和列号，
 * size 是子方阵的边长。若有多个满足条件的子方阵，返回 r 最小的，若 r 相同，
 * 返回 c 最小的子方阵。若无满足条件的子方阵，返回空数组。
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/4 15:44
 */
public class MaxBlackMatrix {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 0, 1, 0, 1, 0, 1},
                {0, 0, 1, 1, 1, 0, 1},
                {0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1},
        };
        int[] ints = find(matrix);
        System.out.println("row: " + ints[0] + ", col: " + ints[1] + ", size: " + ints[2]);
    }

    public static int[] find(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 统计每行从右到左连续出现0的个数
        int[][] towardRights = new int[rows][cols];
        // 统计每列从下到上连续出现0的个数
        int[][] towardDowns = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = cols - 1; j >= 0; j--) {
                if (j == cols - 1 && matrix[i][j] == 0) {
                    towardRights[i][j] = 1;
                    continue;
                }

                if (matrix[i][j] == 0) {
                    towardRights[i][j] = towardRights[i][j + 1] + 1;
                }
            }
        }
        for (int j = 0; j < cols - 1; j++) {
            for (int i = rows - 1; i >= 0; i--) {
                if (i == rows - 1 && matrix[i][j] == 0) {
                    towardDowns[i][j] = 1;
                    continue;
                }

                if (matrix[i][j] == 0) {
                    towardDowns[i][j] = towardDowns[i + 1][j] + 1;
                }
            }
        }

        int row = 0, col = 0, size = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 根据子矩阵上方连续边长长度与左方连续边长长度最小值 获得子矩阵有效边长
                int len = Math.min(towardRights[i][j], towardDowns[i][j]);
                if (len == 0 || i + len > rows || j + len - 1 > cols) {
                    continue;
                }

                // 根据边长判断右边边长及下方边长是否满足 size ，满足则说明边长为size的子矩阵满足要求
                if (towardRights[i + len - 1][j] >= len && towardDowns[i][j + len - 1] >= len) {
                    if (size < len) {
                        size = len;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        return new int[]{row, col, size};
    }
}
