package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/8 21:24
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {9, 3, 7, 2, 6, 1};
        sort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            if (i != arr.length - 1) {
                System.out.print(value + " , ");
            } else {
                System.out.println(value);
            }

        }
    }

    public static void sort(int[] arr, int start, int end) {
        if (start == end) {
            return;
        }
        int mid = (start + end) / 2;
        sort(arr, start, mid);
        sort(arr, mid + 1, end);

        merge(arr, start, mid, end);
    }

    public static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int index = 0;
        int left = start;
        int right = mid + 1;
        while (left <= mid && right <= end) {
            if (arr[left] < arr[right]) {
                temp[index++] = arr[left++];
            }
            if (arr[left] >= arr[right]) {
                temp[index++] = arr[right++];
            }
        }

        while (left <= mid) {
            temp[index++] = arr[left++];
        }
        while (right <= end) {
            temp[index++] = arr[right++];
        }

        int count = end - start + 1;
        for (int i = 0; i < count; i++) {
            arr[start++] = temp[i];
        }
    }

    public static void qsort(int[] arr, int start, int end){
        int pivot = arr[start];
        int pindex = start;
        int left = start, right = end;
        while(left < right){
            if(arr[right] < pivot){
                arr[left++] = arr[right];
            }
            if(arr[left] > pivot){
                arr[right--] = arr[left];
            }
        }
    }
}
