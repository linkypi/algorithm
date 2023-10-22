package com.lynch.game_theory;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/predict-the-winner/description/
 *
 * 给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
 *
 * 玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。
 * 每一回合，玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），
 * 取到的数字将会从数组中移除（数组长度减 1 ）。
 * 玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。
 *
 * 如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，
 * 也返回 true 。你可以假设每个玩家的玩法都会使他的分数最大化。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/20 9:48
 */
public class PredictTheWinner {
    @Test
    public void test() {
        int[] arr = {1, 5, 2};
        int[] arr2 = {1, 5, 233, 7};
        boolean result = find(arr);
        System.out.println("result: " + result);
    }

    private boolean find(int[] arr) {
        int n = arr.length;
        // dp[i][j] 表示 从 i 到 j 中先手与后手差额的最大值
        // 1. i == j 时，此时只能是先手赢, 因为只有一个数，dp[i][j] = arr[i]
        // 2. 当 i > j 时，越界，dp[i][j] = 0
        // 3. 当 i < j 时，
        //       dp[i][j] = max( arr[i] - dp[i+1][j], arr[j] - dp[i][j-1] )
        //       即 当前i..j之间的结果取决与其 左边前一个单元格 以及 下方一个单元格数值（以i为行，j为列）
        //       同时还有 i < j ，所以实际计算方式应该是从下往上，从左往右
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = arr[i];
        }

        // 当前i..j之间的结果取决与其 左边前一个单元格 以及 下方一个单元格数值（以i为行，j为列）
        // 同时还有 i < j ，所以实际计算方式应该是从下往上，从左往右
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Math.max(arr[i] - dp[i + 1][j], arr[j] - dp[i][j - 1]);
            }
        }

        return dp[0][n - 1] >= 0;
    }
}
