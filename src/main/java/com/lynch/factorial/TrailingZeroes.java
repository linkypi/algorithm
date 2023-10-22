package com.lynch.factorial;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/factorial-trailing-zeroes/description/
 *
 * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
 *
 * 提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
 *
 * @author leo
 * @ClassName TrailingZeroes
 * @description: TODO
 * @date 10/21/23 11:08 AM
 */
public class TrailingZeroes {
    @Test
    public void test(){
        int zeros = findZeros(125);
        System.out.println("tail zero count: "+ zeros);
    }

    private int findZeros(int n) {
        // 阶乘后的结果后面存在多少个 0 ,取决于累乘过程中有多少个 10 , 即 2x5
        // 由于每隔两个数必存在一个偶数, 也就必然存在一个 2, 而每隔 5 个数才会出现一个 5
        // 故只要找出其中有多少个 5 即可, 但是当遇到 25 , 125 , 625 ... 时 5 出现的
        // 个数会有所增加, 如 25 出现的是两个 5, 125 出现的是 3 个 5, 625 出现的是四个 5
        int divisor = 5;
        int count = 0;
        while (n > 0) {
            count += n / divisor;
            n = n / divisor;
        }
        return count;
    }
}
