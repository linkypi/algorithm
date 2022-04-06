package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/2 16:16
 */
public class CollectWater {
    public static void main(String[] args) {
        int[] arr = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int count = trap(arr);
        System.out.println("count: " + count);
    }

    private static int collectOptimize(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        int lmax = 0, rmax = 0;
        int res = 0;
        while (left < right) {
            lmax = Math.max(arr[left], lmax);
            rmax = Math.max(arr[right], rmax);
            if (lmax < rmax) {
                res += lmax - arr[left];
                left++;
            } else {
                res += rmax - arr[right];
                right--;
            }
        }
        return res;
    }

    public static int trap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int lmax = 0;
        int rmax = 0;
        int water = 0;
        while (left <= right) {

            lmax = Math.max(lmax, height[left]);
            rmax = Math.max(rmax, height[right]);

            if(lmax < rmax){
                water += lmax - height[left];
                left++;
            }else{
                water += rmax - height[right];
                right--;
            }
        }
        return water;
    }
}
