package com.lynch.dp;

/**
 * 求两个字符串的最长公共子序列长度， 注意序列不一定是连续的
 * 给定的字符序列： {a,b,c,d,e,f,g,h}，它的子序列示例： {a,c,e,f}
 * 即元素 b,d,g,h 被去掉后，保持原有的元素序列所得到的结果就是子序列
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
//        String str1 = "babcde";
//        String str2 = "ace";
        String str1 = "abcdefgh";
        String str2 = "acef";
        int result = find(str1, str2);
        System.out.print("longest common sub sequence len: " + result);
    }

    private static int find(String str1, String str2) {

        int n = str1.length(), m = str2.length();
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= m; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    // 若当前字符相同则当前最大公共子序列长度就是 str1[i-1] 及 str2[j-1] 两者公共子序列长度 再加 1
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    // 若两者不相同则记录下此前最大的公共子序列长度，方便后续遇到相同字符时累加
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }
}
