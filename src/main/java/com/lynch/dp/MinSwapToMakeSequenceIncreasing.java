package com.lynch.dp;

/**
 * https://leetcode.cn/problems/minimum-swaps-to-make-sequences-increasing/
 *
 * 我们有两个长度相等且不为空的整型数组 nums1 和 nums2 。在一次操作中，我们可以交换 nums1[i] 和 nums2[i] 的元素。
 * 例如，如果 nums1 = [1,2,3,8] ， nums2 =[5,6,7,4] ，你可以交换 i = 3 处的元素，得到 nums1 =[1,2,3,4] 和 nums2 =[5,6,7,8] 。
 * 返回 使 nums1 和 nums2 严格递增 所需操作的最小次数 。
 *
 * 数组 arr 严格递增 且 arr[0] < arr[1] < arr[2] < ... < arr[arr.length - 1] 。
 *
 * 注意：
 * 用例保证可以实现操作
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/8 18:06
 */
public class MinSwapToMakeSequenceIncreasing {
    public static void main(String[] args) {

    }

    public static int minSwap(int[] nums1, int[] nums2) {
        int n = nums2.length;
        // dp[i][j] 表示数组 i 个位置在j状态下（j=0 表示不交换，j=1 表示交换）的满足两个数组严格递增的最小操作次数
        int[][] dp = new int[n][2];

        dp[0][1] = 1;
        for (int i = 1; i < n; i++) {
            int a1 = nums1[i - 1], a2 = nums1[i];
            int b1 = nums2[i - 1], b2 = nums2[i];
            // 本身已经严格递增，那么两边要不都交换，要么都不交换
            if (a1 < a2 && b1 < b2) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] + 1;
            }

            // 交叉情况 ，i-1 及 i 位置仅需要交换其中之一即可
            if (a1 < b2 && b1 < a2) {
                // 不交换当前位置的最小操作次数即为 前一个位置交换本次不交换
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]);
                // 交换当前位置的最小操作次数为 前一个位置不交换，本次交换
                dp[i][1] = Math.min(dp[i][1], dp[i - 1][0] + 1);
            }
        }
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }

}
