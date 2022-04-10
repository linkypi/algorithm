package com.lynch.un_overlap_region;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 给你一个数组events，其中events[i] = [startDayi, endDayi]，表示会议i开始于startDayi，结束于endDayi。
 * 你可以在满足startDayi<= d <= endDayi中的任意一天d参加会议i。注意，一天只能参加一个会议。
 * 请你返回你可以参加的最大会议数目。
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/9 9:23
 */
public class MaxEvents {

    public static void main(String[] args) {
//        int[][] events = {{1, 2}, {2, 3}, {3, 4}, {1, 2}};
        int[][] events = {{1, 4}, {4, 4}, {2, 2}, {3, 4}, {1, 1}};
        int maxEvents = maxEvents(events);
        int maxEvents2 = find(events);
        System.out.println("max: " + maxEvents);
    }

    /**
     * 对于每个时间点，所有在当前时间及之前时间开始 并且在当前时间还未结束的会议都是可以参加的
     * 在所有可参加的会议中，选择结束时间最早的会议是最优的，因为还有更多机会去参加其他会议
     * @param arr
     * @return
     */
    private static int find(int[][] arr){
        if(arr ==null || arr.length ==0){
            return 0;
        }

        Arrays.sort(arr,(a,b)->a[0] - b[0]);

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int current = 1;
        int index = 0;
        int count = 0;
        while(index < arr.length || !queue.isEmpty()) {
            // 将第 current 天开始的会议全部放入小根堆, 放入结束时间
            for (int i = 0; i < arr.length; i++) {
                if (current == arr[i][0]) {
                    queue.add(arr[i][1]);
                }
            }

            // 将已经结束的会议移除
            while (!queue.isEmpty() && queue.peek() < current) {
                queue.poll();
            }

            // 选择结束时间最早会议参加，即移除堆顶元素
            if (!queue.isEmpty()) {
                queue.poll();
                count++;
            }

            current++;
            index++;
        }

        return count;
    }

    public static int maxEvents(int[][] events) {
        int n = events.length;
        // 按照开始时间升序排列，这样，对于相同开始时间的会议，可以一起处理
        Arrays.sort(events, (o1, o2) -> o1[0] - o2[0]);

        // 小顶堆：用于高效的维护最小的 endDay
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int res = 0;
        int currDay = 1;
        int i = 0;
        while (i < n || !pq.isEmpty()) {
            // 将所有开始时间等于 currDay 的会议的结束时间放到小顶堆
            while (i < n && events[i][0] == currDay) {
                pq.add(events[i][1]);
                i++;
            }

            // 将已经结束的会议弹出堆中，即堆顶的结束时间小于 currDay 的
            while (!pq.isEmpty() && pq.peek() < currDay) {
                pq.remove();
            }

            // 贪心的选择结束时间最小的会议参加
            if (!pq.isEmpty()) {
                // 参加的会议，就从堆中删除
                pq.remove();
                res++;
            }

            // 当前的天往前走一天，开始看下下一天能不能参加会议
            currDay++;
        }

        return res;
    }
}
