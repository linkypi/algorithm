package com.lynch.nsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 给定一个包含 n 个整数的数组 nums，及一个目标值 target,
 * 判断 nums 中是否存在三个元素 a, b ,c,
 * 使得 a+b+c=target, 请找出所有满足条件的且不重复的三元组
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/7 14:59
 */
public class ThreeSum {
    public static void main(String[] args) {
         int[] arr = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> lists = find(arr, 0);
        List<List<Integer>> lists2 = threeSum(arr);
        System.out.println("result: " + lists.size());
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        //定义一个结果集
        List<List<Integer>> res = new ArrayList<>();
        //数组的长度
        int len = nums.length;
        //当前数组的长度为空，或者长度小于3时，直接退出
        if (nums == null || len < 3) {
            return res;
        }
        //将数组进行排序
        Arrays.sort(nums);
        //遍历数组中的每一个元素
        for (int i = 0; i < len; i++) {
            //如果遍历的起始元素大于0，就直接退出
            //原因，此时数组为有序的数组，最小的数都大于0了，三数之和肯定大于0
            if (nums[i] > 0) {
                break;
            }
            //去重，当起始的值等于前一个元素，那么得到的结果将会和前一次相同
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1;
            int r = len - 1;
            //当 l 不等于 r时就继续遍历
            while (l < r) {
                //将三数进行相加
                int sum = nums[i] + nums[l] + nums[r];
                //如果等于0，将结果对应的索引位置的值加入结果集中
                if (sum == 0) {
                    // 将三数的结果集加入到结果集中
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    //在将左指针和右指针移动的时候，先对左右指针的值，进行判断
                    //如果重复，直接跳过。
                    //去重，因为 i 不变，当此时 l取的数的值与前一个数相同，所以不用在计算，直接跳
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    //去重，因为 i不变，当此时 r 取的数的值与前一个相同，所以不用在计算
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    //将 左指针右移，将右指针左移。
                    l++;
                    r--;
                    //如果结果小于0，将左指针右移
                } else if (sum < 0) {
                    l++;
                    //如果结果大于0，将右指针左移
                } else if (sum > 0) {
                    r--;
                }
            }
        }
        return res;
    }

    public static List<List<Integer>> find(int[] arr, int target) {

        if (arr == null || arr.length < 3) {
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
