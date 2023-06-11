package com.lynch;

/**
 * 快速乘
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/25 14:02
 */
public class QuickMultiple {
    public static void main(String[] args) {

        int binLen = getBinLen(43);
        int binLen2 = getBinLen(127);
        System.out.println("bin len: "+ binLen);

        long result = quickMulti(15, 10);
        System.out.println("result: "+ result);

        int add = add(13, 25);
        System.out.println("add result: "+ add);
    }

    private static long quickMulti(long a, long b) {
        long result = 0;
        for (; b > 0; b >>= 1) {
            if ((b & 1) == 1) {
                result += a;
            }
            a <<= 1;
        }
        return result;
    }

    /**
     * 计算 a/b, 限制在 32 位数以内
     * @param a
     * @param b
     * @return
     */
    private static int div(int a, int b) {
        // 首先确定除数 b 拥有的二进制位长度

        int len = 0;
        int temp = b;
        // 从最高位 1 开始计算
        while (temp == 0) {
            temp <<= 1;
            len++;
        }
        // b 实际二进制的长度
        len = 32 - len;

//        int result = 0;
//        while(a!=0){
//            int x = a>>()
//        }
        return 0;
    }

    private static int getBinLen(int a) {
        int len = 0;
        int index = 0;
        int mask = (int) Math.pow(2, 32);
//        int mask = 2^32;
        int temp = (a & mask) >> 31;
        // 从最高位 1 开始计算
        while ((temp & 1) != 1) {
            a <<= 1;
            len++;
            temp = (a & mask) >> 31;
        }
        // b 实际二进制的长度
        len = 32 - len;
        return len;
    }



    private static int add(int a, int b) {
        while (b != 0) {
            // 获取进位
            int c = (a & b) << 1;
            // 异或 即 无进位相加
            a = a ^ b;
            // 依次相加直到进位位0
            b = c;
        }
        return a;
    }
}
