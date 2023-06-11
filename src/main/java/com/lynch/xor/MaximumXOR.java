package com.lynch.xor;

import java.util.HashSet;

/**
 * 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/8 10:20
 */
public class MaximumXOR {
    public static void main(String[] args) {
        int[] arr = {3, 10, 5, 25, 2, 8};
        int result = find(arr);
        System.out.println("result: "+ result);
    }

    static int find(int[] arr) {

        // 首先获取最大值
        int max = Integer.MIN_VALUE;
        for (int item : arr) {
            max = Math.max(item, max);
        }

        // 求最高的 1 所在位置 目的为了过滤高位多余的0位 减少遍历次数， 对于数值较大的数该步骤可以消除
        int highestOne = 31;
        while ((max & (1 << highestOne)) == 0 && highestOne > -1) {
            highestOne--;
        }

        int mask = 0;
        int res = 0;
        for (int i = highestOne; i > 0; i--) {
            // 掩码
            mask = mask | 1 << i;
            System.out.println("i: " + i);
            System.out.println("mask: " + Integer.toBinaryString(mask));

            // 取得各个元素前 mask 位 作为前缀 prefix
            HashSet<Integer> set = new HashSet<>();
            for (int item : arr) {
                set.add(item & mask);
            }

            int temp = res | 1 << i;
            System.out.println("temp: " + temp + "  --->  " + Integer.toBinaryString(temp));

            for (int prefix : set) {
                if (set.contains(prefix ^ temp)) {
                    res = temp;
                    System.out.println("res: " + res + "  --->  " + Integer.toBinaryString(res));
                    break;
                }
            }
            System.out.println("");
        }
        return res;
    }

}
