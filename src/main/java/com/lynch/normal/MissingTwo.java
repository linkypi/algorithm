package com.lynch.normal;

import com.lynch.tools.Utils;

import java.util.Arrays;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/1 14:23
 */
public class MissingTwo {
    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 5, 6, 7, 8, 9};
        int[] ints = missingTwo(arr);
        int[] ints2 = missingTwoWithXor(arr);
        Utils.printArr("result: ", ints2);
    }

    public static int[] missingTwoWithXor(int[] nums) {
        int n = nums.length + 2;
        // 记录缺失的两数异或结果
        // nums 数组 及 1...N 中相同的数通过异或后会抵消，最后剩下
        int xor = 0;
        for (int i = 0; i < nums.length; i++) {
            xor ^= nums[i];
        }
        for (int i = 1; i <= n; i++) {
            xor ^= i;
        }

        // 获取最右边的 1 所在位置
        int temp = xor;
        int count = 0;
        while ((temp & 1) != 1) {
            count++;
            temp >>>= 1;
        }

        int num = 0;
        // nums 数组 及 1...N 中相同的数通过异或后会抵消，剩下的
        // 两个缺失的数通过 第 count 位是否为 1 来区分
        for (int i = 0; i < nums.length; i++) {
            if (((nums[i] >> count) & 1) == 1) {
                num ^= nums[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            if (((i >> count) & 1) == 1) {
                num ^= i;
            }
        }

        return new int[]{num, xor ^ num};
    }

    public static int[] missingTwo(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        Arrays.sort(nums);

        // 最小值距离 1 很远则直接返回无效
        if (nums[0] >= 4 || nums[0] - 1 < 0) {
            return new int[]{0, 0};
        }

        if (nums.length == 1) {
            if (nums[0] == 1) {
                return new int[]{2, 3};
            }
            if (nums[0] == 2) {
                return new int[]{1, 3};
            }
            if (nums[0] == 3) {
                return new int[]{1, 2};
            }
        }

        boolean continuous = true;
        int[] result = new int[2];
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] > 3) {
                return new int[]{0, 0};
            }
            // 刚好缺失2个
            if (nums[i] - nums[i - 1] == 3) {
                int min = nums[i - 1];
                return new int[]{min + 1, min + 2};
            }
            if (nums[i] - nums[i - 1] == 2) {
                continuous = false;
                result[index++] = nums[i - 1] + 1;
                if (index == 2) {
                    return result;
                }
            }
        }
        if (continuous) {
            if (nums[0] == 2) {
                return new int[]{nums[0] - 1, nums[nums.length - 1] + 1};
            }
            if (nums[0] == 3) {
                return new int[]{nums[0] - 2, nums[0] - 1};
            }
        }
        if (index == 1) {
            if (nums[0] == 1) {
                result[1] = nums[nums.length-1] + 1;
            } else {
                result[1] = 1;
            }
        }
        return result;
    }
}
