package com.lynch.extern;

public class FindNumberInOrderedArr {
    public static void main(String[] args) {
        int[] arr = new int[]{-2, 0, 1, 3, 4, 6, 8, 10};
        int index = findNumberWithBS(8, arr);
        System.out.println("index: " + index);
    }

    /**
     * 案例 1 ：在一个有序数组中查找一个数, 并返回其位置
     * 使用二分查找即可
     * @return
     */
    private static int findNumberWithBS(int x, int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1) {
            return arr[0] == x ? 1 : -1;
        }

        return find(x, arr, 0, arr.length - 1);
    }

    private static int find(int x, int arr[], int start, int end) {
        int mid = start + (end - start + 1) >> 1;

        // x 在数组的左边
        if (arr[mid] > x) {
            return find(x, arr, start, mid - 1);
        }
        // x 在数组的右边
        if (arr[mid] < x) {
            return find(x, arr, mid + 1, end);
        }
        return mid;
    }

    // 案例2： 在一个无序数组中 任何两个数一定不相等, 求局部最小
    // 1. 对于 0 位置来说,如果 0 位置的数小于 1 位置的数则 0 位置即为局部最小位置
    // 2. 对于最后位置 N-1, 若 N-2 位置的数大于 N-1 位置的数 则 N-1 即为局部最小位置
    // 3. 对于中间位置 i, 若 i-1 > i < i+1 则 i 位置为局部最小位置
    // 对于该问题同样可以使用二分查找, 二分不一定需要数组有序

}
