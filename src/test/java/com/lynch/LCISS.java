package com.lynch;

import java.util.List;

/**
 * 给定一个数组，求最长递增子序列
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/16 9:12
 */
public class LCISS {
    public static void main(String[] args) {
//        int[] arr = {1, 2, 3, 3, 4, 5, 6, 6, 7, 8};
//        int[] arr = {1, 3, 3, 3, 4};
        int[] arr = {1, 3};
        int leftBound1 = findLeftBound(3, arr);
        int leftBound2 = findLeftBound(6, arr);

        int rightBound1 = findRightBound(3, arr);
        int rightBound2 = findRightBound(6, arr);

        int[] arr2 = {8,2,9,5,7,8,9};
        int length = find(arr2);

        System.out.println("left bound: " + leftBound1);
    }

    static int findLeftBound(int target, int[] arr) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = (left + right) >> 1;
            if(arr[mid] == target){
                // 此处不能写成  right = mid - 1; 因为搜索区间是左闭右开区间 [left, right)
                // 若此时减 1 就会导致遗漏一个元素未被搜索到，如 第一次 mid = 5，逻辑刚好走到此处
                // 若此时 right = 5-1= 4 ，搜索区间变为 [left, 4) 而非 [left, 5) 如此会导致
                // 索引4的元素会被忽略，因为搜索的范围是左闭右开区间，所以无需减 1
                right = mid;
            }else if (arr[mid] > target){
                // 此处同样不能写成  right = mid - 1; 因为搜索区间是左闭右开区间 [left, right)
                // 若此时减 1 就会导致遗漏一个元素未被搜索到，如 第一次 mid = 5，逻辑刚好走到此处
                // 若此时 right = 5-1= 4 ，搜索区间变为 [left, 4) 而非 [left, 5) 如此会导致
                // 索引4的元素会被忽略，因为搜索的范围是左闭右开区间，所以无需减 1
                right = mid;
            }else if(arr[mid] < target){
                left = mid+1;
            }
        }
        return left;
    }

    static int findRightBound(int target, int[] arr) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = (left + right) >> 1;
            if(arr[mid] == target){
                // 向右扩大搜索区间，由于循环终止条件是 left==right 所以在返回时需要减去1
                left = mid + 1;
            }else if(arr[mid] > target){
                // 此处同样不能写成  right = mid - 1; 因为搜索区间是左闭右开区间 [left, right)
                // 若此时减 1 就会导致遗漏一个元素未被搜索到，如 第一次 mid = 5，逻辑刚好走到此处
                // 若此时 right = 5-1= 4 ，搜索区间变为 [left, 4) 而非 [left, 5) 如此会导致
                // 索引4的元素会被忽略，因为搜索的范围是左闭右开区间，所以无需减 1
                right = mid;
            }else if(arr[mid] < target){
                left = mid + 1;
            }
        }
        return left - 1;
    }

    static int find(int[] arr){
        int n = arr.length;
        int[] buckets = new int[n];
        buckets[0] = arr[0];

        int index = 1;
        int size = 1;
        while(index < n){
            int item = arr[index];

            // 使用二分法找到左边界
            int left = 0;
            int right = size;
            while(left < right){
                int mid = (left + right) >> 1;
                if(buckets[mid] == item){
                    right = mid;
                }else if(buckets[mid] > item){
                    right = mid;
                }else if(buckets[mid] < item){
                    left = mid + 1;
                }
            }

            // 若左边界已经超出当前桶索引则增加新桶
            if(left == size){
                size ++;
            }
            buckets[left] = item;
            index ++;
        }
        return size;
    }
}
