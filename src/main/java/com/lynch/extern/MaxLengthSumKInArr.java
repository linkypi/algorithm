package com.lynch.extern;

import java.util.HashMap;

/**
 * 给定一个数组, 求累加和为 K 的最长子数组长度
 */
public class MaxLengthSumKInArr {
    public static void main(String[] args) {
        // 求正数数组中累加和为 3 的最长子数组长度
        int[] arr = {1, 2, 1, 3, 2, 1, 1, 1};
        System.out.println(getMaxLengthSumKInPositiveArr(arr, 3));

        // 求数组中累加和为 3 的最长子数组长度, 数组元素可正, 可负, 可 0
        int[] arr2 = {1, 2, 0, -3, 5, -2, 4, 6};
        System.out.println(getMaxLengthSumKInArr(arr2, 3));

        // 求小于等于 10 的最长子数组长度长度, 数组元素可正, 可负, 可 0
        int[] arr3 = {1, 2, 5, -3, 5, -2, 4, 6};
        System.out.println(getMaxLengthLOETSumKInArr(arr3, 10));

        int[] arr4 = {1, 9, 5, -3, 5, -2, 4, 6};
        System.out.println(getMaxLengthLOETSumKInArr(arr4, 10));
    }

    /**
     * 给定一个无序数组, 每个数都为正数, 求数组累加和为 K 的最长子数组长度
     * 使用左右指针实现的滑动窗口来求值
     * @param arr
     * @return
     */
    public static int getMaxLengthSumKInPositiveArr(int[] arr, int k) {

        int max = 0, sum = 0;
        int left =0 , right = 0;
        while(right<arr.length){
            if (sum + arr[right] < k) {
                sum += arr[right];
                right++;
            } else if (sum + arr[right] == k) {
                sum = k;
                max = Math.max(max, right - left + 1);
                right ++;
            } else {
                sum -= arr[left];
                left++;
            }
        }
        return max;
    }

    /**
     * 给定一个无序数组, 每个数都可正, 可负, 可 0, 求数组累加和为 K 的最长子数组长度
     *
     * @param arr
     * @return
     */
    public static int getMaxLengthSumKInArr(int[] arr, int k){
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // important 代表一个数都没有的境况下，
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k)){
                len = Math.max(i - map.get(sum - k), len);
            }
            if (!map.containsKey(sum)){
                map.put(sum, i);
            }
        }
        return len;
    }

    /**
     * 给定一个无序数组, 每个数都可正, 可负, 可 0, 求数组累加和小于等于 K 的最长子数组长度
     *
     * @param arr
     * @return
     */
    public static int getMaxLengthLOETSumKInArr(int[] arr, int k) {
        int N = arr.length;
        // i 位置上的累加和最小值
        int[] minSum = new int[N];
        // 求得 i 位置上累加和最小值时的最右边界
        int[] minSumEnds = new int[N];

        minSum[N - 1] = arr[N - 1];
        minSumEnds[N - 1] = N - 1;
        for (int index = N - 2; index >= 0; index--) {
            // 后面一个数小于 0 则说明可以将后一个数并入子数字使得累加和更小
            if (arr[index + 1] < 0) {
                minSum[index] = minSum[index + 1] + arr[index];
                // 实际右边界即为下一个位置所能达到的右边界
                minSumEnds[index] = minSumEnds[index + 1];
            } else {
                // 否则说明当前值已经足够小
                minSum[index] = arr[index];
                minSumEnds[index] = index;
            }
        }
        int sum = 0, end = 0;
        int maxLen = 0;

        for (int i = 0; i < arr.length; i++) {
            // 仅可能向右边扩充, 直到刚好小于 K
            while (end < arr.length && sum + minSum[end] <= k) {
                sum += minSum[end];
                end = minSumEnds[end] + 1;
            }
            // 记录最长子数组长度
            maxLen = Math.max(maxLen, end - i);

            if (end > i) {
                // 窗口还有元素 此时以 i 位置开始的最长子数组已经无法再扩充,
                // 只能将最左边位置 i 的元素舍弃, 相当于缩小窗口范围
                sum -= arr[i];
            } else {
                // 窗口已经没有元素, 说明以 i 开头的所有子数组累加和都不可能 <= K
                end = i + 1;
            }
        }
        return maxLen;
    }
}
