package com.lynch;

import java.util.ArrayList;
import java.util.List;

/**
 * N皇后问题：
 * 设计一种算法，打印 N 皇后在 N × N 棋盘上的各种摆法，
 * 其中每个皇后都不同行、不同列，也不在对角线上。
 * 这里的 “对角线” 指的是所有的对角线，不只是平分整个棋盘的那两条对角线
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/15 14:27
 */
public class NQueen {
    public static void main(String[] args) {
        int n = 1;
        boolean[][] board = new boolean[n][n];
        check(n,0, board);

        List<List<String>> strs = new ArrayList<>();
        for (boolean[][] item : chessBoards){
            List<String> rowStrs = new ArrayList<>();
            for (boolean[] row: item){
                StringBuilder builder = new StringBuilder();
                for (boolean col: row){
                    if(col){
                        builder.append("Q");
                    }else{
                        builder.append(".");
                    }
                }
                rowStrs.add(builder.toString());
            }
            strs.add(rowStrs);
        }

        System.out.println("count: " + chessBoards.size());
    }

    static List<boolean[][]> chessBoards = new ArrayList<>();
    static void  check(int n, int row, boolean[][] board) {
        if (n == row) {
            chessBoards.add(clone(board));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isValid(row, col, board)) {
                board[row][col] = true;
                check(n, row + 1, board);
                board[row][col] = false;
            }
        }
    }

    static boolean[][] clone(boolean[][] board){
        int m = board.length;
        int n = board[0].length;
        boolean[][] list = new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                list[i][j] = board[i][j];
            }
        }
        return list;
    }

    static boolean isValid(int row, int col, boolean[][] board) {

        //不同行
        for (int i = 0; i < board.length; i++) {
            if (board[row][i]) {
                return false;
            }
        }
        //不同列
        for (int i = 0; i < board.length; i++) {
            if (board[i][col]) {
                return false;
            }
        }

        //不在同一对角线
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (Math.abs(i - row) == Math.abs(j - col) && board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
