package com.lynch.extern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/maximum-number-of-events-that-can-be-attended-ii/
 *
 * 给你一个events数组，其中events[i] = [startDayi, endDayi, valuei]，表示第i个会议在startDayi天开始，
 * 第endDayi天结束，如果你参加这个会议，你能得到价值valuei。同时给你一个整数k表示你能参加的最多会议数目。
 * 你同一时间只能参加一个会议。如果你选择参加某个会议，那么你必须 完整地参加完这个会议。会议结束日期是
 * 包含在会议内的，也就是说你不能同时参加一个开始日期与另一个结束日期相同的两个会议。
 *
 * 请你返回能得到的会议价值最大和。

 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/9 10:41
 */
public class MaxEventValue {

    public static void main(String[] args) {
        int[][] events = {
//            {1, 2, 4}, {3, 4, 3}, {2, 3, 1}
//                {1, 2, 4}, {3, 4, 3}, {2, 3, 10}
                {1, 3, 4}, {2, 4, 1}, {1, 1, 4}, {3, 5, 1}, {2, 5, 5}
        };
        int maxValue = maxValue(events, 2);
        int maxValue3 = maxValueWithRecursive(events, 2);
        int maxValueWithDp = maxValueWithDp(events, 2);
        System.out.println("max: " + maxValue);
    }

    static int maxValueWithRecursive(int[][] events, int k){
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        return find(events, 0, k);
    }

    static int find(int[][] events , int curr, int k) {
        if (k <= 0 || curr >= events.length) {
            return 0;
        }
        //找出可以参见的下一个会议
        int next = 0;
        for (int i = curr + 1; i < events.length; i++) {
            next = i;
            if (events[i][0] > events[curr][1]) {
                break;
            }
        }
        // 参加当前会议
        int p1 = find(events, next, k - 1) + events[curr][2];

        // 不参加当前会议
        int p2 = find(events, curr + 1, k);
        return Math.max(p1, p2);
    }

    static int maxValueWithDp(int[][] events, int k) {
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        int n = events.length;

        //dp[i][j]表示前 i 个会议中，最多只能参加 j 个会议，所能获取到的最大价值
        int[][] dp = new int[n + 1][k + 1];

        dp[0][1] = events[0][2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                // 不参加当前会议，其当前所能获得的最大价值来自前者
                dp[i][j] = dp[i - 1][j];

                // 从 0 到 i-1 的会议中选择与当前会议不重合 即 会议结束时间在 会议i的开始时间之前的
//                int left = 0;
//                int right = i-1;
//                while (left < right) {
//                    int mid = ( right - left + 1) / 2;
//                    if (events[mid][1] < events[i][0]) {
//                        left = mid;
//                    } else {
//                        right = mid - 1;
//                    }
//                }

                //二分找最靠近i的且其结束时间比i的开始时间小的那个会议，返回l这个会议下标
                int l = 0, r = i - 1;
                while (l < r) {
                    int m = l + (r - l + 1) / 2;//取右中位数
                    if (events[m][1] < events[i][0]) l = m;
                    else r = m - 1;//右动
                }


                System.out.println( " left: " + l);
                int can = dp[l][j - 1] + events[i][2];
                dp[i][j] = Math.max(dp[i][j], can);
            }
        }
        return dp[n][k];
    }


    public static int maxValue(int[][] events, int k) {
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        int end = events[0][1];
        List<int[]> availables = new ArrayList<>();
        List<Integer> un = new ArrayList<>();

        availables.add(events[0]);
        for (int i = 1; i < events.length; i++) {
            if (events[i][0] > end) {
                end = events[i][1];
                availables.add(events[i]);
            } else {
                un.add(i);
            }
        }

        // 有冲突的会议只能选择一个，选出冲突中最大价值那个参加
        int max = 0;
        for (int i : un) {
            max = Math.max(max, events[i][2]);
        }

        // 对无冲突会议按照价值降序排序
        availables.sort((a, b) -> b[2] - a[2]);
        int maxValue = 0;
        for (int i = 0; i < k && i < availables.size(); i++) {
            maxValue += availables.get(i)[2];
        }
        return Math.max(max, maxValue);
    }
}
