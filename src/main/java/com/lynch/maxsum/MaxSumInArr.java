package com.lynch.maxsum;

public class MaxSumInArr {
    public static void main(String[] args) {

        int[] arr = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println("result : "+ getMaxSum(arr));
        System.out.println("result2 : "+ getMaxSumWithDP(arr));
    }

    /**
     * 输入一个整型数组，数组里有正数也有负数。数组中一个或连续的多个整数
     * 组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为 O（n）。
     *
     * @param arr
     * @return
     */
    public static int getMaxSum(int[] arr) {

        int current = 0, sum = 0;
        for (int index = 0; index < arr.length; index++) {
            if (current <= 0) {
                current = arr[index];
            } else {
                current += arr[index];
            }
            if (current > sum)
                sum = current;
        }
        return sum;
    }

    public static int getMaxSumWithDP(int[] arr) {
        // 定义 dp 数组，dp[i]表示到数组i为止，当前最大的子数组和
        int[] dp = new int[arr.length];
        dp[0] = arr[0];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < arr.length; i++) {
            // 若在第i个数时子数组累加和已经小于当前值，则舍弃之前的累加和从当前值开始累加
            dp[i] = Math.max(dp[i - 1] + arr[i], arr[i]);
            max = Math.max(dp[i], max);
        }

        return max;
    }

}
