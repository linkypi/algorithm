package com.lynch.subsequence;

import org.junit.Test;

/**
 * 给定一个数组，求最长递增子序列长度
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/28 21:58
 */
public class LongerIncreasingSubSequence {

    @Test
    public void test() {
        int[] arr = {0, 1, 0, 3, 2, 3};
//        int[] arr = {10,9,2,5,3,7,101,18};
        int count = find(arr);
        System.out.println("count: " + count);
    }

    private static int find(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // 用于存放 patient game 纸牌游戏的桶，游戏规则：
        // 只能把点数小的牌压到点数比它大的堆上；如果当前牌点数较大没有可以放置的堆，
        // 则新建一个堆，把这张牌放进去；如果当前牌有多个堆可供选择，则选择最左边的那一堆放置。
        int[] buckets = new int[arr.length];
        // 实际使用的桶数
        int size = 0;
        for (int item : arr) {
            // 找到元素item所在buckets的位置, 即通过二分法找到左边界
            int right = size, left = 0;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (item <= buckets[mid]) {
                    // 收缩右边界
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            // 若实际位置大于当前桶数，则将桶数自增
            if (left >= size) {
                size++;
            }
            buckets[left] = item;
        }
        return size;
    }
}
