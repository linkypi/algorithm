package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/23 15:15
 */
public class TestQsort {
    public static void main(String[] args) {

    }

    private static void sort(int[] arr){
        if(arr==null || arr.length ==0){
            return;
        }

        int n = arr.length;

    }

    private static void qsort(int[] arr, int left, int right){
        int mid = left + ((right - left)>>1);
        int pivot = arr[mid];
        for(int i =left;i<=right;i++){
           if(arr[i]>pivot){
//               swap();
           }
           if(arr[i] < pivot){
//               swap
           }
        }
    }
}
