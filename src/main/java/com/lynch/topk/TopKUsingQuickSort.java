package com.lynch.topk;

import java.util.Random;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/7 21:33
 */
public class TopKUsingQuickSort {
    public static void main(String[] args) {
         int[] arr =  {3,2,1,5,6,4};
        int kthLargest = findKthLargest(arr, 2);
        System.out.println("result: " + kthLargest);
    }

    static Random random = new Random();
    public static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public static int quickSelect(int[] a, int l, int r, int index) {
        int q = randomPartition(a, l, r);
        if (q == index) {
            return a[q];
        }

        if(q < index){
            return  quickSelect(a, q + 1, r, index);
        }
        return quickSelect(a, l, q - 1, index);
    }

    public static int randomPartition(int[] a, int l, int r) {
        int i = random.nextInt(r - l + 1) + l;
        swap(a, i, r);
        return partition(a, l, r);
    }

    public static int partition(int[] a, int l, int r) {
        int x = a[r], i = l - 1;
        for (int j = l; j < r; j++) {
            if (a[j] <= x) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
