package com.lynch;

import org.junit.Test;

/**
 * @author: lynch
 * @description:
 * @date: 2023/8/14 22:23
 */
public class Coins {

    @Test
    public void test() {
        int[] coins = {1, 2, 5};
        int target = 5;
        int ways2 = findWays2(coins, target);
        System.out.println("ways: " + ways2);
    }

    public int findWays2(int[] coins, int rest) {

        if (rest == 0) {
            return 1;
        }

        int ways = 0;
        for (int i = 0; i < coins.length; i++) {
            for (int j = 1; j * coins[i] <= rest; j++) {
                ways += findWays2(coins, rest - j * coins[i]);
            }
        }
        return ways;
    }

//    public int findWays(int[] coins, int target){
//
//        int n = coins.length;
//        // dp[i][j]使用前i个硬币凑出 j 块钱的方法数
//        int[][] dp = new int[n][target+1];
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j * coins[i] <= target; j++) {
//                dp[i][j] =
//            }
//        }
//    }
}
