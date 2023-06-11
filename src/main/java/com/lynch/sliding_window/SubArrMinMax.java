package com.lynch.sliding_window;

import java.util.LinkedList;

/**
 * 给定一个整数数组 arr，及一个整数 num
 * 若arr子数组中的 最大值-最小值 <= num 则达标
 * 求arr所有子数组中达标的数量
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/2 8:50
 */
public class SubArrMinMax {
    public static void main(String[] args) {
        int[] arr = {8, 4, 9, 2, 7, 3, 1};
        int count = getArrayNum(arr, 2);
        System.out.println("count: " + count);
    }

    private static int find(int[] arr, int num) {
        LinkedList<Integer> minQueue = new LinkedList<>();
        LinkedList<Integer> maxQueue = new LinkedList<>();

        int left = 0;
        int right = 0;
        int count = 0;
        while (left < arr.length) {
            while (right < arr.length) {
                if (minQueue.isEmpty() || minQueue.peekLast() != arr[right]) {
                    int element = arr[right];
                    while (!minQueue.isEmpty() && element >= minQueue.peekLast()) {
                        minQueue.pollLast();
                    }
                    minQueue.addLast(element);
                    while (!maxQueue.isEmpty() && element <= maxQueue.peekLast()) {
                        maxQueue.pollLast();
                    }
                    maxQueue.addLast(element);
                }
                if (!maxQueue.isEmpty() && !minQueue.isEmpty() && maxQueue.peekLast() - minQueue.peekLast() <= num) {
                    count++;
                    break;
                }
                right++;
            }
            minQueue.remove(arr[left]);
            maxQueue.remove(arr[left]);
            left++;
        }
        return count;
    }


    public static int getArrayNum(int[] arr, int num) {
        //判空
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //拿LinkedList作为双端队列来使用
        LinkedList<Integer> qmax = new LinkedList();
        LinkedList<Integer> qmin = new LinkedList();
        //j是不重置的，因为上面两个结论，到j不满足条件时，j是不变的，所以j只会不断地增长
        //j不重置则使用while语句
        int i = 0;
        int j = 0;
        int result = 0;
        //到j不满足条件时，i..j-1所有的子数组都满足条件，但是计数的时候只计算了从i开头的
        //后面剩下的会到它们为开头的时候计算（如i+1），这也是j不重置的原因
        while (i < arr.length) {
            while (j < arr.length) {
                //为了保证同一个下标只进栈一次，出栈一次，这样时间复杂度才能保证(每个元素O(1)，n个元素O(n))
                //如果break,j不变，而qmin.peekLast()正好是上一轮的j，后面i++，所以判断[i+1..j]是否满足条件
                //到j不满足条件，所以[i+1..j]不一定满足条件
                if (qmin.isEmpty() || qmin.peekLast() != j) {
                    //双端队列结构
                    while (!qmax.isEmpty() && arr[j] >= arr[qmax.peekLast()]) {
                        qmax.pollLast();
                    }
                    qmax.addLast(j);
                    while (!qmin.isEmpty() && arr[j] <= arr[qmin.peekLast()]) {
                        qmin.pollLast();
                    }
                    qmin.addLast(j);
                }
                if (arr[qmax.peekFirst()] - arr[qmin.peekFirst()] > num) {
                    break;
                }
                j++;
            }
            //以i开头一共j-i个，a[i..i]到a[i..j-1]
            result += (j - i);
            //开头i要自增，应该把队列中的i移除，只可能在最大和最小地方出现，要不就提前被弹出了
            if (qmax.peekLast()==i) {
                qmax.pollFirst();
            }
            if (qmin.peekFirst() == i) {
                qmin.pollFirst();
            }
            i++;
        }
        return result;
    }
}
