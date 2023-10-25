package com.lynch.permutation;

import org.junit.Test;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/next-permutation/description/?envType=study-plan-v2&envId=top-100-liked
 *
 * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
 *
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，
 * 如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列
 * 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，
 * 那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
 *
 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/23 14:10
 */
public class NextPermutation {

    @Test
    public void test() {
//        int[] arr = {1,2,3,5,4};
        int[] arr = {4, 2, 0, 2, 3, 2, 0};
//        int[] arr = {5,4,3,2,1};
        nextPermutation(Arrays.copyOf(arr, arr.length));
        nextPermutation2(Arrays.copyOf(arr, arr.length));
    }

    /**
     * 解答错误 214 / 266 个通过的测试用例
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        // 求下一个排列，即求当前排列从后往前找到一个大数，并与前一个较小的数进行交换
        // 同时交换后 要求大数后面的数字必须按升序进行排列

        // 12345 -> 12354, 即 5 与前面较小的数4进行交换
        // 12354 -> 12435, 即 4 与前一个较小的数3进行交换，并对4后面的数进行升序排列
        int position = -1;
        int maxIndex = 0;
        boolean flag = false;
        for (int i = nums.length - 1; i > -1; i--) {
            for (int j = i - 1; j > -1; j--) {
                if (nums[j] < nums[i]) {
                    position = j;
                    maxIndex = i;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }

        if (position == -1) {
            Arrays.sort(nums);
            System.out.println(Arrays.toString(nums));
            return;
        }

        int temp = nums[position];
        nums[position] = nums[maxIndex];
        nums[maxIndex] = temp;

        Arrays.sort(nums, position + 1, nums.length);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 正确解法
     * @param nums
     */
    public void nextPermutation2(int[] nums) {
        // 首先从后往前寻找到第一个相对升序的组合 i j
        // 目的就是为了找到一对紧邻的数，使得排列结果更加密切
        int position = -1;
        for (int i = nums.length - 1; i > -1; i--) {
            if (nums[i-1] < nums[i]) {
                position = i - 1;
                break;
            }
        }
        // 没有找到更大的数 则直接打印最小排列
        if(position==-1){
            Arrays.sort(nums);
            System.out.println(Arrays.toString(nums));
            return;
        }

        // 从后往前找到第一个大于 nums[position]的数
        int maxIndex = -1;
        for (int j = nums.length - 1; j > position; j--) {
            if (nums[j] > nums[position]) {
                maxIndex = j;
                break;
            }
        }

        // 接着将 position 位置与 maxIndex 位置进行交换
        int temp = nums[position];
        nums[position] = nums[maxIndex];
        nums[maxIndex] = temp;

        // 将交换位置后的所有数字按升序就行排列， 保证当前排序最小
        Arrays.sort(nums, position + 1, nums.length);
        System.out.println(Arrays.toString(nums));
    }
}
