package com.lynch;

import java.util.Arrays;

/**
 * 给你一个events数组，其中events[i] = [startDayi, endDayi, valuei]，
 * 表示第i个会议在startDayi天开始，第endDayi天结束，如果你参加这个会议，
 * 你能得到价值valuei。同时给你一个整数k表示你能参加的最多会议数目。
 *
 * 你同一时间只能参加一个会议。如果你选择参加某个会议，那么你必须 完整地参加完这个会议。
 * 会议结束日期是包含在会议内的，也就是说你不能同时参加一个开始日期与另一个结束日期相同的两个会议。
 *
 * 请你返回能得到的会议价值最大和。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxEvents2 {
    public static void main(String[] args) {

    }

    public static int find(int[][] events, int k) {
        int n = events.length;
        // dp[i][k] 表示在 i 个会议中参加 k 个会议所获得的最大价值
        // dp[i][j] 取决于当前会议参加与否:
        // 1. 参加当前会议, 则 dp[i][j] = dp[last][j-1] + val
        // 2. 不参加当前会议, 则 dp[i][j] = dp[i-1][j]
        int[][] dp = new int[n + 1][k + 1];

        Arrays.sort(events, (a, b) -> {
            return a[1] - b[1];
        });

//        dp[1][k] = events[0][2];
        for (int i = 0; i <= n; i++) {

            int[] cur = events[i];
            int start = cur[0];
            int val = cur[2];
            int last = 0;
            // 找到前一个不冲突的会议, 还可以使用二分查找
            for (int p = i - 1; p >= 0; p--) {
                if (start > events[p][1]) {
                    last = p;
                    break;
                }
            }

            for (int j = 1; j <= k; j++) {
                dp[i+1][j] = Math.max(dp[i][j], dp[last+1][j - 1] + val);
            }
        }
        return dp[n][k];
    }
}