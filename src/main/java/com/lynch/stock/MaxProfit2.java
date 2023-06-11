package com.lynch.stock;

/**
 * 给定一个数组 prices ，其中prices[i] 表示股票第 i 天的价格。
 * 在每一天，你可能会决定购买或出售股票。你在任何时候最多只能持有 一股 股票。
 * 你也可以购买它，然后在 同一天 出售。返回 你能获得的最大利润。
 *
 * 注意该题可以进行多次交易 ！！！
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/24 11:14
 */
public class MaxProfit2 {
    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 2, 5, 7, 3, 6};
//        int[] arr = {7,1,5,3,6,4};
        int result = maxProfit(arr);
        System.out.println("result: "+ result);
    }

    private static int maxProfit(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int dp00 = 0;
        int dp01 = -arr[0];
        for (int i = 0; i < n; i++) {
            // 第i 天没有持有股票：1. 前一天就没有持有，2. 前一天持有，今天卖出
            dp00 = Math.max(dp00, dp01 + arr[i]);
            // 第i天持有股票： 1. 前一天就持有， 2. 前一天没有持有，今天刚好买入
            // 注意第2种情况买入时需把 dp[i-1][0] （即dp00）计算进去，因为每次交易都是独立的，
            // 而dp[i-1][0] 表示的是第 i-1 天没有持有股票时的最大利润, 同时目标要求
            // 只能进行一次交易，所以不能出现交易结果累加的情况，只有可以多次交易的情况方才可以
            dp01 = Math.max(dp01, dp00 - arr[i]);
        }
        return dp00;
    }
}
