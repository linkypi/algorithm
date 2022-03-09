package com.lynch;

import com.lynch.tools.Utils;
import com.sun.javafx.image.PixelUtils;

/**
 * Description algorithm
 * Created by troub on 2021/12/27 15:20
 */
public class TestSort {
    public static void main(String[] args) {

        int[] arr = new int[]{5, 2, 7, 1, 6, 8};
        qsort(arr, 0, arr.length - 1);
        Utils.printArr("quick sort: ", arr);

        int[] arr2 = new int[]{5, 2, 7, 1, 6, 8};
        mergeSort(arr2, 0, arr2.length - 1);
        Utils.printArr("merge sort: ", arr2);

        int[] arr3 = new int[]{3, 1, 7, 5, 4, 8};
        heapSort(arr3);
        Utils.printArr("heap sort: ", arr3);

        int[] arr4 = new int[]{3, 1, 7, 5, 4, 8};
        insertionSort(arr4);
        Utils.printArr("insertion sort: ", arr4);
    }

    private static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j-1]) {
                    int x = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = x;
                }
            }
        }
    }

    private static void qsort(int[] arr, int start, int end) {
        if (end - start < 2) {
            return;
        }
        int pivotIndex = pivotIndex(arr, start, end);
        qsort(arr, start, pivotIndex - 1);
        qsort(arr, pivotIndex + 1, end);
    }

    private static int pivotIndex(int[] arr, int start, int end) {
        int pivot = arr[start];
        while (start < end) {
            while (arr[end] > pivot && start < end) {
                end--;
            }
            arr[start] = arr[end];

            while (arr[start] <= pivot && start < end) {
                start++;
            }
            arr[end] = arr[start];
        }
        arr[start] = pivot;
        return start;
    }

    private static void mergeSort(int[] arr, int start, int end) {
        if (end - start < 1) {
            return;
        }
        int mid = (start + end) >> 1;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, end, mid);
    }

    private static void merge(int arr[], int start, int end, int mid) {
        int[] temp = new int[end - start + 1];
        int current = 0;
        // 左边数组 [start, mid], 右边数组 [mid+1, end]
        int left1 = start, left2 = mid + 1;
        while (left1 <= mid && left2 <= end) {
            if (arr[left1] > arr[left2]) {
                temp[current++] = arr[left2++];
            } else {
                temp[current++] = arr[left1++];
            }
        }

        while (left1 <= mid) {
            temp[current++] = arr[left1++];
        }
        while (left2 <= end) {
            temp[current++] = arr[left2++];
        }

        int index = 0;
        while (index < end - start + 1) {
            arr[start + index] = temp[index++];
        }
    }

    private static int[] heapSort(int[] arr) {
        int n = arr.length;
        // 建堆
        heapify(arr, n);

        // 调整
        for (int i = n - 1; i > -1; i--) {
            swap(arr, 0, i);
            heapify(arr, i);
        }
        return arr;
    }

    /**
     * 建堆，由于调整堆后有部分元素已排序完成，故需要调整堆长度，所以需要传入heapSize
     *
     * @param arr
     * @param heapSize
     */
    private static void heapify(int[] arr, int heapSize) {
        // 查找第一个非叶子节点下标
        int x = (heapSize >> 1) - 1;
        for (int i = x; i >= 0; i--) {
            int current = arr[i];
            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;
            // 左子节点必定存在，不需要判断其下标是否越界
            int left = arr[leftIndex];
            int maxIndex = leftIndex;
            // 判断右子节点是否存在，若存在则比较左右子节点哪个更大，并记录最大值索引
            if (rightIndex < heapSize && left < arr[rightIndex]) {
                maxIndex = rightIndex;
            }

            // 使用最大值与当前值比较，若最大值比当前值大则交换
            if (arr[maxIndex] > current) {
                swap(arr, maxIndex, i);
            }
        }
    }


    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}