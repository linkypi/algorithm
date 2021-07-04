package com.lynch.sort;

public class Test {

    public static void main(String[] args) {
        int[] arr = {9,4,8,2,6,3};
        bubbleSort(arr);
        for (int item: arr){
            System.out.print(item);
        }
    }
    
    private static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
