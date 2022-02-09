package com.lynch.stock;

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices [i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 * Created by troub on 2022/1/26 9:13
 */
public class MaxProfit {
    public static void main(String[] args) {
        int[] arr = new int[]{8, 9, 1, 2, 5, 7, 3, 6};
        final Info bestTime = getBestTime(arr);
        System.out.printf("max values: %d, index: %d%n", bestTime.value, bestTime.index);

        final Info max = getBestTime2(arr);
        System.out.printf("optimize max values: %d, index: %d%n", max.value, max.index);
    }

    private static int getBestTimeUseDp(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        /**
         * 一维表示第 i 天， 二维表示是否持有股票
         * dp[i][j] 即表示第 i 天持有股票与否所获得的最大利润
         */
        int[][] dp = new int[n][2];

        dp[0][0] = 0;
        // 未开始就已持有股票
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
        }
        return dp[n-1][0];
    }

    private static int getBestTimeUseDp2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        int dp0 = 0;
        int dp1 = -prices[0];

        for (int i = 1; i < n; i++) {
            int newDp0 = Math.max(dp0, dp1 + prices[i]);
            int newDp1 = Math.max(dp1, dp0 - prices[i]);
            dp0 = newDp0;
            dp1 = newDp1;
        }
        return dp0;
    }

    /**
     * 每次计算时记录下最佳买入的价格（最低价），然后与之后的价格
     * 做对比即可得到最大利润，整个过程只需遍历一遍，时间复杂度 O(N)
     *
     * @param arr
     * @return
     */
    private static Info getBestTime2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new Info(-1, 0);
        }
        int min = arr[0];
        int maxIndex = -1, maxValue = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] - min > maxValue) {
                maxIndex = i;
                maxValue = arr[i] - min;
            }
        }
        return new Info(maxIndex, maxValue);
    }

    /**
     * 逐个遍历，以当前价格为买入基准，其最佳卖出时间即后续价格中的最大值。
     * 比较完成后再将从每个最值中求最大即为所求，双重循环，时间复杂度 O(N2)
     *
     * @param arr
     * @return
     */
    private static Info getBestTime(int[] arr) {
        Info info = new Info(-1, 0);
        for (int x = 0; x < arr.length; x++) {
            int current = arr[x];
            Info max = getMax(arr, x + 1);
            int value = max.value - current;
            if (value > 0 && info.value < value) {
                info.value = value;
                info.index = max.index;
            }
        }
        return info;
    }

    private static Info getMax(int[] arr, int i) {
        if (i > arr.length - 1) {
            return new Info(-1, 0);
        }
        Info info = new Info(-1, 0);
        for (int k = i; k < arr.length; k++) {
            if (info.value < arr[k]) {
                info.value = arr[k];
                info.index = k;
            }
        }
        return info;
    }

    private static class Info {
        private int index;
        private int value;

        public Info(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

}
