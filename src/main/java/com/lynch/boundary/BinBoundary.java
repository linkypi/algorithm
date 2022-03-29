package com.lynch.boundary;

/**
 * 通过二分寻找左右边界
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/3/28 22:41
 */
public class BinBoundary {
    public static void main(String[] args) {
        int[] arr = {2, 5, 6, 6, 6, 8, 9, 12, 15};
        int leftBound = findLeftBound(arr, 6);
        int rightBound = findRightBound(arr, 6);

        int i = left_bound(arr, 6);
        int j = right_bound(arr, 6);
        System.out.println("left bound: " + leftBound);
        System.out.println("right bound: " + rightBound);
    }

    static int left_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 当找到 target 时，收缩右侧边界
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        return left;
    }

    static int right_bound(int[] nums, int target) {
        if (nums.length == 0) return -1;
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 当找到 target 时，收缩左侧边界
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        return left - 1;
    }

    private static int findRightBound(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (target == arr[mid]) {
                // 因为寻找的是右边界 所以需要向右边收缩，所以有 mid+1
                left = mid + 1;
            } else if(target > arr[mid]) {
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        return left - 1;
    }

    private static int findLeftBound(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (target == nums[mid]) {
                right = mid;
            } else if(target < nums[mid]){
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
