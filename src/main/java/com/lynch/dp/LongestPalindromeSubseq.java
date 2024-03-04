package com.lynch.dp;

/**
 * 求最长回文子序列长度
 * https://leetcode.cn/problems/longest-palindromic-subsequence/description/
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
            dp[i][i] = 1;
        }

        // 仅需遍历左下三角部分
        for (int k = 1; k < m; k++) {
            int j = k;
            for (int i = 0; i < m - k; i++) {
                if (str.charAt(i) == str.charAt(j)) {
                    if (i + 1 == j) {
                        dp[i][j] = 2;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    }
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
                j++;
            }
        }
        return dp[0][m - 1];
    }
}
