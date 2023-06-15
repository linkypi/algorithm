package com.lynch;

/**
 * 在两个有序数组中寻找中位数
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/24 9:01
 */
public class TwoArrMedium {
    public static void main(String[] args) {

    }


    public static int findCutOffIndex(int[] arr1, int[] arr2) {
        int i = -1;
        int j = 0;
        int m = arr1.length;
        int n = arr2.length;
        int leftCount = 0;
        int rightCount = (m+n+1)/2 - 1 ;

        while (leftCount < rightCount) {

            // 处理左边界
            if (i == -1) {

            } else {

            }

            // 数组1左边的最大值 大于 数组2右边的最小值 则需调整
            while (j + 1 < n && arr1[i] > arr2[j + 1]) {
                j++;
                leftCount ++;
            }
            // 数组2左边最大值 大于 数组1 右边最小值
            while (i + 1 < m && arr2[j] > arr1[i + 1]) {
                i++;
                leftCount++;
            }

            int left = i + j + 2;
            int right = m + n - left;
            if (arr2[j] <= arr1[i + 1] && arr1[i] <= arr2[j + 1]
                    && left - right >= 0 && left - right <= 1) {
                break;
            }
            leftCount ++;
        }

        if (leftCount - rightCount > 0) {
            return arr1[i];
        } else {
            return (arr1[i] + arr2[j + 1]) / 2;
        }
    }

//    public static int find(int[] arr1, int[] arr2){
//        int m = arr1.length;
//        int n = arr2.length;
//
//        if((m+n)%2 == 1){
//            // 若是奇数则取中间一个值即可
//
//        }else{
//            // 若是偶数则取中间两数的平均值
//
//        }
//    }
//
//    public static int kth(int[] arr1, int left, int[] arr2, int right, int k){
//
//        int mid = k/2;
//        if(arr1[mid] > arr2[mid]){
//            // 剔除 arr2 前 mid 部署元素
//
//        }
//        if(arr1[mid] < arr2[mid]){
//            // 剔除 arr1 前 mid 部署元素
//
//        }
//        if(arr1[mid] == arr2[mid]){
//
//        }
//    }
}
