package com.lynch;

import com.lynch.tools.Generator;
import com.lynch.tools.Utils;

public class SortDemo {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 4, 6, 5, 1, 7, 2};
        int[] arr2 = Utils.copyArr(arr);
        insertSort(arr);

        System.out.println();
        mergeSort(arr2);
    }

    private static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        Utils.printArr("insert sort before", arr);
        for (int i = 1; i < arr.length; i++) {
            //  当走到第 i 个位置时 就从该位置开始,取该位置的数与前一个的数比较, 若前一个数较大则交换
            // 交换完成后再取前一个数与更前一个数比较, 若更前一个数较大则交换,以此不断进行,直到结束
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        Utils.printArr("insert sort after", arr);
    }

    private static void mergeSort(int[] arr) {
        Utils.printArr("merge sort before", arr);
        process(arr, 0, arr.length - 1);
        Utils.printArr("merge sort after", arr);
    }

    private static void process(int[] arr, int start, int end) {
        if (start == end) {
            return;
        }
        int mid = start + ((end - start) >> 1);

        process(arr, start, mid);
        process(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    private static void merge(int[] arr, int start, int mid, int end) {

        int[] temp = new int[end - start + 1];
        int tempIndex = 0;
        int leftIndex = start;
        int rightIndex = mid + 1;

        while (leftIndex <= mid && rightIndex <= end) {
            temp[tempIndex++] = arr[leftIndex] > arr[rightIndex] ? arr[rightIndex++] : arr[leftIndex++];
        }

        while (rightIndex <= end) {
            temp[tempIndex++] = arr[rightIndex++];
        }
        while (leftIndex <= mid) {
            temp[tempIndex++] = arr[leftIndex++];
        }

        for (int i = 0; i < temp.length; i++) {
            arr[start + i] = temp[i];
        }

        Utils.printArr("merge, start: " + start + ", mid: " + mid + ", end: " + end, arr);
    }
}
