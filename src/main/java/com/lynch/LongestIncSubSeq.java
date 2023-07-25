package com.lynch;

/**
 * 求最长递增子序列的长度, 要求严格递增
 */
public class LongestIncSubSeq {
    public static void main(String[] args) {
        int[] arr = {10,9,2,5,3,7,101,18};
        int count = find(arr);
        System.out.println("count: "+ count);
    }

    public static int find(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        // dp[i] 表示 第 i 个位置处最长递增子序列长度
        // dp[i] 取决于 arr[i] 与 arr[i-1] 以及 dp[i-1]
        // 1. 若arr[i] > arr[i-1], 则 dp[i] = dp[i-1] + 1
        // 2. 若arr[i] <= arr[i-1] 则 dp[i] = dp[i-1]
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            // 固定 i 位置后就可以从头开始遍历 0-i 位置的最长递增序列长度
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
