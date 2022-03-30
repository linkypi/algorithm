package com.lynch;

import java.util.HashMap;

/**
 * 给定一个数组，请找出和为 k 的连续子数组个数
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/29 16:31
 */
public class Test {
    public static void main(String[] args) {
        int[] arr = {5, 1, 4, 6, 2, 3, 7};
        int result = subarraySum(arr, 5);
        System.out.println("result: " + result);
    }

    private static int subarraySum(int[] nums, int k) {
        int n = nums.length;
        // map：前缀和 -> 该前缀和出现的次数
        HashMap<Integer, Integer> preSum = new HashMap<>();
        // base case
        preSum.put(0, 1);
        int res = 0, sum0_i = 0;
        for (int i = 0; i < n; i++) {
            sum0_i += nums[i];
            // 这是我们想找的前缀和 nums[0..j]
            int sum0_j = sum0_i - k;
            // 如果前⾯有这个前缀和，则直接更新答案
            if (preSum.containsKey(sum0_j))
                res += preSum.get(sum0_j);
            // 把前缀和 nums[0..i] 加⼊并记录出现次数
            preSum.put(sum0_i, preSum.getOrDefault(sum0_i, 0) + 1);
        }
        return res;
    }
}
