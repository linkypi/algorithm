package com.lynch;

import com.lynch.tools.Utils;

public class SortDemo {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 4, 6, 5, 1, 7, 2};
        insertSort(arr);
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
}
