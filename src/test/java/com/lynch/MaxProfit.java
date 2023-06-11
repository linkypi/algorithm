package com.lynch;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/24 9:51
 */
public class MaxProfit {
    public static void main(String[] args) {
//         int[] arr = {7,1,5,3,6,4};
         int[] arr = {7,6,4,3,1};
         int result = maxProfit(arr);
         System.out.println("result: "+ result);
    }

    public static int maxProfit(int[] prices) {
        if(prices==null || prices.length ==0){
            return 0;
        }
        int n = prices.length;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            if(prices[i] - min>0){
                max = Math.max(prices[i] - min, max);
            }
            min = Math.min(prices[i], min);
        }
        return max < 0 ? 0: max;
    }

    private static int maxProfit1(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];

        // 第一天持有
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            // 第i 天没有持有股票：1. 前一天就没有持有，2. 前一天持有，今天卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 第i天持有股票： 1. 前一天就持有， 2. 前一天没有持有，今天刚好买入
            // 注意第2种情况买入时不能把 dp[i-1][0] 计算进去，因为每次交易都是独立的，
            // 而dp[i-1][0] 表示的是第 i-1 天没有持有股票时的最大利润
            dp[i][1] = Math.max(dp[i - 1][1], - prices[i]);
        }
        return dp[n - 1][0];
    }
}
