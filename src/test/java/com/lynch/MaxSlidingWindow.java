package com.lynch;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.cn/problems/sliding-window-maximum
 * 
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/20 10:25
 */
public class MaxSlidingWindow {

    public static void main(String[] args) {

    }

    /**
     * 使用单调队列实现
     *
     * @param arr
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow(int[] arr, int k) {
        // 使用队列维护单调递减队列
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && arr[i] > arr[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        int n = arr.length;
        int[] ans = new int[n - k + 1];

        for (int i = k; i < n; i++) {
            while (!deque.isEmpty() && arr[i] > arr[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            // 移除已经不再滑动窗口内的数据索引
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            ans[i - k + 1] = arr[deque.pollFirst()];
        }
        return ans;
    }

}
