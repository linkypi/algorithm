package com.lynch.un_overlap_region;

import java.util.Arrays;

/**
 * 给定很多形如 [start, end]的闭区间，请设计一个算法，
 * 算出这些区间最多有几个互不相交的区间
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/8 23:49
 */
public class IntervalSchedule {
    public static void main(String[] args) {
        int[][] arr = {
                {1,3},{2,4},{3,6}
        };
        int count = find(arr);
        System.out.println("count: "+ count);
    }

    private static int find(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // 首先将数组按照 end 升序排序
        Arrays.sort(arr, (o1, o2) -> o1[1] - o2[1]);

        int max = 1;
        int end = arr[0][1];
        for (int i = 1; i < arr.length; i++) {
            int start = arr[i][0];
            if (start >= end) {
                max++;
                end = arr[i][1];
            }
        }
        return max;
    }
}
