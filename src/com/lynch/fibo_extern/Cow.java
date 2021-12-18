package com.lynch.fibo_extern;

/**
 * 母牛生仔问题
 */
public class Cow {
    public static void main(String[] args) {

    }

    /**
     * 有一头母牛，它每年年初生一头小母牛。每头小母牛从第四个年头开始，
     * 每年年初也生一头小母牛。请编程实现在第 n 年的时候，共有多少头母牛？
     * @param n
     * @return
     */
    private int getCows(int n) {
        if (n <= 3) {
            return n;
        }
        int[][] base = new int[][]{
                {3, 2, 1}
        };

        // 系数矩阵, 使用前面几年已知的数据推导得出
        int[][] coefficient = new int[][]{
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}
        };
        coefficient = powOfMatrix(coefficient, n - 3);
        int[][] result = multiplyMatrix(base, coefficient);
        return result[0][0];
    }

    /**
     * 求矩阵的 n 次数
     * @param matrix
     * @param n
     * @return
     */
    private static int[][] powOfMatrix(int[][] matrix, int n) {
        int[][] base = new int[matrix.length][matrix[0].length];
        int rows = matrix.length;
        int columns = matrix[0].length;
        // 设置单位矩阵, 左上角到右下角对角线的值全为 1
        for (int index = 0; index < rows; index++) {
            base[index][index] = 1;
        }

        int[][] result = new int[matrix.length][matrix[0].length];
        while (n != 0) {
            if ((n & 1) == 1) {
                result = multiplyMatrix(result, base);
            }
            base = multiplyMatrix(base, base);
            n >>= 1;
        }
        return result;
    }

    private static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2){
        if(matrix1[0].length == matrix2.length){
            throw new IllegalArgumentException("matrix1 的列数必须等于 matrix2 的行数");
        }

        int rows = matrix1.length;
        int columns = matrix2[0].length;
        int[][] result = new int[rows][columns];
        for (int row=0;row<rows;row++){
            for (int col=0;col<columns;col++){
                for (int k=0;k<columns;k++){
                    result[row][col]+= matrix1[row][k]*matrix2[k][col];
                }
            }
        }
        return result;
    }
}
