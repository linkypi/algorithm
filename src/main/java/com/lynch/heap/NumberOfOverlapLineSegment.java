package com.lynch.heap;

import java.util.PriorityQueue;

/**
 * 给定很多线段，每个线段都有两个数 [start, end], 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 *   1. 线段的开始和结束位置一定都是整数值
 *   2. 线段重合的区域长度必须 >=1
 * 返回线段最多重合区域中，包含了几条线段
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/1 21:34
 */
public class NumberOfOverlapLineSegment {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {2, 4}, {3, 6}, {1, 10}, {5, 8}, {4, 9}
        };
        int count = countOfOverlapSegment(arr);
        System.out.println("count: " + count);
    }

    private static void qsort2d(int[][] arr, int start, int end) {
        if (end - start < 2) {
            return;
        }
        int mid = getPivot(arr, start, end);
        qsort2d(arr, start, mid - 1);
        qsort2d(arr, mid + 1, end);
    }

    private static int getPivot(int[][] arr, int start, int end){
        int[] pivot = arr[start];

        while(start < end) {
            while (arr[end][0] > pivot[0] && start < end) {
                end--;
            }
            arr[start] = arr[end];
            while (arr[start][0] <= pivot[0] && start < end) {
                start++;
            }
            arr[end] = arr[start];
        }
        arr[start] = pivot;
        return start;
    }

    private static int countOfOverlapSegment(int[][] segments){

        // 1. 首先根据start位置排序
        qsort2d(segments,0, segments.length-1);

        int max = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(segments.length);
        // 2.准备一个小根堆，每次进堆前判断堆顶元素是否小于等于
        //   当前start的值，是则移除堆顶直到满足条件后将 end 加入到堆中。
        //   每次遍历一次完成后堆元素的数量即为重叠数，对每次遍历取最大值即可
        //   每次进堆前判断堆顶元素是否存在小于等于当前start的值 目的就是
        //   为了剔除不重叠的线段
        for (int[] segment : segments) {
            int end = segment[1];
            if (queue.size() == 0) {
                queue.add(end);
                continue;
            }

            int start = segment[0];
            // 因为数组已经根据start排序，所以若新的线段start 比前一个线段的 end 大
            // 则说明他们不会重合，可以重新开始
            while (!queue.isEmpty() && queue.peek() <= start) {
                queue.poll();
            }
            queue.add(end);
            max = Math.max(max, queue.size());
        }
        return max;
    }
}
