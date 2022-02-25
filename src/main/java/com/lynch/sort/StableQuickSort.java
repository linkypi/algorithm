package com.lynch.sort;


import com.lynch.tools.Utils;

/**
 * 稳定快速排序实现，参考论文： https://www.doc88.com/p-7834458644507.html
 * Created by troub on 2022/2/25 17:14
 */
public class StableQuickSort {


    public static void main(String[] args) {
        int[] arr = new int[]{5, 1, 6, 3, 9, 2, 8};
        Utils.printArr("before stable quick sort: ", arr);
        stableQuickSort(arr, 0, arr.length - 1);
        Utils.printArr("after stable quick sort: ", arr);
    }

    private static int[] temp = new int[100];

    private static void stableQuickSort(int[] arr, int start, int end) {
        int i, j;
        int i0, j0, i1, j1;

        int mid = arr[start];
        i = i0 = i1 = start;
        j = j0 = j1 = end;

        do {
            // 从左边扫描小于mid的数
            while (arr[i] < mid && i <= j) {
                if (i0 != i) {
                    arr[i0] = arr[i];
                }
                i0++;
                i++;
            }
            // 从右边扫描大于mid的数
            while (arr[j] >= mid && j >= i) {
                if (j0 != j) {
                    arr[j0] = arr[j];
                }
                j0--;
                j--;
            }
            // 找到一对数据，将不满足条件的数据分别移动到临时空间的前后位置
            if (i < j) {
                temp[i1++] = arr[i++];
                temp[j1--] = arr[j--];
            } else if (i == j) {
                i++;
                j--;
            } else {
                i++;
            }
        } while (i <= j);

        // 将 temp 中前面的数据复制到 arr 中后部分数据的前面
        for (int k = i1 - 1; k >= start; k--, j0--) {
            arr[j0] = temp[k];
        }

        // 将 temp 中后面的数据复制到 arr 中前部分数据的后面
        for (int k = j1 + 1; k <= end; k++, i0++) {
            arr[i0] = temp[k];
        }

        if (start < j) {
            stableQuickSort(arr, start, j);
        }
        if (end > i) {
            stableQuickSort(arr, i, end);
        }
    }
}