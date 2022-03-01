package com.lynch.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * N 皇后问题，在一个 NxN 的棋盘中摆放N个皇后，求一共有多少种摆放方式
 * 要求：与皇后的同一行同一列的不能摆，与皇后的左右上角，左右下角所形成的对角线不能摆
 * 使用'Q'表示当前位置已摆放皇后，'.'表示没有摆放
 * Created by troub on 2022/3/1 16:36
 */
public class NQueen {
    public static void main(String[] args) {
        final int N = 8;
        char[][] board = new char[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                board[r][c] = '.';
            }
        }
        List<String> track = new ArrayList<>();
        final int count = get(board, track, 0, 0);
        System.out.println("result: "+ count);
    }

    private static int get(char[][] board, List<String> track, int startRow, int startCol) {
        int n = board.length;
//        if (!isValid(board, n, startRow, startCol)) {
//            return 0;
//        }
        // 若已经填满 N 个皇后则返回
        if (startRow == n) {
            track.clear();
            return 1;
        }
        int count = 0;
        for (int r = startRow; r < n; r++) {
            for (int c = startCol; c < n; c++) {
                if (!isValid(board, n, r, c)) {
                    continue;
                }
                board[r][c] = 'Q';
                // 记录摆放位置
                track.add(r + "," + c);
                // 由于下一个摆放的皇后不能与当前的皇后在同一行同一列，且不能同一对角线
                // 所以需要从下一行的对角线位置的下一个位置开始摆放
                final int result = get(board, track, r + 1, c + 2);
                if (result != 0) {
                    count += result;
                }
                board[r][c] = '.';
            }
        }
        return count;
    }

    private static boolean isValid(char[][] board, int n, int row, int col) {
        // 检测同一行是否已经摆放皇后
        for (int c = 0; c < col; c++) {
            if (board[row][c] == 'Q') {
                return false;
            }
        }
        // 检测同一列是否已经摆放皇后
        for (int r = 0; r < row; r++) {
            if (board[r][col] == 'Q') {
                return false;
            }
        }

        // 检测左上角及右上角是否已摆放皇后
        // 由于是从上往下穷举，所以只需要检测左右上角位置，左右下角无需检测
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                // 检测左上角
                if (r - 1 > -1 && c - 1 > -1 && board[r - 1][c - 1] == 'Q') {
                    return false;
                }
                // 检测右上角
                if (r - 1 > -1 && c + 1 < n && board[r - 1][c + 1] == 'Q') {
                    return false;
                }
            }
        }
        return true;
    }
}
