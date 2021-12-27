package com.lynch.extern;

/**
 * 给定N个非负整数表示每个每个坐标中的点，坐标中的数字表示桶的高度，
 * 找出其中两条线使得他们与x轴共同构成的容器可以容纳最多的水
 * Description algorithm
 * Created by troub on 2021/12/6 10:01
 */
public class CollectRainWater2 {
    public static void main(String[] args) {

    }

    /**
     * 判断每个位置是否可以收集雨水取决于其左边及右边的高度中的
     * 最小值 与 左右两边距离所构成的面积。求该面积的最大值即可
     * 算法空间复杂度为 O(N), 时间复杂度为 空间复杂度为 O(N)
     * @param arr
     * @return
     */
    private int collect(int[] arr) {
        int res = 0;
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int area = Math.min(arr[left], arr[right]) * (right - left);
            res = Math.max(res, area);
            if (arr[left] < arr[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }

}
