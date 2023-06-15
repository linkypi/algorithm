package com.lynch;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/5/9 14:35
 */
public class NSum {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        int[] arr2 = {3,4,7,2,-3,1,4,2};
//        int count = find(arr, 3);
        int count = find(arr2, 7);
        System.out.println("count: "+ count);
    }

    /**
     * 在数组 arr 中寻找连续元素和为 k 的子数组个数
     * @param arr
     * @param k
     * @return
     */
    private static int find(int[] arr, int k) {
        // 记录前缀和 与 其出现的个数
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int preSum = 0;
        int count = 0;
        for (int item : arr) {
            preSum += item;

            if (map.containsKey(preSum - k)) {
                count += map.get(preSum - k);
            }
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}
