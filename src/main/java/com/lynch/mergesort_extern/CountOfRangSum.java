package com.lynch.mergesort_extern;

/**
 * 给定一个数组arr, 两个整数 lower 及 upper,
 * 返回arr中有多少个子数组的累加和在 [lower, upper] 范围上
 *
 * 假设 0...i 位置上的整体累加和为 X ，则有：
 *   X = sum(n-1) = sum(i) + sum(i...n-1)
 * 换个角度考虑：
 *   sum(i) = X - sum(i...n-1)
 * 假设 0...i 位置上的整体累加和是 X ，求必须以 i 位置结尾
 * 的子数组有多少个在 [lower, upper] 范围内  等同于 求 i 位置
 * 之前的所有前缀和中有多少个前缀和在 [X-upper, X-lower] 范围内
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/20 11:04
 */
public class CountOfRangSum {
    public static void main(String[] args) {

    }

    private static int find(int[] arr, int lower, int upper) {

        int n = arr.length;
        int[] sum = new int[n];
        sum[0] = arr[0];
        for (int i = 1; i < n; i++) {
            sum[i] += sum[i - 1];
        }
        return count(sum, 0, n - 1, lower, upper);
    }

    private static int count(int[] sum, int start, int end, int lower, int upper) {
        if (start == end) {
            if (sum[start] >= lower && sum[end] <= upper) {
                return 1;
            }
            return 0;
        }

        int mid = (start + end) >> 1;
        int left = count(sum, start, mid, lower, upper);
        int right = count(sum, mid + 1, upper, lower, upper);
        int mergeCount = merge(sum, start, mid, end, lower, upper);
        return left + right + mergeCount;
    }

    private static int merge(int[] sum, int start, int mid, int end, int lower, int upper) {

        int count = 0;
        int leftWindow = start;
        int rightWindow = start;
        for (int i = mid + 1; i <= end; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            // 在左侧部署使用滑动窗口来寻找有效位置

            // 1. 若窗口右侧的值小于等于max 则不断右移右指针扩大窗口
            while (leftWindow <= mid && sum[rightWindow] <= max) {
                rightWindow++;
            }
            // 2. 若窗口左侧的值小于min 则不断右移左指针缩小窗口
            while (rightWindow <= mid && sum[leftWindow] < min) {
                leftWindow++;
            }
            // 最后有效个数即是 right - left
            count += Math.max(0, rightWindow - leftWindow);
        }

        int left = start;
        int right = mid;
        int index = 0;

        int[] temp = new int[end - start + 1];
        while (left <= mid && right <= end) {
            if (sum[left] > sum[right]) {
                temp[index++] = sum[right++];
            } else {
                temp[index++] = sum[left++];
            }
        }

        while (left <= mid) {
            temp[index++] = sum[left++];
        }
        while (right <= end) {
            temp[index++] = sum[right++];
        }
        while (start <= end) {
            sum[start] = temp[end - start - index];
            start++;
        }
        return count;
    }
}
