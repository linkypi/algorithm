package com.lynch;

import java.util.Arrays;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/14 21:28
 */
public class ChangeCoin {
    public static void main(String[] args) {
        int[] arr = {1, 2, 5};
        int target = 5;
        int minWays = findMin(target, 0, arr);

        int[][] dp = new int[target + 1][arr.length];
        for (int i = 0; i < target + 1; i++) {
            Arrays.fill(dp[i], -1);
        }
        int minWithMem = findMinWithMem(target, 0, arr, dp);
        int minWithDp = findMinWithDp(target, arr);
        System.out.println("min count: " + minWays);
    }

    static int findMin(int amount , int index, int [] arr) {

        if (amount == 0) {
            return 0;
        }

        if (amount < 0 || index > arr.length - 1) {
            return -1;
        }
        int count = Integer.MAX_VALUE;
        for (int i = 0; i * arr[index] <= amount; i++) {
            int rest = amount - arr[index] * i;
            int use = findMin(rest, index + 1, arr);
            if (use != -1 && use != Integer.MAX_VALUE) {
                count = Math.min(count, use + i);
            }
        }
        return count;
    }

    static int findMinWithMem(int amount , int index, int [] arr, int[][] dp) {
        if (amount == 0) {
            return 0;
        }

        if (amount < 0 || index > arr.length - 1) {
            return -1;
        }
        if (dp[amount][index] != -1) {
            return dp[amount][index];
        }
        int count = Integer.MAX_VALUE;
        for (int i = 0; i * arr[index] <= amount; i++) {
            int rest = amount - arr[index] * i;
            int use = findMinWithMem(rest, index + 1, arr, dp);
            if (use != -1 && use != Integer.MAX_VALUE) {
                count = Math.min(count, use + i);
            }
        }
        dp[amount][index] = count;
        return count;
    }

    static int findMinWithDp(int amount ,int [] arr) {
        int n = arr.length;
        int[][] dp = new int[n][amount + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        for (int i = 0; i < n; i++) {
            dp[i][arr[i]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= amount; j++) {

                for (int piece = 0; piece * arr[i] <= j; piece++) {
                    int rest = j - piece * arr[i];
                    if (rest >= 0) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][rest] + piece);
                    }
                }
            }
        }
        return dp[n - 1][amount];
    }
}
