package com.lynch;

import java.awt.*;

public class QuickSort {
    public static void main(String[] args) {

        int[] arr = {4, 8, 3, 9, 2, 7,14,11};
        quickSort(arr, 0, arr.length-1);
        for (int index = 0; index < arr.length; index++) {
            System.out.println(arr[index]);
        }
    }

    private static void quickSort(int[] arr, int left, int right) {

        if(left<right) {
            int position = partition(arr, left, right);
            quickSort(arr, left, position - 1);
            quickSort(arr, position + 1, right);
        }
    }

    private static int partition(int[] arr, int start, int end) {

        if(start >= end){
            return 0;
        }

        // 首先保存一个基准值，在以后的比较中也就空出该位置用于挪动元素
        // 挪动完成后将元素放回中间位置即可
        int pivot = arr[start];
        int left = start;
        int right = end;
        while (left < right) {
            while (arr[right] >= pivot && left < right) {
                right--;
            }
            arr[left] = arr[right];
            while (arr[left] <= pivot && left < right) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = pivot;
        return left;
    }
}
