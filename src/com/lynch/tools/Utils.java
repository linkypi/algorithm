package com.lynch.tools;

public class Utils {
    public static void printArr(String msg, int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println(msg + " arr: null");
            return;
        }

        System.out.print(msg + " arr: ");
        for (int item : arr) {
            System.out.print(item + "  ");
        }
        System.out.println();
    }

    public static int[] copyArr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }
        return ret;
    }

    /**
     * 对比两个数组中的元素是否一致
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null || arr1.length == 0) {
            System.out.println("arr1 is null or it's length is 0.");
            return false;
        }
        if (arr2 == null || arr2.length == 0) {
            System.out.println("arr2 is null or it's length is 0.");
            return false;
        }
        if(arr1.length != arr2.length){
            System.out.println("arr1 length is not equal arr2 length.");
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] !=arr2[i]){
                return false;
            }
        }
        return true;
    }
}