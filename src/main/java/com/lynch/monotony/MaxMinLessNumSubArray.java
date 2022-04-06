package com.lynch.monotony;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个数组及num，若其子数组最大值 - 最小值 <= num
 * 则认为该子数组达标，求达标子数组的个数
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/4 21:38
 */
public class MaxMinLessNumSubArray {
    public static void main(String[] args) {

    }

    private static int find(int[] arr, int num){
        // 最大值队列，由大到小
        Deque<Integer> qmax = new LinkedList<>();
        // 最小值队列，由小到大
        Deque<Integer> qmin = new LinkedList<>();

        int result = 0;
        int left = 0;
        int right = 0;
        while(left < arr.length) {
            while (right < arr.length) {
                // qmin 存放的队列由小到大，当遇到更小的元素 时需要将
                // 队尾较大的元素弹出，直到队尾的数比新的元素小
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[right]) {
                    qmin.pollLast();
                }
                qmin.addLast(right);

                // qmax 存放的队列由大到小，当遇到更大的元素 时需要将
                // 队尾的元素弹出，直到队尾的数比新的元素大
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[right]) {
                    qmax.pollLast();
                }
                qmax.addLast(right);

                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }
                right++;
            }

            result += right - left;
            if(qmax.peekFirst() == left){
                qmax.pollFirst();
            }
            if(qmin.peekFirst() == left){
                qmin.pollFirst();
            }
            left++;
        }
        return result;
    }
}
