package com.lynch;

import java.util.Arrays;

/**
 * 给你一个events数组，其中events[i] = [startDayi, endDayi, valuei]，
 * 表示第i个会议在startDayi天开始，第endDayi天结束，如果你参加这个会议，你能得到价值valuei。
 * 同时给你一个整数k表示你能参加的最多会议数目。
 * 你同一时间只能参加一个会议。如果你选择参加某个会议，那么你必须 完整地参加完这个会议。
 * 会议结束日期是包含在会议内的，也就是说你不能同时参加一个开始日期与另一个结束日期相同的两个会议。
 * 请你返回能得到的会议价值最大和。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/11 8:51
 */
public class EventMaxValue {
    public static void main(String[] args) {
//        int[][] arr = {{1, 2, 4}, {3, 4, 3}, {2, 3, 10}};
//        int[][] arr = {{1, 2, 4}, {3, 4, 3}, {2, 3, 1}};
//        int k = 2;
        int[][] arr = {{1, 3, 4}, {2, 4, 1}, {1, 1, 4}, {3, 5, 1}, {2, 5, 5}};
        int k = 3;
        int maxValue = maxValue(arr, k);
        int withDp = findWithDp(arr, k);
        System.out.println("max value: " + maxValue);
    }

    private static int maxValue(int[][] events, int k) {
        // 为了可以参加更多的会议 那就尽量参加早点结束的会议， 所以按会议开始时间升序排列
        Arrays.sort(events, (a,b)->a[0] - b[0]);
        return find(events, 0, k);
    }

    static int find(int[][] events, int current, int k) {
        if (current > events.length - 1 || k <= 0) {
            return 0;
        }
        int next = 0;
        // 找出下一个与 i 会议时间不冲突的会议参加
        for (next = current + 1; next < events.length; next++) {
            if (events[next][0] > events[current][1]) {
                break;
            }
        }
        // 若参加当前会议，则最大价值是当前会议价值 加上 后面可参加会议的最大价值
        int select = events[current][2] + find(events, next, k - 1);
        // 若不参加当前会议，则最大价值由后面的会议决定
        int unSelect = find(events, current + 1, k);
        return Math.max(select, unSelect);
    }

    static int findWithDp(int[][] events, int k) {
        int n = events.length;
        // dp[i][j] 从第i个会议开始 可参加k个会议的情况下可获得的最大价值
        int[][] dp = new int[n+1][k + 1];

        for(int i = 0; i <n; i++){
            dp[i][0] = 0;
        }

//        for(int j = 0; j <=k; j++){
//            dp[0][j] = events[0][2];
//        }

        dp[0][1] = events[0][2];
        Arrays.sort(events, (a, b) -> a[1] - b[1]);
        for (int j = 1; j <= k; j++) {
            for (int i = n-1; i > 0; i--) {
                int last = 0;
                // 找出上一个结束时间在当前会议开始之前的
                for (last = 0; last < i; last++) {
                    if (events[last][1] < events[i][0]) {
                        break;
                    }
                }

                // 若参加当前会议，则最大价值是当前会议价值 加上 后面可参加会议的最大价值
                int max = events[i][2] + dp[last][j - 1];
                // 若不参加当前会议，则最大价值由后面的会议决定
                if (i + 1 <= n - 1) {
                    max = Math.max(max, dp[i + 1][j]);
                }
                dp[i][j] = Math.max(dp[i][j], max);
            }
        }
        return dp[0][k];
    }
}
