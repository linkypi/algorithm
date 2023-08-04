package com.lynch;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/first-missing-positive/description/
 *
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/1 17:19
 */
public class FirstMissingPositive {

    @Test
    public void test() {

        int[] arr = {7,8,9,11,12};
        int result = find(arr);
        System.out.println("result: "+ result);
    }

    public int find(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] > 0 && arr[i] < n && arr[i] != i) {
                int index = arr[i] - 1;
                swap(arr, index, i);
            }
        }

        for (int i = 0; i < n; i++) {
            if (arr[i] != i) {
                return i + 1;
            }
        }
        return arr.length;
    }

    public void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
