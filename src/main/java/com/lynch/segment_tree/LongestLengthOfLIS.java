package com.lynch.segment_tree;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/longest-increasing-subsequence-ii/description/
 *
 * 给你一个整数数组 nums 和一个整数 k 。
 * 找到 nums 中满足以下要求的最长子序列：
 * 子序列 严格递增, 子序列中相邻元素的差值 不超过 k 。
 * 请你返回满足上述要求的 最长子序列 的长度。
 *
 * 子序列 是从一个数组中删除部分元素后，剩余元素不改变顺序得到的数组。
 * 示例 1：
 * 输入：nums = [4,2,1,4,3,4,5,8,15], k = 3
 * 输出：5
 * 解释：
 * 满足要求的最长子序列是 [1,3,4,5,8] 。
 * 子序列长度为 5 ，所以我们返回 5 。
 * 注意子序列 [1,3,4,5,8,15] 不满足要求，因为 15 - 8 = 7 大于 3 。
 *
 * 示例 2：
 * 输入：nums = [7,4,5,1,8,12,4,7], k = 5
 * 输出：4
 * 解释：
 * 满足要求的最长子序列是 [4,5,8,12] 。
 * 子序列长度为 4 ，所以我们返回 4 。
 * @author: lynch
 * @description:
 * @date: 2024/2/24 20:32
 */
public class LongestLengthOfLIS {

    @Test
    public void test() {
        int[] arr = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int len = lengthOfLIS(arr, 3);
        System.out.println("length: " + len);
    }

    public int lengthOfLIS(int[] nums, int k) {
        Line line = new Line();
        int ans = 0;
        for (int num : nums) {
            int max = line.getMax(num - k, num - 1);
            ans = Math.max(max + 1, ans);
            line.setVal(num, max + 1);
        }
        return ans;
    }

    static class Line {
        int start;
        int end;
        Line left;
        Line right;
        int maxVal;

        public Line() {
            start = 0;
            end = (int) (1e6 + 1);
        }

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getMax(int L, int R) {
            if (start > R || end < L) return 0;
            if (start >= L && end <= R) return maxVal;
            int m1 = left != null ? left.getMax(L, R) : 0;
            int m2 = right != null ? right.getMax(L, R) : 0;
            return Math.max(m1, m2);
        }

        public void setVal(int pos, int V) {
            if (start > pos || end < pos) return;
            maxVal = Math.max(V, maxVal);
            if (start == end) return;

            int mid = (end - start) / 2 + start;
            if (pos <= mid) {
                if (left == null) left = new Line(start, mid);
                left.setVal(pos, V);
            } else {
                if (right == null) right = new Line(mid + 1, end);
                right.setVal(pos, V);
            }
        }
    }
}
