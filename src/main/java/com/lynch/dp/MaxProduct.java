package com.lynch.dp;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/maximum-product-subarray/
 *
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 * 测试用例的答案是一个 32 - 位 整数。
 * 子数组 是数组的连续子序列。
 *
 * 示例 1:
 * 输入: nums = [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 *
 * 示例 2:
 * 输入: nums = [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/24 16:02
 */
public class MaxProduct {
    @Test
    public void test() {

        int a = Integer.MAX_VALUE;
        int b = a+1;
//        int[] arr = {2, 3, -2, 4};
        int[] arr = {-2, 0, -1};
        int max = findMax(arr);
        System.out.println("max : " + max);
    }

    public int findMax(int[] arr) {
        int n = arr.length;
        // mins[i] 表示从0到i位置成绩最小值
        // 当前位置的最小值为： 前一个位置最小 * 正数  或者 前一个位置最大 * 负数
        // 当前位置的最大值为： 前一个位置最大 * 正数  或者 前一个位置最小 * 负数
        int[] mins = new int[n];
        int[] maxs = new int[n];

        mins[0] = arr[0];
        maxs[0] = arr[0];
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            int item = arr[i];
            if (item >= 0) {
                mins[i] = Math.min(item, item * mins[i - 1]);
                maxs[i] = Math.max(item, item * maxs[i - 1]);
            } else {
                mins[i] = Math.min(item, maxs[i - 1] * item);
                maxs[i] = Math.max(item, mins[i - 1] * item);
            }
            max = Math.max(max, maxs[i]);
        }
        return max;
    }

    public int findMaxOptimize(int[] arr) {
        int n = arr.length;
        // mins[i] 表示从0到i位置成绩最小值
        // 当前位置的最小值为： 前一个位置最小 * 正数  或者 前一个位置最大 * 负数
        // 当前位置的最大值为： 前一个位置最大 * 正数  或者 前一个位置最小 * 负数
        int preMin = arr[0];
        int preMax = arr[0];

        int curMin = 0;
        int curMax = 0;

        int max = arr[0];
        for (int i = 1; i < n; i++) {
            int item = arr[i];
            if (item >= 0) {
                curMin = Math.min(item, item * preMin);
                curMax = Math.max(item, item * preMax);
            } else {
                curMin = Math.min(item, preMax * item);
                curMax = Math.max(item, preMin * item);
            }
            max = Math.max(max, curMax);

            preMax = curMax;
            preMin = curMin;
        }
        return max;
    }
}


