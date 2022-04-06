package com.lynch.matrix;


/**
 * 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
 *
 * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角
 * 的行号和列号，r2, c2 分别代表右下角的行号和列号。若有多个满足条件的子矩阵，返回任意一个均可。
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/4 15:44
 */
public class MaxSumOfMatrix {
    public static void main(String[] args) {
//        int[][] matrix = {
//            {-1, 0},
//            {0, -1}
//        };
        int[][] matrix = {
                {-4, -5}
        };

//        int maxSum = getMaxSum(matrix[0]);

        int[] result = find(matrix);
        System.out.println("r1: " + result[0] + ", c1: " + result[1] + ", r2: " + result[2] + " c2: " + result[3]);
    }

    private static int[] find(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int sum = Integer.MIN_VALUE;
        int r1 = 0, c1 = 0;
        int r2 = 0, c2 = 0;
        int[] arr = null;

        int startRow = 0;
        while (startRow < rows) {

            // 每次计算完成后清空数组
            arr = new int[cols];

            // 求第 i 行开始的累加和，累加每行后都统计一位数组的最大累加和
            for (int k = startRow; k < rows; k++) {
                for (int j = 0; j < cols; j++) {
                    arr[j] += matrix[k][j];
                }

                int[] result = maxSumOfArr(arr);
                if (sum < result[0]) {
                    sum = result[0];
                    r1 = startRow;
                    r2 = k;
                    c1 = result[1];
                    c2 = result[2];
                }
            }
            startRow++;
        }
        return new int[]{r1, c1, r2, c2};
    }

    /**
     * 从一维数组中查找子数组最大和
     *
     * @param arr
     * @return 返回起始位置及终止位置
     */
    private static int[] maxSumOfArr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        int sum = Integer.MIN_VALUE;
        int current = 0;
        int start = 0, temp = 0;

        int end = 0;
        for (int i = 0; i < arr.length; i++) {
            if (current <= 0) {
                temp = i;
                current = arr[i];
            } else {
                current += arr[i];
            }

            if (current > sum) {
                start = temp;
                end = i;
                sum = current;
            }
        }
        return new int[]{sum, start, end};
    }
}
