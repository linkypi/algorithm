package com.lynch.dp;

import java.util.Arrays;

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
        int[][] arr = {{1, 2, 4}, {3, 4, 3}, {2, 3, 1}};
//        int value = maxValue(arr, 2);
        int value = findMaxValue(arr, 2);
        System.out.println("max value: " + value);
    }

    public static int maxValue(int[][] events, int k) {
        int n = events.length;
        // max[i][k] 表示前 i 个事件，参会次数不超过 k 的最大值价值
        int[][] max = new int[n + 1][k + 1];
        // 按结束时间排序
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        for (int i = 1; i <= events.length; i++) {
            int[] event = events[i-1];
            int start = event[0], value = event[2];

            // 找到之前一个不冲突的事件, 即当前事件的开始时间要晚于之前事件的结束时间
            int last = 0;
            for (int p = i - 1; p >= 1; p--) {
                // 此处 p 需要多减一次 1 ，因为 i - 1 索引对应的是当前事件，此处需要从前一个事件开始寻找
                int end = events[p-1][1];
                if (end < start) {
                    last = p;
                    break;
                }
            }

            // 当前事件 i 的最大价值等于之前事件i，参会次数不超过 k 的最大价值
            // 每个事件 i 可以有两种选择：
            // 1. 不选择 i 事件，则最大价值即为前一个事件 i-1 的价值 max[i-1][j]
            // 2. 选择 i 事件，则最大价值为前一个事件 i-1 的价值 与 当前价值 value 的和： max[last][j-1] + value
            for (int j = 1; j <= k; j++) {
                max[i][j] = Math.max(max[i - 1][j], max[last][j - 1] + value);
            }

        }
        return max[n][k];
    }

    public static int findMaxValue(int[][] events, int k) {
        int n = events.length;
        // max[n][k] 表示 n 个事件在允许最多参加 k 个时可以获取到的最大价值
        // 第 n 个会议的最大价值取决于两种情况：参加第n个会议 或者 不参加第n个会议
        // 1. 若参加第n个会议，则最大价值为前一个不冲突会议last的最大价值 与 当前会议价值的总和： max[last][k-1] + value
        // 2. 若不参见第n个会议，则最大价值为前一个会议n-1的最大价值 max[n-1][k]
        // 综上，max[n][k] = max(max[last][k-1] + value, max[n-1][k])
        int[][] max = new int[n + 1][k + 1];

        // 对每个会议的结束时间按升序排序
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        for (int i = 1; i <= n; i++) {
            int[] event = events[i - 1];
            int start = event[0], value = event[2];

            // 找到前一个不冲突的会议，通过二分查找到前一个不冲突的会议
            int left = 0, right = i - 1;
            while (left < right) {
                int mid = (left + right + 1) >> 1;
                int end = events[mid][1];
                // 若mid的结束时间在当前会议开始时间之后，
                // 则只能从前半部分查询，可直接跳过mid, 因为这部分不符合要求，故有 mid-1
                if (end > start) {
                    right = mid - 1;
                } else {
                    // 否则从后半部分查询
                    left = mid;
                }
            }

            // 二分的最终结果应该是刚好与当前会议不冲突的上一个会议
            int last = right > 0 && events[right - 1][1] < start ? right : 0;

            for (int j = 1; j <= k; j++) {
                max[i][j] = Math.max(max[i - 1][j], max[last][j - 1] + value);
            }
        }
        return max[n][k];
    }
}
