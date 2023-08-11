package com.lynch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/greatest-sum-divisible-by-three/description/
 *
 * 给你一个整数数组 nums，请你找出并返回能被三整除的元素最大和。
 *
 * 示例 1：
 *
 * 输入：nums = [3,6,5,1,8]
 * 输出：18
 * 解释：选出数字 3, 6, 1 和 8，它们的和是 18（可被 3 整除的最大和）。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/11 14:41
 */
public class MaxSumDivThree {

    @Test
    public void test() {
//        int[] arr = {3, 6, 5, 1, 8};
//        int[] arr = {2,6,2,2,7};
        int[] arr = {1, 1, 3};
        int maxSumDivThree = getMaxSumDivThree(arr);
        System.out.println("max sum dev three: " + maxSumDivThree);
    }

    public int getMaxSumDivThree(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int sum = 0;
        List<Integer> oneList = new ArrayList<>();
        List<Integer> twoList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (arr[i] % 3 == 1) {
                oneList.add(arr[i]);
            }
            if (arr[i] % 3 == 2) {
                twoList.add(arr[i]);
            }
        }

        oneList.sort((a, b) -> a - b);
        twoList.sort((a, b) -> a - b);
        if (sum % 3 == 0) {
            return sum;
        }

        // 总和除3余1说明需要移除一个1，为了使得结果除3余0则分两种情况
        // 1. 可以从oneList中随机选取一个，使用总数减去该数即可
        // 2. 可以从twoList中选取两个来组成一个 1，使用总数减去该数即可
        // 但是为了使得结果的总和最大，所以减去的数必然是较小的一个方可生效
        if (sum % 3 == 1) {

            if (twoList.size() >= 2) {
                if (oneList.isEmpty()) {
                    return sum - twoList.get(0) - twoList.get(1);
                }
                if (twoList.get(0) + twoList.get(1) < oneList.get(0)) {
                    return sum - twoList.get(0) - twoList.get(1);
                }
            }
            return sum - oneList.get(0);
        }

        // 总和除3余2说明需要移除一个2，为了使得结果除3余0则分两种情况
        // 1. 可以从oneList中随机选取两个，使用总数减去该数即可
        // 2. 可以从twoList中选取一个，使用总数减去该数即可
        // 但是为了使得结果的总和最大，所以减去的数必然是较小的一个方可生效
        if (sum % 3 == 2) {
            if (oneList.size() >= 2) {
                if (twoList.isEmpty()) {
                    return sum - oneList.get(0) - oneList.get(1);
                }
                if (oneList.get(0) + oneList.get(1) < twoList.get(0)) {
                    return sum - oneList.get(0) - oneList.get(1);
                }
            }
            return sum - twoList.get(0);
        }
        return -1;
    }

}
