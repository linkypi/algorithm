package com.lynch.dp;

/**
 * 求最长回文子序列长度
 */
public class LongestPalindromeSubseq {
    public static void main(String[] args) {

    }

    private static int getLongestPalindromeSubSeq(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int m = str.length();

        // dp[i][j] 表示从i到j的最长回文子序列长度
        // dp[i][j] 取决于 i 与 j 字符是否相同
        // 1. 若 str[i] == str[j] 则 dp[i][j] = dp[i+1][j-1] + 2
        // 2. 若 str[i] != str[j] 则 dp[i][j] = max( dp[i][j-1], dp[i+1][j])
        int[][] dp = new int[m][m];
        // i j 表示从i到j最长回文子序列长度
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == j) {
                    dp[i][j] = 1;
                }
                // i 超过 j 后无意义，该步骤可以忽略，因为int元素默认为0
                if (i > j) {
                    dp[i][j] = 0;
                }
            }
        }

        for (int i = m - 2; i > -1; i--) {
            for (int j = i + 1; j < m; j++) {
                if (i == j) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }
        return dp[0][m - 1];
    }
}
