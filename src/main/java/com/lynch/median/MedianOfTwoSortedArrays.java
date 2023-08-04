package com.lynch.median;

import org.junit.Test;

/**
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 * 请你找出并返回这两个正序数组的 中位数 。算法的时间复杂度应该为 O(log(m+n)) 。
 *
 * https://leetcode.cn/problems/median-of-two-sorted-arrays/description/
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/7/28 9:41
 */
public class MedianOfTwoSortedArrays {

    @Test
    public void test() {
//        int[] arr1 = {1, 2};
//        int[] arr2 = {3, 4, 5, 6, 7};
//        int mid = find(arr1, arr2);

        int[] arr1 = {1, 2, 9, 11};
        int[] arr2 = {3, 4, 5, 6, 7};
        double mid = find(arr1, arr2);
        double medianSortedArrays = findMedianSortedArrays(arr1, arr2);
        System.out.println("mid: "+ mid);
    }

    /**
     * 一般想到的解法是使用归并排序将数组合并后，根据奇偶数求解即可，但该方法的算法复杂度无法满足 O(log(m+n))
     * 既然复杂度存在 log 那么势必离不开二分查找算法。
     * @param arr1
     * @param arr2
     * @return
     */
    private double find(int[] arr1, int[] arr2) {
        // 数组中位数取决于总数组长度 n 是奇数还是偶数
        // 1. 若是奇数，则中位数是 n/2 位置的数
        // 2. 若是偶数，则中位数是 n/2 与 n/2-1 两个位置的数的平均数
        int m = arr1.length;
        int n = arr2.length;

        int total = m + n;
        int k = total / 2;

        if (total % 2 == 0) {
            int a = findKthMin(arr1, 0, arr2, 0, k);
            int b = findKthMin(arr1, 0, arr2, 0, k+1);
            return (a + b) / 2.0;
        }
        return findKthMin(arr1, 0, arr2, 0, k + 1);
    }

    /**
     * 在两个正序数组中寻找第 k 小的数,
     * 默认 arr1 长度比 arr2 长度短
     * @param arr1
     * @param i ，arr1 的开始位置
     * @param arr2
     * @param j  arr2 的开始位置
     * @param k  第 K 小
     * @return
     */
    private int findKthMin(int[] arr1, int i, int [] arr2, int j, int k) {

        // 默认 arr1 长度小于 arr2, 若 arr1 长度大于 arr2 则翻转
        if (arr1.length - i > arr2.length - j) {
            return findKthMin(arr2, j, arr1, i, k);
        }
        // 由于arr1长度较短，故 arr1 指针必先到达边界
        // 此时中位数直接在 arr2 中获取即可
        if (i > arr1.length - 1) {
            return arr2[j + k - 1];
        }
        // 已经寻找到第一小的位置，则最小必定是两者中的较小的一个
        if (k == 1) {
            return Math.min(arr1[i], arr2[j]);
        }
        // 分别对两个数组进行二分，虽然比较两者中位数的大小
        // 对两个数组各自分配一半的排名，arr1 分配 k/2的排名， arr2 分配剩下的 k-k/2 排名
        // 由于索引是从 0 开始，故分配排名后实际做比较时仍需减去1，如当前索引 i=0, 分配的排名是 2，
        // 若直接使用 i+k=2 此时索引已经走到第三个数的位置
        int m1 = Math.min(arr1.length, i + (k / 2));
        int m2 = j + k - (k / 2);

        // 若 arr1[m1] 大于 arr2[m2] ，说明中位数只可能出现在 arr1，此处就可以排除 arr2 位置 m+1 之前的数
        // 继续从 arr2 后面 m+1 到末尾位置进行比较来获得中位数，由于已排除 k/2 个数，故后面仅需求 (k-k/2) 小的数即可
        if (arr1[m1 - 1] > arr2[m2 - 1]) {
            // 剔除 arr2 位置索引 m2 前面的数据，使用 m2, arr2.length - 1 与 arr1 数组进行比较
            // 已经移除 (m2 - j) 个数，此时仅需求出第 k - (m2 - j) 小即可
            return findKthMin(arr1, i, arr2, m2, k - (m2 - j));
        }
        // 剔除 arr1 位置索引 m1 前面的数据，使用 m1, arr1.length - 1 与 arr2 数组进行比较
        // 已经移除 (m1 - i) 个数，此时仅需求出第 k - (m1 - i) 小即可
        return findKthMin(arr1, m1, arr2, j, k - (m1 - i));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int tot = nums1.length + nums2.length;
        if (tot % 2 == 0) {
            int left = find(nums1, 0, nums2, 0, tot / 2);
            int right = find(nums1, 0, nums2, 0, tot / 2 + 1);
            return (left + right) / 2.0;
        } else {
            return find(nums1, 0, nums2, 0, tot / 2 + 1);
        }
    }
    int find(int[] n1, int i, int[] n2, int j, int k) {
        if (n1.length - i > n2.length - j)
            return find(n2, j, n1, i, k);

        if (i >= n1.length) return n2[j + k - 1];
        if (k == 1) {
            return Math.min(n1[i], n2[j]);
        } else {
            int si = Math.min(i + (k / 2), n1.length);
            int sj = j + k - (k / 2);
            if (n1[si - 1] > n2[sj - 1]) {
                return find(n1, i, n2, sj, k - (sj - j));
            } else {
                return find(n1, si, n2, j, k - (si - i));
            }
        }
    }
}
