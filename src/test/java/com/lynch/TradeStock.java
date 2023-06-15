package com.lynch;

/**
 * 买卖股票最佳时机
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/30 10:13
 */
public class TradeStock {
    public static void main(String[] args) {

    }

    /**
     * 买卖股票最佳时机， 最多允许交易 k 次
     * @param prices
     * @param k
     * @return
     */
    public static int find(int[] prices, int k) {

        int n = prices.length;
        // dp[i][j][0] 表示在第 i 天最多允许k次交易的情况 没有持有股票的最大收益
        // dp[i][j][0] 今天没有持有股票的最大收益取决于 前一天同样没有持有 与 前一天持有但是今天卖出 两者的最大值
        // dp[i][j][0] = max(dp[i-1][j][0] , dp[i-1][j][1] + price)

        // dp[i][j][1] 表示在第 i 天最多允许k次交易的情况下，持有股票的最大收益
        // dp[i][j][1] 今天持有股票的最大收益取决于 前一天同样持有 与 前一天未持有但是今天买入 两者的最大值
        // dp[i][j][1] = max(dp[i-1][j][1] , dp[i-1][j-1][0] - price)
        int[][][] dp = new int[n][k][2];

        for(int j =0;j<k;j++) {
            dp[0][j][0] = 0;
            dp[0][j][1] = -prices[0];
        }

        for (int i = 1; i < n; i++) {
            int price = prices[i];
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + price);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j-1][0] - price);
            }
        }

        return dp[n-1][k][0];
    }
}
