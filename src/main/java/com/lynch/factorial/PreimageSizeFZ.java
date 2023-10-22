package com.lynch.factorial;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/preimage-size-of-factorial-zeroes-function/description/
 *
 * f(x) 是 x! 末尾是 0 的数量。回想一下 x! = 1 * 2 * 3 * ... * x，且 0! = 1 。
 * 例如， f(3) = 0 ，因为 3! = 6 的末尾没有 0 ；而 f(11) = 2 ，因为 11!= 39916800 末端有 2 个 0 。
 * 给定 k，找出返回能满足 f(x) = k 的非负整数 x 的数量。
 *
 * @author leo
 * @ClassName PreimageSizeFZF
 * @description: TODO
 * @date 10/21/23 4:30 PM
 */
public class PreimageSizeFZ {
    @Test
    public void test(){

    }

    /**
     * 根据 TrailingZeroes 案例得知阶乘后面存在多少个 0, 是由阶乘过程中存在多少个 5 所决定
     * 阶乘结果存在 k 个 0, 那么其范围必定在 [0, 5k] 之间, 因为每隔 5 个数必定会出现一个 5
     * 阶乘结果末尾出现 K 个 0 的个数即为头一次出现 K+1 个 0 的数与头一次出现 K 个 0 的数的差值, 如:
     * 5! = 1 x 2 x 3 x 4 x 5 = 120, 头一次出现一个 0
     * 6! = 120 x 6 = 720
     * 7! = 5040
     * 8! = 40320
     * 9! = 362880
     * 10! = 3628800 , 头一次出现两个 0
     * 实际出现 K=1 个 0 的数有 10-5=5 个
     * 问题就可以转换为寻找 K 的左边界 与右边界, 左右边界的差值即为结果
     * 但是通过前面分析得知 每隔 5 个数必定会出现一个 5, 那就说明每隔 5 个数末尾 0 的个数必定自增
     * 所以末尾出现 k 个0的数要么是 5 个,要么就是 0 个, 实际仅需要找出其中一个便知道结果是 5 还是 0
     *
     * @param k
     * @return
     */
    private int find(int k) {
        int start = 0;
        int end = 5 * k;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int nums = 0;
            long n = 5L;
            while (n <= mid) {
                nums += mid / n;
                n *= 5;
            }
            if (nums == k) {
                return 5;
            }
            if (nums > k) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return 0;
    }
}
