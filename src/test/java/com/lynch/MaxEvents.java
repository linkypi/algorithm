package com.lynch;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 *  给你一个数组events，其中events[i] = [startDayi, endDayi]，表示会议i开始于startDayi，结束于endDayi。
 *  你可以在满足startDayi<= d <= endDayi中的任意一天d参加会议i。注意，一天只能参加一个会议。
 *  请你返回你可以参加的最大会议数目。
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/10 15:57
 */
public class MaxEvents {
    public static void main(String[] args) {
//        int[][] events = {{1, 4}, {4, 4}, {2, 2}, {3, 4}, {1, 1}};
        int[][] events = {{1, 2}, {3, 4}, {2, 3}};
        int count = count(events);
        System.out.println("max events : " + count);
    }

    public static int count(int[][] arr) {
        // 按照开始时间升序排列
        Arrays.sort(arr, (a, b) -> {
            return a[0] - b[0];
        });

        // 最小值，也即其实天数
        int current = arr[0][0];
        // 最大值，即最大天数
        int n = arr[arr.length-1][0];
        int count = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        while (current < n || !priorityQueue.isEmpty()) {
            // 首先将当天开始的会议加入队列
            for (int[] ints : arr) {
                if (ints[0] == current) {
                    priorityQueue.add(ints[1]);
                }
            }
            // 移除已经过期的会议， 即结束时间已经过期
            while (!priorityQueue.isEmpty() && priorityQueue.peek() < current) {
                priorityQueue.remove();
            }

            // 选择第一个结束的会议，由于优先队列已做升序处理
            // 故选择第一个结束时间最早的会议 后面方可参加更多会议
            if (!priorityQueue.isEmpty()) {
                count++;
                priorityQueue.remove();
            }

            current++;
        }
        return count;
    }
}
