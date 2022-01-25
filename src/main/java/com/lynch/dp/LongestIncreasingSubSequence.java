package com.lynch.dp;

/**
 * 求数组最长递增子序列的长度，注意子序列不一定是连续的
 */
public class LongestIncreasingSubSequence {
    public static void main(String[] args) {

    }

    private static int find(int[] arr) {
        int[] dp = new int[arr.length];
        for (int index = 0; index < arr.length; index++) {
            dp[index] = 1;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
