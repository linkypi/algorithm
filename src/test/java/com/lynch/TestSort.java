package com.lynch;

import com.lynch.tools.Utils;

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
}
