package com.lynch.sort;

public class Test {

    public static void main(String[] args) {
        int[] arr = {9, 4, 8, 2, 6, 3};
        print(arr, "before bubble sort");

        bubbleSort(arr);
        print(arr, "after bubble sort");

        int[] arr2 = {7, 4, 5, 2, 1, 6};
        print(arr2, "before merge sort");
        final int[] result = mergeSort(arr2, 0, arr2.length - 1);
        print(result, "after merge sort");
    }

    private static void print(int[] arr, String msg) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(" -> ");
            }
        }
        System.out.println(msg + " : " + builder.toString());
    }

    private static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    private static int[] mergeSort(int[] arr, int start, int end) {
        if (arr == null || arr.length == 1) {
            return new int[0];
        }
        // 当 start 到 end 该段范围的元素不可再分时则返回当前元素组成的数组
        if (end == start) {
            return new int[]{ arr[start] };
        }
        // 找到中间位置
        int mid = start + ((end - start) >> 1);
        int[] left = mergeSort(arr, start, mid);
        int[] right = mergeSort(arr, mid + 1, end);
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int m = left.length;
        int n = right.length;
        int[] arr = new int[m + n];
        int li = 0, ri = 0, index = 0;
        while (li < m && ri < n) {
            if (left[li] < right[ri]) {
                arr[index++] = left[li++];
            } else {
                arr[index++] = right[ri++];
            }
        }
        if (li < m) {
            for (int i = li; i < m; i++) {
                arr[index++] = left[i];
            }
        }
        if (ri < n) {
            for (int i = ri; i < n; i++) {
                arr[index++] = right[i];
            }
        }
        return arr;
    }

    private static int[] merge2(int[] left, int[] right, int start, int mid, int end) {
        int m = mid + 1;
        int n = end + 1;
        int[] arr = new int[end - start + 1];
        int li = start, ri = mid + 1, index = 0;
        while (li < m && ri < n) {
            if (left[li] < right[ri]) {
                arr[index++] = left[li++];
            } else {
                arr[index++] = right[ri++];
            }
        }
        if (li < m) {
            for (int i = li; i < m; i++) {
                arr[index++] = left[i];
            }
        }
        if (ri < n) {
            for (int i = ri; i < n; i++) {
                arr[index++] = right[i];
            }
        }
        return arr;
    }
}
