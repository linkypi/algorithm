package com.lynch.backtrack;

/**
 * 数独，棋盘共有9x9个方格，每行每列的数字都不能相同，其中9x9方格还分为了9个均分的3x3大方格
 * 同时还需确保 3x3方格中的数字也不能重复。该问题类似八皇后问题，可以使用回溯方法暴力求解
 * Created by troub on 2022/2/28 15:49
 */
public class Sudoku {
    public static void main(String[] args) {

    }

    private static int ROWS = 9;
    private static int COLOMNS = 9;

    private static boolean solve(char[][] board, int row, int column) {
        // 若当前行的所有列都已尝试则从下一行开始处理
        if (column == COLOMNS) {
            return solve(board, row + 1, 0);
        }
        // 若当前已经存在数字则跳过继续处理下一个
        if (board[row][column] != '.') {
            return solve(board, row, column + 1);
        }
        // 若所有的行及列都已尝试过，则表示当前填入的字符符合要求
        if (row == ROWS) {
            return true;
        }
        for (char x = '1'; x <= '9'; x++) {
            if (!isValid(board, row, column, x)) {
                continue;
            }
            board[row][column] = x;
            if (solve(board, row, column + 1)) {
                return true;
            }
            board[row][column] = '.';
        }

        //穷举完1-9都没有找到解
        return false;
    }

    private static boolean isValid(char[][] board, int row, int column, char value) {
        for (int i = 0; i < 9; i++) {
            // 若当前行所在所有列中存在重复数字则无效
            if (board[row][i] == value) {
                return false;
            }
            // 若当前列所在的所有行中存在重复数字则无效
            if (board[i][column] == value) {
                return false;
            }
        }

        // 获取 3x3 九方格开始位置
        int startRow = (row / 3) * 3;
        int startCol = (column / 3) * 3;
        // 判断 3x3 九方格是否存在重复数字
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == value) {
                    return false;
                }
            }
        }
        return true;
    }
}