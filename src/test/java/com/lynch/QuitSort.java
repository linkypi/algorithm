package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/9 13:42
 */
public class QuitSort {
    public static void main(String[] args) {
        int[] arr = {1, 6, 2, 9, 3, 8, 5, 4};
        qsort(arr, 0, arr.length - 1);

        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                System.out.print(arr[i] + " ");
            } else {
                System.out.println(arr[i]);
            }
        }
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
