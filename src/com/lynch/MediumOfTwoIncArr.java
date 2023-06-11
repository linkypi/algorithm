package com.lynch;

/**
 * 在两个有序数组中寻找中位数
 */
public class MediumOfTwoIncArr {
    public static void main(String[] args) {

//        int[] arr1 = {1,7,9};
//        int[] arr2 = {2,4,6,8,10};

//        int[] arr1 = {8, 9};
//        int[] arr2 = {2, 4, 6};

        int[] arr1 = {1,2};
        int[] arr2 = { 4, 6, 8};

//        int[] arr1 = {1,3};
//        int[] arr2 = { 2 };

//        int[] arr1 = {1,2};
//        int[] arr2 = { 3,4 };

//        int[] arr1 = {};
//        int[] arr2 = { 2,3};

//        double mid = find(arr1, arr2);
        double mid= findMedianSortedArrays2(arr1, arr2);
        System.out.println("result: " + mid);
    }

    public static double findMedianSortedArrays2(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            return findMedianSortedArrays2(B,A); // 保证 m <= n
        }
        int iMin = 0, iMax = m;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = (m + n + 1) / 2 - i;
            if (j != 0 && i != m && B[j-1] > A[i]){ // i 需要增大
                iMin = i + 1;
            }
            else if (i != 0 && j != n && A[i-1] > B[j]) { // i 需要减小
                iMax = i - 1;
            }
            else { // 达到要求，并且将边界条件列出来单独考虑
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; } // 奇数的话不需要考虑右半部分

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0; //如果是偶数的话返回结果
            }
        }
        return 0.0;
    }



    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    private static int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        }
        else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

    public static double find(int[] arr1, int[] arr2) {
        int m = arr1.length;
        int n = arr2.length;

        if (m > n) {
            return find(arr2, arr1);
        }

        if (m + n == 1) {
            return m > 0 ? arr1[0] : arr2[0];
        }

        int leftCount = (m + n + 1) / 2; //   确保 左边数量比右边最多多一个

        // i 为数组 1 的分割线, 将数组一分为二, i 包含在左半部分中
        // j 为数组 2 的分割线, 将数组一分为二, j 包含在左半部分中
        // 由于两个数组都是升序数组,则只要确保数组 1 及数组 2 的左半部分数
        // 都小于等于数组 1 及数组 2 的右半部分数, 那么 i,j 位置附近的数即为中位数
        // 首先从中间开始
        int i = (m + 1) / 2;
        while (i >= m) {
            i--;
        }
        int j = leftCount - (i + 1) - 1;
        while (true) {

            // 若数组 1 左边最大值 大于 数组 2 的右边最小值
            // 则需要调整分割位置
            if (i > -1 && j + 1 < n && arr1[i] > arr2[j + 1]) {
                i--;
                // 为保证左边数量总是大于等于右边数量 故同时需要调整数组 2 的分割线
                j++;
            }

            // 若数组 2 左边最大值 大于数组 1 右边最小值 则需要调整分割位置
            else if (i + 1 < m && j > -1 && arr1[i + 1] < arr2[j]) {
                // 为保证左边数量总是大于等于右边数量 故同时需要调整数组 1 的分割线
                j--;
                i++;
            }
            // 满足条件则可得到结果
            else {
                int leftMax = 0;
                int rightMin = 0;
                if (i == -1) {
                    // 分割线i在数组 1 的最左侧, 则左边最大值必定来自数组 2
                    leftMax = arr2[j];
                    int a = m == 0 ? Integer.MAX_VALUE : arr1[0];
                    int b = j == n - 1 ? Integer.MAX_VALUE : arr2[j - 1];
                    rightMin = Math.min(a, b);
                } else if (i == m) {
                    // 分割线 i 在数组 1 的最右侧,则左边最大值应该由两个数组的左侧共同决定
                    leftMax = Math.max(arr1[i - 1], j == -1 ? Integer.MIN_VALUE : arr2[j]);
                    rightMin = arr2[j + 1];
                }
                if (j == -1) {
                    leftMax = arr1[i];
                    rightMin = Math.min(arr2[j + 1], i == m - 1 ? Integer.MAX_VALUE : arr1[i + 1]);
                } else if (j == n) {
                    leftMax = Math.max(arr2[j - 1], i == -1 ? Integer.MIN_VALUE : arr1[i]);
                    rightMin = arr1[i + 1];
                }
                if (i > -1 && i < m && j > -1 && j < n) {
                    leftMax = Math.max(arr1[i], arr2[j]);
                    int a = i + 1 < m ? arr1[i + 1] : Integer.MAX_VALUE;
                    int b = j + 1 < n ? arr2[j + 1] : Integer.MAX_VALUE;
                    rightMin = Math.min(a, b);
                }
                if ((m + n) % 2 == 0) {
                    return (leftMax + rightMin) / 2.0;
                } else {
                    return leftMax;
                }
            }
        }
    }
}
