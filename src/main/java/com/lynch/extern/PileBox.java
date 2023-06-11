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
        // [wi, di, hi]
//        int[][] arr = {{2, 6, 7}, {3, 4, 5}, {1, 1, 1}, {2, 3, 4}};
        int[][] arr = {{9, 9, 10}, {8, 10, 9}, {9, 8, 10}, {9, 8, 10}, {10, 8, 8}, {9, 8, 9}, {9, 8, 8}, {8, 9, 10}, {10, 9, 10},
        {8, 8, 10}, {10, 9, 10}, {10, 9, 8}, {8, 9, 9}, {9, 10, 8}, {8, 9, 9}, {10, 10, 9}, {8, 9, 10}, {8, 10, 10}, {8, 9, 10}, {10, 10, 8}, 
        {10, 10, 9}, {9, 10, 10}, {10, 8, 9}, {10, 10, 9}, {8, 9, 10}, {8, 8, 9}, {8, 10, 10}, {9, 9, 10}, {10, 8, 8}, {10, 10, 8}, {8, 9, 9}, 
        {8, 9, 8}, {10, 10, 8}, {8, 10, 8}, {10, 9, 10}, {9, 9, 10}, {9, 9, 9}, {8, 9, 8}, {9, 8, 8}, {8, 9, 10}, {10, 10, 8}, {9, 9, 9}, {10, 10, 10},
        {10, 9, 8}, {9, 8, 9}, {8, 8, 10}, {8, 8, 8}, {8, 8, 8}, {8, 9, 10}, {10, 9, 8}, {8, 10, 8},{8, 10, 10}, {9, 10, 10}, {8, 8, 9}, {9, 9, 9}, 
        {10, 8, 8}, {8, 10, 10}, {9, 10, 9}, {9, 9, 8}, {8, 10, 9}, {9, 8, 8}, {9, 9, 10}, {9, 10, 10}, {8, 8, 10}};
//        int max = pileBox(arr);

        int[] list = {1, 4, 7, 5};
        int maxIncreaseSubSeq = getMaxIncreaseSubSeq(list);
        int[] list2 = {2, 7, 3, 5, 4, 6};
        int maxIncreaseSubSeq2 = getMaxIncreaseSubSeq(list2);

        Arrays.sort(arr,(a,b)->{
            if(a[0]!=b[0]) {
                return a[0] - b[0];
            }else{
                if(a[1]!=b[1]) {
                    return a[1] - b[1];
                }else{
                    return b[2] - a[2];
                }

            }
        });
//        int pileBox2 = pileBox2(arr);
        int[][] wideArr = getMaxIncreaseSubSeq(arr, 0);
        int[][] deepArr = getMaxIncreaseSubSeq(wideArr, 1);
        int[][] HiArr = getMaxIncreaseSubSeq(deepArr, 2);
        int max = 0;
        for(int[] item: HiArr){
            max += item[2];
        }
        System.out.println("max high: " + max);
    }

    public static int pileBox2(int[][] box) {
        Arrays.sort(box, (a, b) -> a[0] - b[0]);
        int[] dp = new int[box.length];
        for (int i = 0; i < box.length; i++) {
            dp[i] = box[i][2];
        }
        int res = 0;
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < i; j++) {
                if (box[j][0] < box[i][0] && box[j][1] < box[i][1] && box[j][2] < box[i][2])
                    dp[i] = Math.max(dp[i], dp[j] + box[i][2]);
            }
            res = Math.max(res, dp[i]);
        }
        return res;
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
            if (arr[i] > arr[i - 1]) {
                dp[i]++;
            }
        }
        return dp[n - 1];
    }

    static int[][] getMaxIncreaseSubSeq(int[][] arr, int col) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;

        List<int[]> list = new ArrayList<>();
        list.add(arr[0]);

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1];
            if (arr[i][col] > arr[i - 1][col]) {
                list.add(arr[i]);
                dp[i]++;
            }
        }
        System.out.println("col: " + col + "max:" + dp[n - 1]);
        return list.toArray(new int[list.size()][3]);
    }
}
