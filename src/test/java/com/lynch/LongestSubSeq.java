package com.lynch;

/**
 * 求最长公共子序列
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/22 23:18
 */
public class LongestSubSeq {

    public static int find(String a, String b) {
        if (a == null || a.length() == 0) {
            return 0;
        }
        if (b == null || b.length() == 0) {
            return 0;
        }

        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i <= m; i++) {
            char c1 = a.charAt(i-1);
            for (int j = 1; j <= n; j++) {
                char c2 = b.charAt(j-1);
                if(c1==c2){
                    dp[i][j] = dp[i-1][j-1] +1;
                }else{
                    dp[i][j] = Math.max( dp[i][j-1] ,dp[i-1][j]);
                }
            }
        }
        return dp[m][n];
    }
}
