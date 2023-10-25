package com.lynch.random;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * https://leetcode.cn/problems/random-pick-index/description/
 *
 * 给你一个可能含有 重复元素 的整数数组 nums ，请你随机输出给定的
 * 目标数字 target 的索引。你可以假设给定的数字一定存在于数组中。
 *
 * 实现 Solution 类：
 *
 * Solution(int[] nums) 用数组 nums 初始化对象。
 * int pick(int target) 从 nums 中选出一个满足 nums[i] == target
 * 的随机索引 i 。如果存在多个有效的索引，则每个索引的返回概率应当相等。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/23 15:43
 */
public class RandomIndex {
    @Test
    public void test(){

    }

    class Solution {

        private int[] nums = null;
        private HashMap<Integer, List<Integer>> map = new HashMap<>();
        private Random random = new Random();

        public Solution(int[] nums) {
            // 方法一：哈希表法， 记录每个数在数组中存放的索引下标列表，执行pick函数时通过 random 来随机获取列表下标值
            this.nums = nums;
            for (int i = 0; i < nums.length; i++) {
                int item = nums[i];
                map.putIfAbsent(item, new ArrayList<>());
                map.get(item).add(i);
            }
        }

        public int pick(int target) {
            if (!map.containsKey(target)) {
                return -1;
            }

            List<Integer> list = map.get(target);
            return list.get(random.nextInt(list.size()));
        }

        public int pick2(int target) {
            int ans = 0;
            for (int i = 0, cnt = 0; i < nums.length; ++i) {
                if (nums[i] == target) {
                    ++cnt; // 第 cnt 次遇到 target
                    if (random.nextInt(cnt) == 0) {
                        ans = i;
                    }
                }
            }
            return ans;
        }

    }
}
