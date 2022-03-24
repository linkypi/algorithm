package com.lynch;

import com.lynch.tools.Utils;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/23 14:27
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = new int[]{9, 6, 8, 2, 5};
        Utils.printArr("before sort:", arr);
        sort(arr);
        Utils.printArr("after sort:", arr);
    }

    private static void sort(int[] arr) {
        int n = arr.length;

        heapify(arr, n);

        for (int i = n - 1; i >= 1; i--) {
            swap(arr,i,0);
            heapify(arr, i);
        }
    }

    private static void heapify(int[] arr, int heapSize) {
        // 最后一个非叶子节点的位置
        int nodes = heapSize / 2 - 1;
        while (nodes >= 0) {
            int leftIndex = 2 * nodes + 1;
            int rightIndex = 2 * nodes + 2 < heapSize ? 2 * nodes + 2 : -1;

            int maxIndex = arr[nodes] > arr[leftIndex] ? nodes : leftIndex;
            if (rightIndex > 0 && arr[maxIndex] < arr[rightIndex]) {
                maxIndex = rightIndex;
            }
            if (maxIndex != nodes) {
                swap(arr, maxIndex, nodes);
            }
            nodes--;
        }
    }

    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
