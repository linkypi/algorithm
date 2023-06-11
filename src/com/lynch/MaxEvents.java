package com.lynch;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，结束于 endDayi 。
 * 你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。注意，一天只能参加一个会议。
 * 请你返回你可以参加的 最大 会议数目。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxEvents {
    public static void main(String[] args) {
//        int[][] events = {{1,2},{1,2},{3,3},{1,5},{1,5}};
        int[][] events = {{1,2},{1,2},{3,3},{1,5},{1,5}};
        int count = find(events);
        System.out.println("count: "+ count);
    }

    public static int find(int[][] events) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
                (a, b) -> { return a[1] - b[1]; }
        );

        // 按开始时间排序
        Arrays.sort(events, (a, b) -> {
            return a[0] - b[0];
        });

        int n = events.length;
        int current = events[0][0];
        int i = 0;
        int result = 0;
        while (current < 10000) {
            // 将已经开始的会议放到队列中
            while (i < n && events[i][0] <= current && events[i][1] >= current) {
                priorityQueue.add(events[i]);
                i++;
            }
            // 移除已经过期的会议
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[1] < current) {
                priorityQueue.poll();
            }
            // 选择最近一个会议参加
            if (!priorityQueue.isEmpty()) {
                priorityQueue.poll();
                result++;
            }
            current++;
        }
        return result;
    }
}
