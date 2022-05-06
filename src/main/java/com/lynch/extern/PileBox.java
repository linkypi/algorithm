package com.lynch.extern;

import java.util.*;

/**
 * 堆箱子。给你一堆 n 个箱子，箱子宽 wi、深 di、高 hi。箱子不能翻转，
 * 将箱子堆起来时，下面箱子的宽度、高度和深度必须大于上面的箱子。
 * 实现一种方法，搭出最高的一堆箱子。箱堆的高度为每个箱子高度的总和。
 * 输入使用数组 [wi, di, hi] 表示每个箱子。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/15 16:12
 */
public class PileBox {
    public static void main(String[] args) {
        int[][] arr = {{2, 6, 7}, {3, 4, 5}, {1, 1, 1}, {2, 3, 4}};
        int maxHigh = pileBox(arr);

        int[] list = {1, 4, 7, 5};
        int maxIncreaseSubSeq = getMaxIncreaseSubSeq(list);
        int[] list2 = {2, 7, 3, 5, 4, 6};
        int maxIncreaseSubSeq2 = getMaxIncreaseSubSeq(list2);

        List<Integer> maxIncreaseSubSeq1 = getMaxIncreaseSubSeq(arr, 2);
        List<Integer> maxIncreaseSubSeq3 = getMaxIncreaseSubSeq(arr, 1);
        System.out.println("max high: " + maxHigh);
    }

    /**
     * [wi, di, hi]
     *
     * @param box
     * @return
     */
    public static int pileBox(int[][] box) {
        if (box == null || box.length == 0) {
            return 0;
        }

        Arrays.sort(box, (a, b) -> {
            // 首先根据 宽度 width 升序排序
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            // width 相同则 首先根据深度升序排序  然后再根据高度升序排列
            if (a[1] == b[1]) {
                return a[2] - b[2];
            }

            return a[1] - b[1];
        });

        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> {
            // 首先根据 宽度 width 降序排序
            if (a[0] != b[0]) {
                return b[0] - a[0];
            }
            // width 相同则 首先根据深度降序排序  然后再根据高度降序排列
            if (a[1] == b[1]) {
                return b[2] - a[2];
            }

            return b[1] - a[1];
        });

        for (int i = 0; i < box.length; i++) {
            int[] item = box[i];
            if (queue.isEmpty()) {
                queue.add(item);
                continue;
            }

            int[] peek = queue.peek();
            if (peek[0] < item[0] && peek[1] < item[1] && peek[2] < item[2]) {
                queue.add(item);
            }
        }

        int max = 0;
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            max += poll[2];
        }
        return max;
    }

    static int getMaxIncreaseSubSeq(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1];
            if (arr[i] - arr[i - 1] > 0) {
                dp[i]++;
            }
        }
        return dp[n - 1];
    }

    static List<Integer> getMaxIncreaseSubSeq(int[][] arr, int col) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1];
            if (arr[i][col] - arr[i - 1][col] > 0) {
                list.add(i);
                dp[i]++;
            }
        }
        System.out.println("col: " + col + "max:" + dp[n - 1]);
        return list;
    }
}
