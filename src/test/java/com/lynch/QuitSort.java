package com.lynch;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/9 13:42
 */
public class QuitSort {
    public static void main(String[] args) {
        int[] arr = {1, 6, 2, 9, 3, 8, 5, 4};
//        qsort(arr, 0, arr.length - 1);

        Map<String, String> map = new LinkedHashMap();

        qs(arr, 0, arr.length - 1);
//        qs(arr, 0, mid - 1);
//        qs(arr, mid + 1, arr.length - 1);

        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                System.out.print(arr[i] + " ");
            } else {
                System.out.println(arr[i]);
            }
        }
    }

    public static void qs(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = arr[start];

        int left = start;
        int right = end;
        while (left < right) {
            while (left < right && arr[left] < pivot) {
                left++;
            }
            while (right > -1 && arr[right] > pivot) {
                right--;
            }
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        arr[left] = pivot;

        qs(arr, start, left - 1);
        qs(arr, left + 1, end);
    }








    public static void qsort(int[] arr, int start, int end) {
        if(start >= end){
            return;
        }
        int pivotIndex = sort(arr, start, end);
        qsort(arr, start, pivotIndex - 1);
        qsort(arr, pivotIndex + 1, end);
    }

    public static int sort(int[] arr, int left, int right) {

        int pivot = arr[left];
        while (left < right) {
            while (right > left && arr[right] > pivot) {
                right--;
            }
            if (left < right) {
                arr[left++] = arr[right];
//                int temp = arr[left];
//                arr[left] = arr[right];
//                arr[right] = temp;
//                left++;
            }

            while (left < right && arr[left] < pivot) {
                left++;
            }
            if (left < right) {
                arr[right--] = arr[left];
//                int temp = arr[left];
//                arr[left] = arr[right];
//                arr[right] = temp;
//                right--;
            }
        }
        arr[left] = pivot;
        return left;
    }
}
