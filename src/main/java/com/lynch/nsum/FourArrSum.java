package com.lynch.nsum;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/4sum-ii
 *
 * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
 *
 * 0 <= i, j, k, l < n
 * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
 *
 * 要求时间复杂度为 O(N2)
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/9 16:27
 */
public class FourArrSum {
    public static void main(String[] args) {

    }

    public static int count(int[] arr1, int[] arr2, int[] arr3, int[] arr4) {
        // 两数之和 以及 出现该和数的对数
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                int result = arr1[i] + arr2[j];
                if (map.containsKey(result)) {
                    map.put(result, map.get(result) + 1);
                } else {
                    map.put(result, result);
                }
            }
        }

        int rest = 0;
        for (int i = 0; i < arr3.length; i++) {
            for (int j = 0; j < arr4.length; j++) {
                int result = -(arr3[i] + arr4[j]);
                if (map.containsKey(result)) {
                    rest += map.get(result);
                }
            }
        }
        return rest;
    }
}
