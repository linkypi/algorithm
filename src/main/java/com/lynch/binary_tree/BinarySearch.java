package com.lynch.binary_tree;

/**
 * Description algorithm
 * Created by troub on 2021/12/10 15:57
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 4, 5, 7, 9, 12, 14, 18};
        int result = binSearch(arr, 14);
        System.out.println("bin search result index：  " + result);


        arr = new int[]{1, 2, 2, 3, 3, 4, 5};
        int leftBound = leftBound(arr, 2);
        int rightBound = rightBound(arr, 3);
        System.out.println("left bound index:  " + leftBound);
        System.out.println("right bound index:  " + rightBound);

    }

    private static int rightBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] == target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            }
        }
        return right;
    }

    /**
     * 使用二分查找确定左边界
     * @param arr
     * @param target
     * @return
     */
    private static int leftBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] == target) {
                right = mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid;
            }
        }
        return left;
    }

    /**
     * 二分查找
     * @param arr
     * @param target
     * @return
     */
    private static int binSearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            // 此处求解mid时不是直接使用 (left+right) / 2 而是使用
            // left + ((right - left) >> 1) 是为了防止 left+right 溢出
            int mid = left + ((right - left) >> 1);
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid - 1;
            }
        }
        return -1;
    }
}