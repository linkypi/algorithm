package com.lynch;

/**
 * Description algorithm
 * Created by troub on 2021/5/27 16:44
 */
public class Fibonacci {
    public static void main(String[] args) {
        System.out.println("un recursive result: "+ fibonacci_un_recursive(44));
        System.out.println("recursive result: "+ fibonacci_recursive(44));

    }

    /**
     * 使用递归方式求解，当 n 较大时会引起栈溢出
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
}
