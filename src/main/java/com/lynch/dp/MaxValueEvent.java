package com.lynch.dp;

/**
 * https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/
 *
 * 给你一个events数组，其中events[i] = [startDayi, endDayi, valuei]，表示第i个会议在startDayi天开始，
 * 第endDayi天结束，如果你参加这个会议，你能得到价值valuei。同时给你一个整数k表示你能参加的最多会议数目。
 *
 * 你同一时间只能参加一个会议。如果你选择参加某个会议，那么你必须 完整地参加完这个会议。会议结束日期是包含
 * 在会议内的，也就是说你不能同时参加一个开始日期与另一个结束日期相同的两个会议。
 * 请你返回能得到的会议价值最大和。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/10 17:15
 */
public class MaxValueEvent {
    public static void main(String[] args) {

    }

    public static int maxValue(int[][] events, int k) {
        int n = events.length;
        // max[i][j] 表示前i个事件，价值不超过j的最大值价值
        int[][] max = new int[n + 1][k + 1];

        for (int i = 1; i < events.length; i++) {
            int[] event = events[i];
            int start = event[0], end = event[1], value = event[2];

            // 找到之前一个不冲突的事件
            int last = 0;
            for (int x = i - 1; x >= 1; x--) {
                int pre = events[x][1];
                if (pre < start) {
                    last = pre;
                    break;
                }
            }

            // 当前事件i的最大价值等于之前事件i，不超过j的最大价值
            // 每个事件i可以有两种选择：
            // 1. 不选择 i 事件，则最大价值为 max[i-1][j]
            // 2. 选择 i 事件，则最大价值为max[last][j-1]
            for (int j = 1; j <= k; j++) {
                max[i][j] = Math.max(max[i - 1][j], max[last][j - 1] + value);
            }

        }
        return max[n][k];
    }
}
