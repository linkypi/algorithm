package com.lynch.extern;

import com.lynch.tools.Utils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个数组, 按顺序求出指定窗口大小内子数组元素的最大值, 并将他们放到一个数组中返回
 * 如: [3,5,2,6,4,7,1,8], 窗口大小为 3 则结果为: [5, 6, 6, 7, 7, 8]
 */
public class MaxWindow {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 3, 5, 4, 3, 3, 6, 7};
        int[] result = getMaxWindow(arr, 3);
        Utils.printArr(result);
    }

    private static int[] getMaxWindow(int[] arr, int windowSize) {
        int[] maxArr = new int[arr.length - windowSize + 1];
        // 使用双端队列记录从左到右按顺序依次递减的序列
        Deque<Integer> queue = new LinkedList<>();
        int right = 0, left = 0;
        while (right < arr.length) {
            if (queue.size() == 0) {
                queue.offer(right);
                right++;
                continue;
            }

            int current = arr[right];
            // 若后面加入的数与单调序列的最大值相等或较大大,则依次移除尾部元素后再将新元素放入队列
            while (queue.size() > 0 && current >= arr[queue.getLast()]) {
                queue.removeLast();
            }
            queue.offer(right);
            // 若已经达到窗口最大值 则输出队列头部的元素到最大值数组
            if (right - left + 1 == windowSize) {
                maxArr[left] = arr[queue.peekFirst().intValue()];
                left++;
                // 滑动窗口不断右移, 同时删除旧的元素位置
                if (left > queue.getFirst()) {
                    queue.removeFirst();
                }
            }

            right++;
        }
        return maxArr;
    }
}
