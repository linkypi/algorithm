package com.lynch.extern;

/**
 * 给定N个非负整数表示每个宽度为1的柱子的高度，计算按此排列的柱子，下雨后可以接多少雨水
 * Description algorithm
 * Created by troub on 2021/12/6 10:01
 */
public class CollectRainWater {
    public static void main(String[] args) {

    }

    /**
     * 判断每个位置是否可以收集雨水取决于其左边及右边的最高柱子
     * 此方法会记录下下 i 位置左边最大值及 i 位置右边最大值
     * 那么 i 位置可以接的水量为 左右两边最大值中的最小值 与 当前位置 i 的高度差
     * 算法空间复杂度为 O(N), 时间复杂度为 空间复杂度为 O(N)
     * @param arr
     * @return
     */
    private int collect(int[] arr) {
        int[] lmax = new int[arr.length];
        int[] rmax = new int[arr.length];

        for (int index = 1; index < arr.length; index++) {
            lmax[index] = Math.max(arr[index], lmax[index-1]);
        }

        for (int index = arr.length - 2; index > -1; index--) {
            rmax[index] = Math.max(arr[index], rmax[index + 1]);
        }

        int result = 0;
        for (int index = 0; index < arr.length; index++) {
            result += (Math.min(lmax[index], rmax[index])) - arr[index];
        }
        return result;
    }

    private int collectOptimize(int[] arr) {
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
}
