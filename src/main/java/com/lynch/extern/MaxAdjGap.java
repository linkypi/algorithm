package com.lynch.extern;

/**
 * 给定一个数组，求数组排序后，相邻两数的最大差值
 * 要求时间复杂度 O(N)，且要求不能用非基于比较的排序
 */
public class MaxAdjGap {

    public static void main(String[] args) {

    }

    private static int getMaxGap(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        if (min == max) {
            return 0;
        }

        // 使用n+1个桶来平均存放数组的所有元素，其中每个桶仅仅需要记录该桶的最大值及最小值
        // 当前桶的最大值与下一个桶之间的差值即为相邻元素间的差值，把所有差值都比较一遍即可得到最大差值
        int n = arr.length;
        boolean[] hasNums = new boolean[n + 1];
        int[] mins = new int[n + 1];
        int[] maxs = new int[n + 1];
        int bucketSize = (max - min) / (n + 1);
        for (int i = 0; i < n; i++) {
            int index = (arr[i] - min) / bucketSize;
            mins[index] = hasNums[i] ? Math.min(mins[index], arr[i]) : arr[i];
            maxs[index] = hasNums[i] ? Math.max(maxs[index], arr[i]) : arr[i];
            hasNums[i] = true;
        }

        int result = 0;
        int lastMax = maxs[0];
        for (int i = 1; i < n; i++) {
            if (hasNums[i]) {
                result = Math.max(result, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return result;
    }


}
