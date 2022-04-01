package com.lynch.median;

import com.lynch.tools.Utils;

import java.util.PriorityQueue;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/1 16:03
 */
public class MedianFinder {

    // 使用大根堆存放左侧最大值
    PriorityQueue<Integer> left;
    // 使用小根堆存放右侧最小值, PriorityQueue默认以自然顺序存放，所以默认是小根堆
    PriorityQueue<Integer> right;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        left = new PriorityQueue<>((a, b) -> {
            return b.compareTo(a);
        });
        right = new PriorityQueue<>();
        left.add(Integer.MIN_VALUE);
        right.add(Integer.MAX_VALUE);
    }

    public void addNum(int num) {
        int lmax = left.peek();
        if (num <= lmax) {
            left.add(num);
        } else {
            right.add(num);
        }
        resize();
    }

    public double findMedian() {
        if (left.size() == right.size()) {
            return (left.peek() + right.peek()) / 2;
        }
        return left.peek();
    }

    private void resize(){
        while(left.size() - right.size()>1 ){
            Integer item = left.poll();
            right.add(item);
        }
        while(left.size() - right.size() < 0 ){
            Integer item = right.poll();
            left.add(item);
        }
    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(7);
        medianFinder.addNum(2);
        medianFinder.addNum(6);
        medianFinder.addNum(5);
        medianFinder.addNum(3);

        int[] arr = {7, 3, 9, 2, 4, 6, 1, 5, 8, 43, 24, 65, 17};
//        int[] arr = {7,3,9,2,4};
        heapSort(arr);
        Utils.printArr(arr);
    }

    private static void heapSort(int[] arr) {
        int n = arr.length;
        heapify(arr, n);
        for (int i = 0; i < n; i++) {
            swap(arr, 0, n - i - 1);
            heapify(arr, n - i - 1);
        }
    }

    private static void heapify(int[] arr, int heapSize) {
        // 找到最后一个非叶子节点
        int index = heapSize / 2 - 1;
        while (index >= 0) {
            int leftIndex = 2 * index + 1;
            int maxIndex = arr[index] > arr[leftIndex] ? index : leftIndex;
            if (leftIndex + 1 < heapSize) {
                maxIndex = arr[maxIndex] > arr[leftIndex + 1] ? maxIndex : leftIndex + 1;
            }
            swap(arr, maxIndex, index);
            index--;
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}

