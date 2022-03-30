package com.lynch.maxsum;

/**
 * 给定一个整数矩阵, 请矩阵的最大累加和
 */
public class MaxSumOfMatrix {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {7, 8, -4, 5},
                {-2, 5, 11, 3},
                {1, 8, 9, -80},
                {-2, 6, -12, 13},
        };

        int row = arr.length, column = arr[0].length;
        int max = 0;
        for (int index = 0; index < row; index++) {
            // 将结果逐层累计到 sumRow, 然后取该数组中子数组的最大累加和
            int[] sumRow = new int[column];
            for (int k = 0; k < index + 1; k++) {
                for (int c = 0; c < column; c++) {
                    sumRow[c] += arr[k][c];
                }
            }
            int sum = maxSubArr(sumRow);
            max = Math.max(max, sum);
        }

        System.out.println("max sum of matrix: "+ max);
    }

    private static int maxSubArr(int[] arr) {
        int current = 0, sum = 0;

        for (int item : arr) {
            current += item;
            sum = Math.max(current, sum);
            current = current < 0 ? 0 : current;
        }
        return sum;
    }
}
