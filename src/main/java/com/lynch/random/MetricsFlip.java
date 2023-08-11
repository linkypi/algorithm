package com.lynch.random;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 给你一个 m x n 的二元矩阵 matrix ，且所有值被初始化为 0 。请你设计一个算法，
 * 随机选取一个满足 matrix[i][j] == 0 的下标 (i, j) ，并将它的值变为 1 。
 * 所有满足 matrix[i][j] == 0 的下标 (i, j) 被选取的概率应当均等。
 *
 * 尽量最少调用内置的随机函数，并且优化时间和空间复杂度。
 *
 * 实现 Solution 类：
 *
 * Solution(int m, int n) 使用二元矩阵的大小 m 和 n 初始化该对象
 * int[] flip() 返回一个满足 matrix[i][j] == 0 的随机下标 [i, j] ，并将其对应格子中的值变为 1
 * void reset() 将矩阵中所有的值重置为 0
 *
 * 输入
 *    ["Solution", "flip", "flip", "flip", "reset", "flip"]
 *    [[3, 1], [], [], [], [], []]
 * 输出
 *    [null, [1, 0], [2, 0], [0, 0], null, [2, 0]]
 *
 * 解释
 * Solution solution = new Solution(3, 1);
 * solution.flip();  // 返回 [1, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
 * solution.flip();  // 返回 [2, 0]，因为 [1,0] 已经返回过了，此时返回 [2,0] 和 [0,0] 的概率应当相同
 * solution.flip();  // 返回 [0, 0]，根据前面已经返回过的下标，此时只能返回 [0,0]
 * solution.reset(); // 所有值都重置为 0 ，并可以再次选择下标返回
 * solution.flip();  // 返回 [2, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/9 15:03
 */
public class MetricsFlip {

    @Test
    public void test() {
         int n = 12321;
        String i = longestPalindrome("abcxcbt");
        System.out.println("result:" + i);
    }

    public String longestPalindrome(String s) {
        if (s == null || s.equals("")) {
            return "";
        }

        int n = s.length();
        //从i到j的字符是否为回文子串
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i-j < 3) {
                        dp[j][i] = true;
                    } else {
                        dp[j][i] = dp[j+1][i - 1];
                    }
                }
            }
        }
        int max = -1;
        int start = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j]) {
                    int temp = j - i + 1;
                    if (temp > max) {
                        max = temp;
                        start = i;
                        System.out.println("update range : " + i + " " + j);
                    }
                }
            }
        }
        return s.substring(start, max + 1);
    }


    public static class Solution {
        int total = 0;
        int m = 0;
        int n = 0;
        Random random = new Random();
        Map<Integer, Integer> map = new HashMap<>();

        public Solution(int m, int n) {
            this.total = m * n - 1;
            this.m = m;
            this.n = n;
        }

        public int[] flip() {
            // 随机选取0到total-1中的一个数
            int ran = random.nextInt(total - 1);
            // 查看随机树ran是否已映射到指定位置，若未映射则返回当前值ran
            int index = map.getOrDefault(ran, ran);
            // 将当前值ran映射到末尾，即有效部分在前，无效部分在后
            map.put(ran, map.getOrDefault(total, total));
            total--;
            return new int[]{index / n, index % n};
        }

        public void reset() {
            map = new HashMap<>();
            total = m * n - 1;
        }
    }
}
