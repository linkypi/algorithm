package com.lynch;

/**
 * Description algorithm
 * Created by troub on 2021/5/27 16:44
 */
public class Fibonacci {
    public static void main(String[] args) {
        final int count= 44;

        long start = System.nanoTime();
        int result = fibonacci_un_recursive(count);
        System.out.println("unrecursive result: "+ result + ", cost "+ (System.nanoTime() - start) +" ns");

        start = System.nanoTime();
        result =fibonacci_recursive(count);
        System.out.println("recursive result: "+ result + ", cost "+ (System.nanoTime() - start) +" ns");

        useMatrix(count);
    }

    private static void useMatrix(int count) {
        long start = System.nanoTime();
        /**
         * f(1) f(0)
         */
        long[][] base = new long[SIZE][1];
        base[0][0] = 1;
        base[1][0] = 0;

        /**
         * 实现原理参考 https://www.desgard.com/algo/docs/part2/ch01/3-matrix-quick-pow/
         *                 n-1次方
         *     f(n)    1  1       f(1)
         *           =        *
         *    f(n-1)   1  0       f(0)
         *
         */

        // 系数矩阵
        long[][] coefficient = new long[SIZE][SIZE];
        coefficient[0][0] = 1;
        coefficient[0][1] = 1;
        coefficient[1][0] = 1;
        coefficient[1][1] = 0;
        final long[][] quickPowArr = quickPowArr(coefficient, count -1);
        final long[][] matrix = matrixMultiple(quickPowArr, base);

        System.out.println("matrix result: "+ matrix[0][0]+ ", cost "+ (System.nanoTime() - start) +" ns");
    }

    /**
     * 使用递归方式求解，当 n 较大时会引起栈溢出
     * 时间复杂度为 O(n2)
     * @param n
     * @return
     */
    private static int fibonacci_recursive(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fibonacci_recursive(n - 1) + fibonacci_recursive(n - 2);
    }

    /**
     * 时间复杂度为 O(n), 空间复杂度为 O(1)
     * @param n
     * @return
     */
    private static int fibonacci_un_recursive(int n) {
        // 将每次计算的结果存储到 res, 减少递归次数
        int a = 0, b = 1, res = 0;
        for (int index = 1; index < n; index++) {
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }

    // 快速幂
    private static long quickPow(int base, int n) {
        long result = 1;
        while (n != 0) {
            if ((n & 1) == 1 ){
                result *= base;
            }
            base *= base;
            n >>= 1;
        }
        return result;
    }

    /**
     * 两个矩阵相乘 arr1 * arr2
     * 只有前一个矩阵的列数 等于 后一个矩阵的行数才可以相乘
     * @param matrix1
     * @param matrix2
     * @return
     */
    private static long[][] matrixMultiple(long[][] matrix1, long[][] matrix2) {
        // 只有前一个矩阵的列数 等于 后一个矩阵的行数才可以相乘
        if (matrix1[0].length != matrix2.length) {
            throw new IllegalStateException("matrix multiple not allow, columns of arr1 is not equal rows of arr2.");
        }

        int rows = matrix1.length;
        int columns = matrix2[0].length;
        long[][] result = new long[rows][columns];

        // matrix1的行 * matrix2 的列
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < columns; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    private static long[][] quickPowArr(long[][] arr, int n) {
        long[][] result = new long[SIZE][SIZE];
        // 主对角线都为 1
        for (int i = 0; i < SIZE; i++) {
            result[i][i] = 1;
        }
        while (n != 0) {
            if ((n & 1) == 1) {
                result = matrixMultiple(result, arr);
            }
            arr = matrixMultiple(arr, arr);
            n >>= 1;
        }
        return result;
    }

    private static final int SIZE = 2;

}
