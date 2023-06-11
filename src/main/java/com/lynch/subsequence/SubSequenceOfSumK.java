package com.lynch.subsequence;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个数组，求其中和为 k 的连续子数组个数
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/29 16:54
 */
public class SubSequenceOfSumK {
    public static void main(String[] args) {
        int[] arr = {5, 1, 4, 6, 2, 3, 7};
        int result = find(arr, 5);
        System.out.println("result: " + result);
    }

    private static int find(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 根据前缀和可知： sum[i] == sum[i-1] + arr[i]
        // 同理可以知道： sum[i] == sum[j] + k (其中 k 可以有多个连续元素组成)
        //   交换位置后： sum[j] == sum[i] - k 即可以理解为以和为k的连续子数组的个数
        // 可以理解为有有多少个前缀和为 sum[i] - k 的sum[j]即可
        Map<Integer, Integer> cache = new HashMap<>();
        // 基础条件不可少，否则对于单个元素满足k的将不会计算在内
        cache.put(0, 1);

        int sum = 0;
        int count = 0;
        for (int item : arr) {
            sum += item;

            int res = sum - k;
            if (cache.containsKey(res)) {
                count += cache.get(res);
            }
            cache.put(sum, cache.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
