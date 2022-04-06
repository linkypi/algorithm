package com.lynch.matrix;

/**
 * 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
 *
 * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角
 * 的行号和列号，r2, c2 分别代表右下角的行号和列号。若有多个满足条件的子矩阵，返回任意一个均可。
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/4 15:44
 */
public class MaxSumOfMatrix {
    public static void main(String[] args) {
        int[][] matrix = {
                {2, -4, 3, 2, 1},
                {-2, 3, -1, 6, 4},
                {4, -1, 3, 2, -1},
                {-3, 6, 1, 4, -2}
        };
//        int[] arr = {5,-6,3,-1,4,-2};
        int[] arr = {1, -2, 3, 10, -4, 7, 2, -5};
        int[] sum = findMaxSumInSubArr(arr);
        System.out.println("sum: " + sum[2] );

        int[] maxSum = findMaxSum(matrix);
        System.out.println("r1: " + maxSum[0] + "c1: " + maxSum[1]
        + "r2: " + maxSum[2] + "c2: " + maxSum[3] );
    }

    public static int[] findMaxSum(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // 记录每一行子数组最大和的 起始位置，终止位置 及 累加和
        int[][] tempRows = new int[rows][3];
        for (int i = 0; i < rows; i++) {
            int[] result = findMaxSumInSubArr(matrix[i]);
            if (result == null || result.length == 0) {
                continue;
            }
            int start = result[0];
            int end = result[1];
            tempRows[i][0] = start;
            tempRows[i][1] = end;

            if (i - 1 >= 0 && start < tempRows[i - 1][0]) {
                tempRows[i][0] = tempRows[i - 1][0];
            }
            if (i - 1 >= 0 && end > tempRows[i - 1][1]) {
                tempRows[i][1] = tempRows[i - 1][1];
            }
        }

        // 记录每一列子数组最大和的 起始位置，终止位置 及 累加和
        int[][] tempCols = new int[cols][3];
        for (int j = 0; j < cols; j++) {
            int[] arr = new int[rows];
            for (int i = 0; i < rows; i++) {
                arr[i] = matrix[i][j];
            }
            int[] result = findMaxSumInSubArr(arr);
            if (result == null || result.length == 0) {
                continue;
            }
            int start = result[0];
            int end = result[1];
            tempCols[j][0] = start;
            tempCols[j][1] = end;

            if (j - 1 >= 0 && start < tempCols[j - 1][0]) {
                tempCols[j][0] = tempCols[j - 1][0];
            }
            if (j - 1 >= 0 && end > tempCols[j - 1][1]) {
                tempCols[j][1] = tempCols[j - 1][0];
            }
        }

        int r1 = tempCols[cols - 1][0];
        int c1 = tempRows[rows - 1][0];
        int r2 = tempCols[cols - 1][1];
        int c2 = tempRows[rows - 1][1];
        return new int[]{r1, c1, r2, c2};
    }

    static int[] findMaxSumInSubArr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int sum = 0;
        int current = 0;
        int start = 0, end = 0;
        for (int i = 0; i < arr.length; i++) {
            current += arr[i];
            if(current<0){
                current = 0;
                start = i+1;
            }else{
                sum = Math.max(sum, current);
                if(sum==current){
                    end = i;
                }
            }
        }

        System.out.println("start: "+ start + ", end: "+ end);
        return new int[]{ start, end, sum};
    }
}
