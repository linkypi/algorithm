package com.lynch.extern;

/**
 * 使用异或解决的相关算法题
 */
public class XOR {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 4, 6, 3, 4, 6, 7, 9, 7};
        int oddNumber = findOneOddNumber(arr);
        System.out.println("odd num: " + oddNumber);

        int a = 12, b = 28;
        System.out.println("before swap , a: " + a + ", b: " + b);
        swapWithXor(a, b);

        int[] arr2 = new int[]{3, 4, 6, 3, 4, 6, 7, 9, 7, 8};
        int[] result = findTwoOddNumber(arr2);
        System.out.println("two odd num: " + result[0] + " , " + result[1]);
    }

    private static void swapWithXor(int a, int b){
        a = a^b;
        b = a^b;
        a = a^b;
        System.out.println("after swap , a: " + a + ", b: "+ b);
    }

    /**
     * 在给定的数组中, 有一个数出现了奇数次, 其他的出现了偶数次, 求出现了奇数次的数
     * 异或运算: 相同为 0 ,不同为 1. 任何数与 0 异或都是其自身, 任何数与其自身异或都是 0
     * @return
     */
    private static int findOneOddNumber(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int x = 0;
        //任何数与 0 异或都是其自身, 任何数与其自身异或都是 0
        for (int item : arr) {
            x ^= item;
        }
        return x;
    }

    /**
     * 在给定的数组中, 有两个数出现了奇数次, 其他的出现了偶数次, 求出现了奇数次的两个数
     * 异或运算: 相同为 0 ,不同为 1. 任何数与 0 异或都是其自身, 任何数与其自身异或都是 0
     * @return
     */
    private static int[] findTwoOddNumber(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        // 首先求出两个奇数的异或结果 result
        // 令两个奇数分别为 a , b, 则 result = a ^ b
        int result = 0;
        for (int item : arr) {
            result ^= item;
        }

        // 获取两个奇数异或后右边第一个是 1 的数, 即表示 a与 b在该位置上必然不同
        int rightOne = result & (~result + 1);

        // 基于右边第一个位置是 1 的位置做区分,
        // a , b 必然有一个数在该位置为 0 , 另外一个数必然为 1
        // 所以此时只要用 rightOne 与其中一组数异或即可得到 a 或 b 的其中一个
        int x = 0;
        for (int item : arr) {
            if ((rightOne & item) == 0) {
                x = rightOne ^ item;
            }
        }

        return new int[]{x, result ^ x};
    }
}
