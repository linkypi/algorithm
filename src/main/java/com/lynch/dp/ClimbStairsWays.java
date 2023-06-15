package com.lynch.dp;

/**
 * https://leetcode.cn/problems/climbing-stairs/
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/8 17:18
 */
public class ClimbStairsWays {
    public static void main(String[] args) {
        int result = calc(4);
        System.out.println("result: "+ result);
    }

    /**
     * 令 dp[i] 为走 i 阶楼梯可以使用的方法数
     * dp[i-1] 表示走 i-1 阶楼梯的方法数，再走一阶即可到达 i
     * dp[i-2] 表示走 i-2 阶楼梯的方法数，再走两阶即可到达 i
     * 所以有 dp[i] = dp[i-1] + dp[i-2]
     *
     * @return
     */
    public static int calc(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
