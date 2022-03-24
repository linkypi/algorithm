package com.lynch.stock;

/**
 * 给定一个数组 prices ，其中prices[i] 表示股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润，你最多可以完成两笔交易。
 * 注意，不能同时参与多笔交易，必须在再次购买前出售掉之前的股票。
 *
 * 注意该题仅可以进行两次交易 ！！！
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/24 22:01
 */
public class MaxProfit3 {
    public static void main(String[] args) {
        int[] arr = {3,3,5,0,0,3,1,4};
//        int[] arr = {1,2,3,4,5};
        int result = maxProfit(arr);
        System.out.println("result: "+ result);
    }

    private static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        int k = 2;

        // dp[i][j][0] 表示到第i天为止，在交易次数j的限制下不再持有股票的最大利润
        // dp[i][j][1] 表示到第i天为止，在交易次数j的限制下持有股票的最大利润
        int[][][] dp = new int[n][k + 1][2];

        // base case 第一天无论交易次数多大(>0)，其持有股票时的最大利润都是 -prices[0]
        for (int i = 1; i <= k; i++) {
            dp[0][i][1] = -prices[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                // 今天没有持有股票，可能前一天没有持有，或者前一天持有但今天卖出
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                // 今天持有股票，可能前一天已持有，或前一天没有持有，但今天刚好买入, 开始一笔交易(j-1)
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[n - 1][k][0];
    }

}
