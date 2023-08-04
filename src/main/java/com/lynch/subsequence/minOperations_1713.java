package com.lynch.subsequence;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.cn/problems/minimum-operations-to-make-a-subsequence/description/
 *
 * 给你一个数组 target ，包含若干 互不相同 的整数，以及另一个整数数组 arr ，arr 可能 包含重复元素。
 * 每一次操作中，你可以在 arr 的任意位置插入任一整数。比方说，如果 arr = [1,4,1,2] ，
 * 那么你可以在中间添加 3 得到 [1,4,3,1,2] 。你可以在数组最开始或最后面添加整数。
 *
 * 请你返回 最少 操作次数，使得 target 成为 arr 的一个子序列。
 *
 * 一个数组的 子序列 指的是删除原数组的某些元素（可能一个元素都不删除），同时不改变其余元素的相对顺序得到的数组。
 * 比方说，[2,7,4] 是 [4,2,3,7,2,1,4] 的子序列（加粗元素），但 [2,4,2] 不是子序列。
 *
 * 示例 1：
 * 输入：target = [5,1,3], arr = [9,4,2,3,4]
 * 输出：2
 * 解释：你可以添加 5 和 1 ，使得 arr 变为 [5,9,4,1,2,3,4] ，target 为 arr 的子序列。
 *
 * 示例 2：
 * 输入：target = [6,4,8,1,3,2], arr = [4,7,6,2,3,8,6,1]
 * 输出：3
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/1 14:44
 */
public class minOperations_1713 {

    @Test
    public void test() {

//        int[] arr = {9, 4, 2, 3, 4};
//        int[] target = {5, 1, 3};

//        int[] arr = {4, 7, 6, 2, 3, 8, 6, 1};
//        int[] target = {6, 4, 8, 1, 3, 2};

//        int[] arr = {9, 7, 9, 2, 15, 14, 3, 8, 14, 8};
//        int[] target = {19, 15, 2, 3, 10, 6, 7, 4, 8, 14};
//
//        int[] target = {17, 5, 7, 1, 2, 19, 4, 20, 10, 18};
//        int[] arr = {2, 10, 5, 9, 9, 17, 8, 1, 19, 1};

        int[] target = {11, 16, 20, 1, 2, 13, 7, 6, 12, 3};
        int[] arr = {11, 13, 3, 7, 7, 1, 10, 12, 14, 1};

        int count = find(arr, target);
        System.out.println("count: " + count);
    }

    private int find(int[] arr, int[] target) {
        // 为使得数组 target 成为 arr 的子序列，并且操作次数最少，
        // 那么只需求得两者的最长公共序列长度 len, 然后target多出来 (target.length - len) 的数即为最少操作次数
        // 但是一般通过DP求解最长公共子序列的时间复杂度为 O(m*n), 对于10的5次方及以上的数据必定超时
        // 所以不能简单的使用最长公共子序列求解。一般的，当其中一个数组元素各不相同时，
        // 最长公共子序列 LCS 问题可以转换为最长上升子序列问题 LIS 进行求解
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length; i++) {
            map.put(target[i], i);
        }
        List<Integer> list = new ArrayList<>();
        for (int j : arr) {
            if (map.containsKey(j)) {
                list.add(map.get(j));
            }
        }
        int count = getLIS(list);
        int count2 = getLISWithBug(list);
        return target.length - count;
    }

    private int getLIS(List<Integer> list) {
        int res = 0;
        int[] buckets = new int[list.size()];
        for (int item : list) {
            int left = 0;
            int right = res;
            // 使用二分查找，找到当前数据应放入哪个桶中
            while (left < right) {
                int mid = (left + right) >> 1;
                if (item > buckets[mid]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            // 若存储的位置超出已有位置 则桶的size自增
            if (left >= res) {
                res++;
            }
            buckets[left] = item;
        }
        return res;
    }

    /**
     * 该求解方式存在 bug，提交不通过
     * @param list
     * @return
     */
    private int getLISWithBug(List<Integer> list) {
        int res = 0;
        int[] buckets = new int[list.size()];
        for (int item : list) {
            int left = 0;
            int right = res;
            // 使用二分查找，找到当前数据应放入哪个桶中
            while (left < right) {
                int mid = (left + right) >> 1;
                if (item > buckets[mid]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            // 若当前数值比当前桶的最小值要大，则新开一个桶来存放
            // 即每个桶中的元素是呈递减的顺序存放，小值覆盖大值
            if (item > buckets[left]) {
                res++;
            }
            buckets[left] = item;
        }
        return res;
    }
}
