package com.lynch.nsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *  给定一个包含 n 个整数的数组 nums，及一个目标值 target,
 *  判断 nums 中是否存在三个元素 a, b ,c, d
 *  使得 a+b+c+d=target, 请找出所有满足条件的且不重复的四元组
 *
 *  四数之和可以从三数之和来求解
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/7 16:23
 */
public class FourSum {
    public static void main(String[] args) {
         int[] arr = {1,0,-1,0,-2, 2};
        List<List<Integer>> lists = fourSum(arr, 0);
        System.out.println("xxx");
    }

    private static List<List<Integer>> fourSum(int[] arr, int target) {
        if (arr == null || arr.length < 4) {
            return new ArrayList<>();
        }
        Arrays.sort(arr);
        if (arr[0] > target) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            // 由于数组已排序，所以针对相同的数无需重复计算
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            List<List<Integer>> threeSum = threeSum(arr, target - arr[i], i + 1);
            for (List<Integer> item : threeSum) {
                item.add(arr[i]);
            }
            result.addAll(threeSum);
        }
        return result;
    }

    public static List<List<Integer>> threeSum(int[] arr, int target, int start) {

        List<List<Integer>> result = new ArrayList<>();
        for (int i = start; i < arr.length; i++) {
            // 由于数组已排序，所以针对相同的数无需重复计算
            if (i > start && arr[i] == arr[i - 1]) {
                continue;
            }

            List<List<Integer>> twoSum = findTwoSum(arr, target - arr[i], i + 1);
            for (List<Integer> item : twoSum) {
                item.add(arr[i]);
            }
            result.addAll(twoSum);
        }
        return result;
    }

    public static List<List<Integer>> findTwoSum(int[] arr, int target, int start) {
        int low = start;
        int high = arr.length - 1;
        List<List<Integer>> list = new ArrayList<>();
        while (low < high) {
            int left = arr[low];
            int right = arr[high];
            int sum = left + right;

            if (sum == target) {
                List<Integer> objects = new ArrayList<>();
                objects.add(arr[low]);
                objects.add(arr[high]);
                list.add(objects);

                // 若前一个遇到重复的值则跳过继续往前
                while (high > low && arr[--high] == right) {
                    high--;
                }
                // 若后一个遇到重复的值则跳过继续往后
                while (high > low && arr[++low] == left) {
                    low++;
                }
            }
            if (sum > target) {
                high--;
                // 若遇到重复的值则跳过继续往前
                while (high > low && arr[high] == right) {
                    high--;
                }
            }
            if (sum < target) {
                low++;
                // 若遇到重复的值则跳过继续往后
                while (high > low && arr[low] == left) {
                    low++;
                }
            }
        }
        return list;
    }
}
