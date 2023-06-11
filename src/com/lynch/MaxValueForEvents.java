package com.lynch;

import java.util.Arrays;

/**
 * 给你一个 events 数组，其中 events[i] = [startDayi, endDayi, valuei] ，
 * 表示第 i 个会议在 startDayi 天开始，第 endDayi 天结束，如果你参加这个会议，
 * 你能得到价值 valuei 。同时给你一个整数 k 表示你能参加的最多会议数目。
 *
 * 你同一时间只能参加一个会议。如果你选择参加某个会议，那么你必须 完整 地参加完这个会议。
 * 会议结束日期是包含在会议内的，也就是说你不能同时参加一个开始日期与另一个结束日期相同的两个会议。
 *
 * 请你返回能得到的会议价值 最大和 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxValueForEvents {
    public static void main(String[] args) {
//        int[][] events = {{1, 2, 4}, {3, 4, 3}, {2, 3, 1}};
//        int k = 2;

//        int[][] events = {{1,2,4},{3,4,3},{2,3,10}};
//        int k = 2;
        int[][] events = {{21, 77, 43}, {2, 74, 47},
                {6, 59, 22}, {47, 47, 38},
                {13, 74, 57}, {27, 55, 27},
                {8, 15, 8}};
        int k = 4;
//        int result = maxValue(events, k);
        int result = find(events, k);
        System.out.println("result: " + result);
    }

    public static int find(int[][] events, int k) {
        // 按照结束时间排序
        Arrays.sort(events, (a, b) -> {
            return a[1] - b[1];
        });
        int n = events.length;
        // dp[i][j] 表示前 i 个会议中最多参加 j 个会议所获得的最大价值
        // dp[i][j] 的最大价值取决于是否参加当前会议 i
        // 1. 参加当前会议 i 所获得最大价值取决于前一个不冲突的会议所获得的最大价值 x
        //    即 dp[i][j] = dp[x][j-1] + events[i][2]
        // 2. 不参加当前会议 i 则 dp[i][j] = dp[i-1][j]
        // 最终 dp[i][j] 在两者中取最大即可
        int[][] dp = new int[n][k + 1];

        // 第一个的会议无论参加多少次 其获得的最大价值都是 events[0][2]
        for (int j = 1; j <= k; j++) {
            dp[0][j] = events[0][2];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                // 找打前一个不冲突的会议
                int pre = -1;
                for (int x = i - 1; x > -1; x--) {
                    // 前一个会议结束时间在当前会议开始时间之前
                    if (events[x][1] < events[i][0]) {
                        pre = x;
                        break;
                    }
                }
                // 若前一个会议找不到, 则价值可从当前会议获得
                int preValue = pre == -1 ? events[i][2] : (dp[pre][j - 1] + events[i][2]);
                // 参加当前会议 i 所获得最大价值取决于参加当前会议与不参加当前会议中的最大值
                dp[i][j] = Math.max(preValue, dp[i - 1][j]);
            }
        }
        return dp[n - 1][k];
    }

    public static int maxValue(int[][] es, int k) {
        int n = es.length;
        Arrays.sort(es, (a, b)->a[1]-b[1]);
        // f[i][j] 代表考虑前 i 个事件，选择不超过 j 的最大价值
        int[][] f = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            int[] ev = es[i - 1];
            int s = ev[0], e = ev[1], v = ev[2];

            // 找到第 i 件事件之前，与第 i 件事件不冲突的事件
            // 对于当前事件而言，冲突与否，与 j 无关
            int last = 0;
            for (int p = i - 1; p >= 1; p--) {
                int[] prev = es[p - 1];
                if (prev[1] < s) {
                    last = p;
                    break;
                }
            }

            for (int j = 1; j <= k; j++) {
                f[i][j] = Math.max(f[i - 1][j], f[last][j - 1] + v);
            }
        }
        return f[n][k];
    }
}
